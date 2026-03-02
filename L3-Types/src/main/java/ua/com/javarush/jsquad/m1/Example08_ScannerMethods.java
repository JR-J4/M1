package ua.com.javarush.jsquad.m1;

import java.util.Scanner;

/**
 * Лекція 3: Методи Scanner — nextInt(), nextDouble(), nextLine()
 *
 * Scanner має багато методів для зчитування різних типів даних:
 *
 *   nextLine()    — зчитує весь рядок до Enter           -> String
 *   nextInt()     — зчитує ціле число                    -> int
 *   nextDouble()  — зчитує дробове число                 -> double
 *   nextLong()    — зчитує велике ціле число             -> long
 *   nextBoolean() — зчитує true або false                -> boolean
 *   next()        — зчитує одне слово (до пробілу)       -> String
 *
 *
 * Виклик методу: змінна.метод(параметри);
 *   Якщо параметрів немає — порожні дужки: console.nextLine();
 */
public class Example08_ScannerMethods {

    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        
        // === Приклад: калькулятор покупок ===
        System.out.println("=== Калькулятор покупок ===");
        System.out.println();

        // Зчитуємо назву товару (рядок)
        System.out.print("Назва товару: ");
        String product = console.nextLine();

        // Зчитуємо ціну (дробове число, бо можуть бути копійки)
        System.out.print("Ціна за одиницю (грн): ");
        double pricePerUnit = console.nextDouble();

        // Зчитуємо кількість (ціле число)
        System.out.print("Кількість: ");
        int quantity = console.nextInt();

        // Рахуємо вартість
        double total = pricePerUnit * quantity;

        System.out.println();
        System.out.println("=== Чек ===");
        System.out.println("Товар: " + product);
        System.out.println("Ціна:  " + pricePerUnit + " грн x " + quantity + " шт");
        System.out.println("Всього: " + total + " грн");
        System.out.println();

        // === Приклад: обчислення середнього балу ===
        System.out.println("=== Середній бал ===");

        // "Споживаємо" залишок рядка після nextInt()
        // Це потрібно, бо nextInt() НЕ зчитує символ Enter!
        console.nextLine();

        System.out.print("Ім'я студента: ");
        String studentName = console.nextLine();

        System.out.print("Оцінка з математики: ");
        int math = console.nextInt();

        System.out.print("Оцінка з фізики: ");
        int physics = console.nextInt();

        System.out.print("Оцінка з програмування: ");
        int programming = console.nextInt();

        double average = (math + physics + programming) / 3.0;

        System.out.println();
        System.out.println("Студент: " + studentName);
        System.out.println("Середній бал: " + average);

        console.close();
    }
}
