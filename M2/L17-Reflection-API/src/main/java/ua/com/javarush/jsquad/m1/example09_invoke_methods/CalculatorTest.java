package ua.com.javarush.jsquad.m1.example09_invoke_methods;

/**
 * Набір тестів для калькулятора.
 *
 * <p>Зверніть увагу: цей клас <b>нікого не викликає сам</b> і ні від кого не
 * успадковується. Він просто позначений анотаціями. Знайти й запустити його
 * методи — задача міні-фреймворка з прикладу 09 (і справжнього JUnit).</p>
 */
public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    public void setUp() {
        // Перед кожним тестом отримуємо свіжий калькулятор
        calculator = new Calculator();
    }

    @Test("Додавання двох додатних чисел")
    public void additionWorks() {
        int result = calculator.add(2, 3);
        assertEquals(5, result);
    }

    @Test("Ділення працює правильно")
    public void divisionWorks() {
        double result = calculator.divide(10, 4);
        assertEquals(2.5, result);
    }

    @Test("Ділення на нуль кидає виняток")
    public void divisionByZeroFails() {
        // Цей тест НАВМИСНО провалений: чекаємо 0, а метод кине ArithmeticException
        double result = calculator.divide(10, 0);
        assertEquals(0.0, result);
    }

    @Test("Пам'ять зберігає значення")
    public void memoryWorks() {
        calculator.saveToMemory(42.5);
        assertEquals(42.5, calculator.getMemory());
    }

    @Test("Квадрат числа")
    public void squareWorks() {
        // Ще один навмисно провалений тест — щоб побачити звіт про помилку
        assertEquals(100, Calculator.square(9));
    }

    /** Метод БЕЗ анотації — фреймворк не має його запускати. */
    public void notATest() {
        throw new IllegalStateException("Цей метод не повинен був запуститися!");
    }

    /** Найпростіша перевірка — аналог org.junit.Assertions.assertEquals. */
    private static void assertEquals(Object expected, Object actual) {
        if (!String.valueOf(expected).equals(String.valueOf(actual))) {
            throw new AssertionError("очікували " + expected + ", а отримали " + actual);
        }
    }
}
