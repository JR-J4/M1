package ua.com.javarush.jsquad.m1.example02_static_vs_instance;

/**
 * Лекція 14: Класи та static
 * <hr>
 * <h3>Тема: Відмінність статичних і нестатичних змінних</h3>
 *
 * <p>Звичайні змінні класу прив'язані до об'єктів свого класу (екземплярів),
 * а <b>статичні змінні</b> — до статичного об'єкта класу.</p>
 *
 * <p>Якщо екземплярів класу кілька, у кожному з них є своя копія нестатичних
 * (звичайних) змінних класу. Статичні змінні класу завжди знаходяться всередині
 * статичного об'єкта класу та існують лише в одному екземплярі.</p>
 *
 * <p><b>Аналогія з життя:</b>
 * Онлайн-гра: кожен гравець має своє ім'я та рахунок (нестатичні),
 * але загальна кількість гравців онлайн — одна на всю гру (статична).</p>
 *
 * <p><b>Реальне застосування:</b>
 * Статичні змінні — для даних, що стосуються всього класу: лічильники,
 * конфігурація, кеш. Нестатичні — для даних конкретного об'єкта.</p>
 */
public class Example02_StaticVsInstance {

    public static void main(String[] args) {

        // === 1. Створення об'єктів — спільне vs власне ===
        // Сценарій: три гравці заходять в онлайн-гру

        System.out.println("=== Статичне (спільне) vs нестатичне (власне) ===");

        Player p1 = new Player("Warrior");
        Player p2 = new Player("Mage");
        Player p3 = new Player("Archer");

        // У кожного свій score (нестатична), але totalPlayers спільний (статична)
        p1.addScore(150);
        p2.addScore(300);
        p3.addScore(200);

        p1.showStats();
        p2.showStats();
        p3.showStats();

        System.out.println();

        // === 2. Зміна статичної змінної — видно для всіх ===
        // Статична змінна одна — її зміна відображається скрізь

        System.out.println("=== Зміна статичної змінної з різних об'єктів ===");

        System.out.println("Player.totalPlayers = " + Player.totalPlayers); // 3

        // Додамо ще одного гравця
        Player p4 = new Player("Healer");
        System.out.println("Після додавання Healer:");
        System.out.println("Player.totalPlayers = " + Player.totalPlayers); // 4

        // Кожен об'єкт "бачить" однакове значення статичної змінної
        p1.showStats(); // totalPlayers = 4
        p4.showStats(); // totalPlayers = 4

        System.out.println();

        // === 3. Візуальна схема: де зберігаються змінні ===

        System.out.println("=== Як це виглядає в пам'яті ===");
        System.out.println();
        System.out.println("  Статичний об'єкт класу Player:");
        System.out.println("  ┌─────────────────────────┐");
        System.out.println("  │  totalPlayers = " + Player.totalPlayers + "       │");
        System.out.println("  └─────────────────────────┘");
        System.out.println();
        System.out.println("  Об'єкт p1:          Об'єкт p2:          Об'єкт p3:");
        System.out.println("  ┌──────────────┐   ┌──────────────┐   ┌──────────────┐");
        System.out.println("  │ name=Warrior  │   │ name=Mage    │   │ name=Archer  │");
        System.out.println("  │ score=" + p1.getScore() + "     │   │ score=" + p2.getScore() + "    │   │ score=" + p3.getScore() + "    │");
        System.out.println("  └──────────────┘   └──────────────┘   └──────────────┘");
        System.out.println();
        System.out.println("  Кожен об'єкт має ВЛАСНІ name і score,");
        System.out.println("  але СПІЛЬНИЙ totalPlayers (один на весь клас).");
    }
}
