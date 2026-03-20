package ua.com.javarush.jsquad.m1;

import java.util.Arrays;

/**
 * Лекція 9: Методи — Передача посилань
 *
 * Посилальні типи (масиви, об'єкти) передають у метод адресу пам'яті.
 * Це означає, що зміни всередині методу впливають на той самий об'єкт ззовні.
 *
 * Аналогія: передаємо не копію таблиці Excel, а посилання на Google Sheets — будь-хто бачить оновлення одразу.
 * Реальне застосування: сервіс оновлює масиви конфігурації або DTO, і всі, хто їх використовує, бачать нові дані.
 */
public class Example04_PassByReference {

    public static void main(String[] args) {

        // ============================================================
        //   Приклад 1: Масив продажів змінюється всередині методу
        // ============================================================
        System.out.println("=== Корекція продажів ===");
        int[] monthlySales = {120, 150, 90, 0};
        System.out.println("До:  " + Arrays.toString(monthlySales));
        applyBonus(monthlySales, 15);
        System.out.println("Після: " + Arrays.toString(monthlySales));

        System.out.println();

        // ============================================================
        //   Приклад 2: Двовимірний масив складу
        // ============================================================
        System.out.println("=== Заповнюємо комірки складу ===");
        String[][] warehouse = {
                {"", ""},
                {"", "", ""}
        };
        fillWarehouse(warehouse, "Павербанк");
        for (String[] shelf : warehouse) {
            System.out.println(Arrays.toString(shelf));
        }

        System.out.println();

        // ============================================================
        //   Приклад 3: Масив рядків (імена команди)
        // ============================================================
        System.out.println("=== Замінюємо відповідального ===");
        String[] scrumTeam = {"Олег", "Марія", "Світлана"};
        replaceLead(scrumTeam, "Марія", "Ірина");
        System.out.println("Актуальна команда: " + Arrays.toString(scrumTeam));
    }

    private static void applyBonus(int[] monthlySales, int bonusPercent) {
        for (int i = 0; i < monthlySales.length; i++) {
            monthlySales[i] += monthlySales[i] * bonusPercent / 100;
        }
    }

    private static void fillWarehouse(String[][] warehouse, String itemName) {
        for (int i = 0; i < warehouse.length; i++) {
            for (int j = 0; j < warehouse[i].length; j++) {
                warehouse[i][j] = itemName + " #" + (i + 1) + "-" + (j + 1);
            }
        }
    }

    private static void replaceLead(String[] team, String oldLead, String newLead) {
        for (int i = 0; i < team.length; i++) {
            if (team[i].equals(oldLead)) {
                team[i] = newLead;
            }
        }
    }
}
