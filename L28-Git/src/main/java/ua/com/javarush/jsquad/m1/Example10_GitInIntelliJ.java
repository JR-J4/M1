package ua.com.javarush.jsquad.m1;

/**
 * Лекцiя 28: Git.
 * <p>
 * Тема: Робота з Git у IntelliJ IDEA — графiчний iнтерфейс i гарячi клавiшi.
 * <p>
 * IntelliJ IDEA має чудову вбудовану пiдтримку Git: можна робити майже все,
 * не вiдкриваючи термiнал. Це зручно — видно дiфи, файли пiдсвiчуються
 * кольорами за станом, конфлiкти розв'язуються через 3-панельний редактор.
 * <p>
 * Аналогiя: командний рядок git — це готувати їжу з нуля. IntelliJ Git —
 * це мультиварка з програмами: тi самi результати, але без зайвої метушнi.
 * Професiонал повинен умiти обидва способи: знати команди для складних
 * випадкiв, але у щоденнiй роботi використовувати IDE.
 * <p>
 * Реальне застосування: у командi, де всi працюють у IntelliJ, через GUI
 * робиться 90% операцiй: pull зранку, commit, push увечерi.
 */
public class Example10_GitInIntelliJ {

    public static void main(String[] args) {
        // === Блок 1: гарячi клавiшi (Windows/Linux) ===
        // Сценарiй: основний набiр, який треба знати напам'ять.
        System.out.println("=== Гарячi клавiшi Git у IntelliJ (Windows/Linux) ===");
        System.out.println("Ctrl + T              git pull   — забрати свiжi змiни з сервера");
        System.out.println("Ctrl + K              git commit — вiдкрити дiалог комiту");
        System.out.println("Ctrl + Shift + K      git push   — вiдправити комiти на сервер");
        System.out.println("Ctrl + Alt + Z        rollback   — вiдкотити змiни у файлi/проєктi");
        System.out.println("Alt + 9               вiдкрити панель Git iз iсторiєю");
        System.out.println("Ctrl + Alt + A        git add    — додати файл у staged (рiдко потрiбно)");

        System.out.println();

        // === Блок 2: гарячi клавiшi (macOS) ===
        // Сценарiй: для тих, хто на маку (це бiльшiсть iOS-розробникiв i багато Java).
        System.out.println("=== Гарячi клавiшi Git у IntelliJ (macOS) ===");
        System.out.println("Cmd + T               git pull");
        System.out.println("Cmd + K               git commit");
        System.out.println("Cmd + Shift + K       git push");
        System.out.println("Cmd + Alt + Z         rollback");
        System.out.println("Cmd + 9               панель Git");

        System.out.println();

        // === Блок 3: пiдсвiчування кольорами ===
        // Сценарiй: студент бачить кольоровi файли в проєктi i не розумiє чому.
        System.out.println("=== Кольори файлiв у Project View ===");
        System.out.println("червоний       — untracked (новий, ще не доданий у Git)");
        System.out.println("зелений        — staged (доданий, ще не закомiчений)");
        System.out.println("синiй          — modified (змiнений пiсля останнього комiту)");
        System.out.println("сiрий          — ignored (у .gitignore)");
        System.out.println("звичайний      — committed (нiчого нового вiд останнього комiту)");

        System.out.println();

        // === Блок 4: дiалог комiту (Ctrl+K) ===
        // Сценарiй: що тут можна налаштувати.
        System.out.println("=== Що є у дiалозi Commit (Ctrl+K) ===");
        System.out.println("Лiва панель    — список змiнених файлiв (галочками обираємо, що в комiт)");
        System.out.println("Права панель   — дiф (порiвняння: було -> стало)");
        System.out.println("Поле message   — повiдомлення комiту");
        System.out.println("Чекбокс Amend  — змiнити ОСТАННIЙ комiт (НЕ використовувати пiсля push!)");
        System.out.println("Commit         — тiльки локально");
        System.out.println("Commit & Push  — одразу i на сервер");

        System.out.println();

        // === Блок 5: View History ===
        // Сценарiй: подивитися iсторiю файлу або всього репо.
        System.out.println("=== Подивитися iсторiю ===");
        System.out.println("Правий клiк на файлi -> Git -> Show History");
        System.out.println("Alt+9 -> вкладка Log -> вся iсторiя проєкту з графом гiлок");
        System.out.println("Дабл-клiк на комiтi  -> побачите всi файли i змiни цього комiту");

        System.out.println();

        // === Блок 6: розв'язання конфлiктiв через IDE ===
        // Сценарiй: при конфлiктi IDE сама пропонує триколонковий merge-tool.
        System.out.println("=== Розв'язання конфлiктiв у IntelliJ ===");
        System.out.println("Коли pull/merge створює конфлiкт — IDE покаже дiалог 'Merge Conflicts'.");
        System.out.println("Натиснiть 'Merge' — вiдкриється редактор iз 3 панелями:");
        System.out.println("  лiва   — версiя з main (наша)");
        System.out.println("  центр  — результат, що буде закомiчено");
        System.out.println("  права  — версiя з гiлки, що вливається");
        System.out.println("Стрiлочками '>>' / '<<' переносите потрiбнi рядки в центр.");

        System.out.println();

        // === Блок 7: коли все ж потрiбен термiнал ===
        // Сценарiй: GUI не покриває деякi випадки.
        System.out.println("=== Коли GUI не справляється i потрiбен термiнал ===");
        System.out.println("- складнi rebase iз iнтерактивним вибором комiтiв");
        System.out.println("- 'cherry-pick' конкретного комiту з iншої гiлки");
        System.out.println("- 'stash' зi специфiчними параметрами");
        System.out.println("- 'reflog' — пошук 'втрачених' комiтiв пiсля reset");
        System.out.println();
        System.out.println("У IntelliJ термiнал вiдкривається Alt+F12 (на macOS: Option+F12).");
        System.out.println("Або View -> Tool Windows -> Terminal.");
    }
}
