package ua.com.javarush.jsquad.m1.example05_static_block;

/**
 * У класі може бути КІЛЬКА static-блоків.
 * Вони виконуються згори вниз — у порядку оголошення.
 */
public class BankTerminal {

    public static int dailyLimit;

    static {
        System.out.println("  [BankTerminal] static-блок №1: перевірка зв'язку з банком");
        dailyLimit = 10_000;
    }

    static {
        System.out.println("  [BankTerminal] static-блок №2: святковий день — ліміт подвоєно");
        dailyLimit = dailyLimit * 2;
    }

    static {
        System.out.println("  [BankTerminal] static-блок №2: святковий день — ліміт подвоєно");
        dailyLimit = dailyLimit * 2;
    }
}
