package ua.com.javarush.jsquad.m1.example11_summary;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Міні-контейнер залежностей — навчальна модель того, що робить Spring.
 *
 * <p>Увесь контейнер побудований на рефлексії з цієї лекції:</p>
 * <pre>
 *   Class.forName()               — знайти клас за іменем з "конфігу"
 *   getAnnotation()               — зрозуміти, що з класом робити
 *   getDeclaredConstructor()
 *        .newInstance()           — створити об'єкт
 *   getDeclaredFields()
 *        + setAccessible(true)
 *        + field.set()            — заповнити приватні поля
 *   getDeclaredMethods()
 *        + method.invoke()        — викликати метод життєвого циклу
 *   Proxy.newProxyInstance()      — загорнути компонент у проксі з логуванням
 * </pre>
 */
public class MiniContainer {

    /** Готові компоненти: клас → об'єкт (можливо, загорнутий у проксі). */
    private final Map<Class<?>, Object> beans = new LinkedHashMap<>();

    /** Справжні об'єкти без проксі — саме їм ми заповнюємо поля. */
    private final Map<Class<?>, Object> rawBeans = new LinkedHashMap<>();

    /** "Файл налаштувань". */
    private final Map<String, String> properties;

    public MiniContainer(Map<String, String> properties) {
        this.properties = properties;
    }

    /**
     * Запускає контейнер: приймає імена класів рядками — так само,
     * як Spring отримує їх зі сканування пакетів чи XML-конфігурації.
     */
    public void start(List<String> classNames) throws Exception {

        // --- Крок 1. Завантажуємо класи і створюємо об'єкти ---
        System.out.println("   Крок 1: створення компонентів");
        for (String className : classNames) {
            Class<?> type = Class.forName(className);          // клас відомий лише рядком

            if (!type.isAnnotationPresent(Component.class)) {   // не наш компонент — пропускаємо
                System.out.println("      " + type.getSimpleName() + " — без @Component, пропущено");
                continue;
            }

            Object instance = type.getDeclaredConstructor().newInstance();
            rawBeans.put(type, instance);
            System.out.println("      створено " + type.getSimpleName());
        }

        // --- Крок 2. Заповнюємо поля з @Value значеннями з налаштувань ---
        System.out.println("   Крок 2: підстановка значень з налаштувань (@Value)");
        for (Object bean : rawBeans.values()) {
            injectValues(bean);
        }

        // --- Крок 3. Загортаємо в проксі тих, хто має інтерфейс ---
        System.out.println("   Крок 3: створення проксі");
        for (Map.Entry<Class<?>, Object> entry : rawBeans.entrySet()) {
            Class<?> type = entry.getKey();
            Object raw = entry.getValue();
            Class<?>[] interfaces = type.getInterfaces();

            if (interfaces.length > 0) {
                Object proxy = createLoggingProxy(raw, interfaces);
                beans.put(type, proxy);
                System.out.println("      " + type.getSimpleName() + " -> загорнуто у "
                        + proxy.getClass().getSimpleName());
            } else {
                beans.put(type, raw);
                System.out.println("      " + type.getSimpleName() + " -> без інтерфейсів, проксі не потрібне");
            }
        }

        // --- Крок 4. Підставляємо залежності в поля з @Inject ---
        System.out.println("   Крок 4: підстановка залежностей (@Inject)");
        for (Object bean : rawBeans.values()) {
            injectDependencies(bean);
        }

        // --- Крок 5. Викликаємо методи життєвого циклу ---
        System.out.println("   Крок 5: виклик методів @PostConstruct");
        for (Object bean : rawBeans.values()) {
            callPostConstruct(bean);
        }
    }

    /**
     * Дістає компонент із контейнера.
     * Якщо компонент був загорнутий у проксі — повернеться саме проксі.
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) {
        Object bean = beans.get(type);
        if (bean == null) {
            // Шукаємо за інтерфейсом: хто з компонентів його реалізує
            bean = findByInterface(type);
        }
        if (bean == null) {
            throw new IllegalStateException("Компонент типу " + type.getSimpleName() + " не знайдено");
        }
        return (T) bean;
    }

    /**
     * Заповнює поля, позначені {@code @Value}, значеннями з налаштувань,
     * перетворюючи рядок на тип поля.
     */
    private void injectValues(Object bean) throws IllegalAccessException {
        for (Field field : bean.getClass().getDeclaredFields()) {
            Value annotation = field.getAnnotation(Value.class);
            if (annotation == null) {
                continue;
            }

            String raw = properties.get(annotation.value());
            if (raw == null) {
                System.out.println("      [!] немає налаштування '" + annotation.value() + "'");
                continue;
            }

            field.setAccessible(true);       // поле приватне — знімаємо захист
            Class<?> fieldType = field.getType();

            if (fieldType == int.class) {
                field.setInt(bean, Integer.parseInt(raw));
            } else if (fieldType == boolean.class) {
                field.setBoolean(bean, Boolean.parseBoolean(raw));
            } else if (fieldType == double.class) {
                field.setDouble(bean, Double.parseDouble(raw));
            } else {
                field.set(bean, raw);
            }

            System.out.println("      " + bean.getClass().getSimpleName() + "." + field.getName()
                    + " <- " + raw);
        }
    }

    /**
     * Знаходить поля з {@code @Inject} і підставляє в них потрібні компоненти.
     * Тип поля — це і є "що саме треба знайти".
     */
    private void injectDependencies(Object bean) throws IllegalAccessException {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(Inject.class)) {
                continue;
            }

            Class<?> required = field.getType();
            Object dependency = beans.get(required);
            if (dependency == null) {
                dependency = findByInterface(required);       // поле оголошене через інтерфейс
            }
            if (dependency == null) {
                throw new IllegalStateException("Нема чим заповнити " + field.getName());
            }

            field.setAccessible(true);
            field.set(bean, dependency);

            System.out.println("      " + bean.getClass().getSimpleName() + "." + field.getName()
                    + " <- " + (Proxy.isProxyClass(dependency.getClass())
                    ? "проксі над " + required.getSimpleName()
                    : dependency.getClass().getSimpleName()));
        }
    }

    /** Викликає всі методи, позначені {@code @PostConstruct}. */
    private void callPostConstruct(Object bean) throws Exception {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.setAccessible(true);
                method.invoke(bean);
            }
        }
    }

    /** Шукає компонент, що реалізує заданий інтерфейс. */
    private Object findByInterface(Class<?> required) {
        for (Map.Entry<Class<?>, Object> entry : beans.entrySet()) {
            if (required.isAssignableFrom(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Створює динамічний проксі, який логує кожен виклик і передає його далі
     * справжньому об'єкту. Приблизно так Spring реалізує {@code @Transactional}.
     */
    private Object createLoggingProxy(Object target, Class<?>[] interfaces) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                interfaces,
                (proxy, method, args) -> {
                    // Методи Object не логуємо — інакше зламається toString()
                    if (method.getDeclaringClass() == Object.class) {
                        return method.invoke(target, args);
                    }

                    System.out.println("      [ПРОКСІ] " + target.getClass().getSimpleName()
                            + "." + method.getName() + "() викликано");
                    try {
                        return method.invoke(target, args);
                    } catch (InvocationTargetException e) {
                        throw e.getCause();          // прокидаємо справжню причину
                    }
                });
    }

    /** Перелік створених компонентів — для звіту. */
    public List<String> describeBeans() {
        List<String> report = new ArrayList<>();
        for (Map.Entry<Class<?>, Object> entry : beans.entrySet()) {
            String kind = Proxy.isProxyClass(entry.getValue().getClass()) ? "проксі" : "звичайний об'єкт";
            report.add(entry.getKey().getSimpleName() + " (" + kind + ")");
        }
        return report;
    }
}
