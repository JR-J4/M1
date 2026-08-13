package ua.com.javarush.jsquad.m1.example10_dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Модуль 2. Рівень 17. Reflection API
 * <hr>
 * <h3>Тема: Dynamic Proxy — динамічний проксі</h3>
 *
 * <p>У Java є спеціальний клас {@code java.lang.reflect.Proxy}, за допомогою якого
 * можна <b>сконструювати об'єкт під час виконання програми (динамічно), без
 * створення для нього окремого класу</b>.</p>
 *
 * <p>Для перехоплення викликів використовується інтерфейс
 * {@code InvocationHandler} з єдиним методом {@code invoke}, до якого
 * направляються <b>всі</b> виклики, звернені до proxy-об'єкта.</p>
 *
 * <h4>Синтаксис:</h4>
 * <pre>
 *   UserService proxy = (UserService) Proxy.newProxyInstance(
 *           classLoader,                      // хто завантажить згенерований клас
 *           new Class&lt;?&gt;[]{UserService.class}, // які інтерфейси має реалізувати
 *           handler);                          // куди направляти всі виклики
 *
 *   // сигнатура методу-перехоплювача:
 *   Object invoke(Object proxy, Method method, Object[] args)
 * </pre>
 *
 * <p><b>Важливе обмеження:</b> динамічний проксі підміняє <b>лише інтерфейси</b>.
 * Для проксі над класами потрібні сторонні бібліотеки (CGLIB, ByteBuddy) — саме
 * тому Spring тягне їх із собою.</p>
 *
 * <p><b>Аналогія з життя:</b> проксі — це секретар керівника. Ззовні ви ніби
 * дзвоните керівнику (той самий номер, той самий перелік питань), але слухавку
 * бере секретар: він занотовує дзвінок, вирішує, чи з'єднувати, і лише потім
 * передає розмову далі. Ви навіть не дізнаєтесь, що говорили не напряму.</p>
 *
 * <p><b>Реальне застосування:</b> {@code @Transactional} у Spring — це проксі,
 * що відкриває транзакцію до методу і закриває після. {@code @Cacheable} — проксі,
 * що спершу зазирає в кеш. Mockito створює заглушки в тестах теж проксі.</p>
 */
public class Example10_DynamicProxy {

    public static void main(String[] args) {


        // [C]  -> [Proxy]  ->  [findNameById]
        // [C]  <- [Proxy]  <-  [findNameById]


        // [C]  -> [Proxy]  X  [findNameById]
        // [C]  <- [Proxy]  X  [findNameById]

        //           log()


        // === 1. Проксі, що логує кожен виклик ===
        // Сценарій: сервіс уже написаний і працює. Треба додати логування,
        // не змінюючи в ньому жодного рядка.
        UserService realService = new UserServiceImpl();

        UserService loggingProxy = (UserService) Proxy.newProxyInstance(
                UserService.class.getClassLoader(),        // завантажувач класів
                new Class<?>[]{UserService.class},          // інтерфейси проксі
                new LoggingHandler(realService));           // перехоплювач

        System.out.println("1. Проксі з логуванням:");
        String name = loggingProxy.findNameById(2);
        System.out.println("   Отримано: " + name);
        System.out.println("   Користувачів: " + loggingProxy.countUsers());

        System.out.println();

        // === 2. Що таке проксі насправді ===
        // Це об'єкт згенерованого на льоту класу. Свого .java-файлу він не має.
        System.out.println("2. Що ми отримали:");
        System.out.println("   Клас проксі:  " + loggingProxy.getClass().getName());
        System.out.println("   Це проксі?    " + Proxy.isProxyClass(loggingProxy.getClass()));
        System.out.println("   Реалізує:     " + Arrays.toString(loggingProxy.getClass().getInterfaces()));
        System.out.println("   Батько класу: " + loggingProxy.getClass().getSuperclass().getSimpleName());
        System.out.println("   Обробник:     "
                + Proxy.getInvocationHandler(loggingProxy).getClass().getSimpleName());
        System.out.println("   Такого класу немає у вашому проєкті — JVM створила його щойно.");

        System.out.println();

        // === 3. Проксі, що вимірює час виконання ===
        // Той самий сервіс, інший "секретар" — і сервіс знову не змінювався.
        UserService timingProxy = (UserService) Proxy.newProxyInstance(
                UserService.class.getClassLoader(),
                new Class<?>[]{UserService.class},
                (proxy, method, methodArgs) -> {           // лямбда замість класу
                    long start = System.nanoTime();
                    try {
                        return method.invoke(realService, methodArgs);
                    } finally {
                        long millis = (System.nanoTime() - start) / 1_000_000;
                        System.out.println("   [ЧАС] " + method.getName() + " виконувався " + millis + " мс");
                    }
                });

        System.out.println("3. Проксі, що вимірює час:");
        timingProxy.findNameById(1);
        timingProxy.countUsers();

        System.out.println();

        // === 4. Проксі може НЕ пропустити виклик далі ===
        // Сценарій: заборона видалення для звичайних користувачів (перевірка прав).
        UserService protectedProxy = (UserService) Proxy.newProxyInstance(
                UserService.class.getClassLoader(),
                new Class<?>[]{UserService.class},
                (proxy, method, methodArgs) -> {
                    if (method.getName().startsWith("delete")) {
                        throw new SecurityException("Недостатньо прав для " + method.getName() + "()");
                    }
                    return method.invoke(realService, methodArgs);
                });

        System.out.println("4. Проксі як охоронець прав доступу:");
        System.out.println("   Читання дозволене: " + protectedProxy.findNameById(3));
        try {
            protectedProxy.deleteUser(3);
        } catch (SecurityException e) {
            System.out.println("   Видалення -> SecurityException: " + e.getMessage());
        }
        System.out.println("   Користувач на місці: " + protectedProxy.findNameById(3));

        System.out.println();

        // === 5. Проксі з кешем ===
        // Сценарій: реальний виклик іде до "бази" 30 мс. Другий раз беремо з кешу.
        Map<String, Object> cache = new HashMap<>();

        UserService cachingProxy = (UserService) Proxy.newProxyInstance(
                UserService.class.getClassLoader(),
                new Class<?>[]{UserService.class},
                (proxy, method, methodArgs) -> {
                    String key = method.getName() + Arrays.toString(methodArgs);
                    if (cache.containsKey(key)) {
                        System.out.println("   [КЕШ] " + key + " — беремо збережене");
                        return cache.get(key);
                    }
                    Object value = method.invoke(realService, methodArgs);
                    cache.put(key, value);
                    System.out.println("   [БАЗА] " + key + " — запитали і зберегли");
                    return value;
                });

        System.out.println("5. Проксі з кешем (так працює @Cacheable у Spring):");
        long start = System.currentTimeMillis();
        cachingProxy.findNameById(1);
        cachingProxy.findNameById(1);
        cachingProxy.findNameById(1);
        System.out.println("   Три виклики зайняли " + (System.currentTimeMillis() - start)
                + " мс замість ~90 мс");

        System.out.println();

        // === 6. Проксі БЕЗ реального об'єкта — заглушка ===
        // Найцікавіше: реалізації WeatherApi в проєкті взагалі немає.
        // Проксі сам вигадує відповіді — саме так працює Mockito в тестах.
        WeatherApi fakeWeather = (WeatherApi) Proxy.newProxyInstance(
                WeatherApi.class.getClassLoader(),
                new Class<?>[]{WeatherApi.class},
                (proxy, method, methodArgs) -> {
                    // Дивимось, що метод має повернути, і вигадуємо відповідь
                    if (method.getReturnType() == int.class) {
                        return 21;
                    }
                    if (method.getReturnType() == String.class) {
                        return "Сонячно у місті " + methodArgs[0];
                    }
                    return null;
                });

        System.out.println("6. Заглушка без жодного класу-реалізації:");
        System.out.println("   getTemperature(\"Київ\") -> " + fakeWeather.getTemperature("Київ"));
        System.out.println("   getForecast(\"Львів\")   -> " + fakeWeather.getForecast("Львів"));
        System.out.println("   Класу, що реалізує WeatherApi, у проєкті немає взагалі.");

        System.out.println();

        // === 7. Пастка: через invoke() йдуть ВСІ методи ===
        // Включно з toString(), equals() та hashCode(), успадкованими від Object.
        System.out.println("7. Обережно: перехоплюються навіть методи Object:");

        WeatherApi trickyProxy = (WeatherApi) Proxy.newProxyInstance(
                WeatherApi.class.getClassLoader(),
                new Class<?>[]{WeatherApi.class},
                (proxy, method, methodArgs) -> {
                    System.out.println("   [перехоплено] " + method.getName()
                            + " з класу " + method.getDeclaringClass().getSimpleName());
                    if (method.getReturnType() == int.class) {
                        return 0;
                    }
                    if (method.getReturnType() == boolean.class) {
                        return false;
                    }
                    return "заглушка";
                });

        System.out.println("   Викликаємо метод інтерфейсу:");
        trickyProxy.getTemperature("Одеса");

        System.out.println("   А тепер просто друкуємо об'єкт:");
        System.out.println("   " + trickyProxy);          // це виклик toString() → теж піде в invoke!

        System.out.println("   Саме тому в реальних обробниках методи Object обробляють окремо:");
        System.out.println("   if (method.getDeclaringClass() == Object.class) return method.invoke(this, args);");

        System.out.println();

        // === 8. Обмеження: лише інтерфейси ===
        System.out.println("8. Обмеження динамічного проксі:");
        try {
            Proxy.newProxyInstance(
                    UserServiceImpl.class.getClassLoader(),
                    new Class<?>[]{UserServiceImpl.class},   // КЛАС, а не інтерфейс
                    (proxy, method, methodArgs) -> null);
        } catch (IllegalArgumentException e) {
            System.out.println("   Спроба зробити проксі над класом -> IllegalArgumentException");
            System.out.println("   " + e.getMessage());
        }
        System.out.println("   java.lang.reflect.Proxy вміє підміняти тільки інтерфейси.");
        System.out.println("   Для класів Spring використовує CGLIB / ByteBuddy.");
    }

    /**
     * Обробник, що логує кожен виклик і передає його справжньому об'єкту.
     *
     * <p>Це і є "секретар": він бачить ім'я методу, аргументи та результат.
     * Приблизно так само влаштовані {@code @Transactional} та {@code @Cacheable}.</p>
     */
    private static class LoggingHandler implements InvocationHandler {

        private final Object target;    // справжній об'єкт, якому делегуємо роботу

        LoggingHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // Методи Object обробляємо окремо, щоб не зламати toString()/equals()
            if (method.getDeclaringClass() == Object.class) {
                return method.invoke(this, args);
            }

            System.out.println("   [ЛОГ] виклик " + method.getName()
                    + Arrays.toString(args == null ? new Object[0] : args));

            try {
                Object result = method.invoke(target, args);      // передаємо далі
                System.out.println("   [ЛОГ] " + method.getName() + " повернув " + result);
                return result;
            } catch (InvocationTargetException e) {
                // Виняток справжнього методу лежить у getCause() — його і прокидаємо далі,
                // інакше клієнт отримає незрозумілий InvocationTargetException
                System.out.println("   [ЛОГ] " + method.getName() + " кинув "
                        + e.getCause().getClass().getSimpleName());
                throw e.getCause();
            }
        }
    }
}
