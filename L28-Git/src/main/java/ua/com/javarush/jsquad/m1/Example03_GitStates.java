package ua.com.javarush.jsquad.m1;

/**
 * Лекцiя 28: Git.
 * <p>
 * Тема: 4 стани файлiв у Git — untracked, modified, staged, committed.
 * <p>
 * Це найважливiша концепцiя для розумiння Git. Кожен файл у проєктi
 * перебуває в одному з 4 станiв, i всi команди (add, commit) перемiщують
 * файл мiж цими станами.
 * <p>
 * Аналогiя: уявiть пошту.
 * - untracked  — лист написали, але кур'єр його не бачить (Git не знає про файл).
 * - modified   — лист переписали, але ще не поклали в конверт.
 * - staged     — поклали в конверт i пiдготували до вiдправки.
 * - committed  — кур'єр забрав, лист поiхав у сховище (.git directory).
 * <p>
 * Реальне застосування: коли пишете код, ви постiйно перемикаєте файли
 * мiж цими станами. Розумiти їх — означає не плутатися з командами
 * git add / git commit / git status.
 */
public class Example03_GitStates {

    public static void main(String[] args) {
        // === Блок 1: 4 стани i що вони означають ===
        // Сценарiй: пояснюємо студенту словами, що таке кожен стан.
        System.out.println("=== 4 стани у Git ===");
        System.out.println("1) untracked — Git ще не знає про цей файл (новий файл).");
        System.out.println("2) modified  — файл уже в Git, але ви змiнили його пiсля останнього комiту.");
        System.out.println("3) staged    — ви додали файл командою 'git add', вiн готовий до комiту.");
        System.out.println("4) committed — змiни збереженi в iсторiю проєкту (у .git).");

        System.out.println();

        // === Блок 2: дiаграма потоку ===
        // Сценарiй: ASCII-картинка з трьома "зонами" Git.
        System.out.println("=== Як файл рухається мiж зонами ===");
        System.out.println();
        System.out.println("  +---------------+    +---------------+    +---------------+");
        System.out.println("  | Work Directory|    | Staging Area  |    | .git directory|");
        System.out.println("  | (вашi файли)  |    | (пiдготовка)  |    | (iсторiя)     |");
        System.out.println("  +---------------+    +---------------+    +---------------+");
        System.out.println("         |                    ^                     ^");
        System.out.println("         |   git add file     |                     |");
        System.out.println("         +-------------------->                     |");
        System.out.println("                              |     git commit      |");
        System.out.println("                              +--------------------->");
        System.out.println();
        System.out.println("git checkout <-- забрати останню збережену версiю назад у Work Directory");

        System.out.println();

        // === Блок 3: приклад "життєвого циклу" файлу ===
        // Сценарiй: створили файл, змiнили, додали, закомiтили.
        System.out.println("=== Життєвий цикл файлу Hello.java ===");
        System.out.println("Крок 1: створили файл Hello.java                  -> untracked");
        System.out.println("Крок 2: 'git add Hello.java'                       -> staged");
        System.out.println("Крок 3: 'git commit -m \"add hello\"'                -> committed");
        System.out.println("Крок 4: змiнили рядок у Hello.java                 -> modified");
        System.out.println("Крок 5: 'git add Hello.java'                       -> staged");
        System.out.println("Крок 6: 'git commit -m \"fix typo\"'                 -> committed");

        System.out.println();

        // === Блок 4: як перевiрити стан ===
        // Сценарiй: яка команда показує, у якому станi кожен файл.
        System.out.println("=== Як побачити стан файлiв ===");
        System.out.println("git status — показує всi файли i в якому вони станi:");
        System.out.println();
        System.out.println("  Untracked files:        (новi файли)");
        System.out.println("     Hello.java");
        System.out.println();
        System.out.println("  Changes not staged:     (modified — ще не доданi)");
        System.out.println("     modified: Main.java");
        System.out.println();
        System.out.println("  Changes to be committed: (staged — готовi до комiту)");
        System.out.println("     modified: README.md");

        System.out.println();

        // === Блок 5: типова помилка новачка ===
        // Сценарiй: студент змiнив файл, зробив commit, але змiни не збереглися.
        System.out.println("=== Типова помилка ===");
        System.out.println("ПОМИЛКА: змiнили файл i вiдразу 'git commit -m \"...\"'");
        System.out.println("        Git скаже: 'nothing to commit, working tree clean' або");
        System.out.println("        просто проiгнорує змiни. Чому? Бо modified != staged!");
        System.out.println();
        System.out.println("ПРАВИЛЬНО: спочатку 'git add <file>', потiм 'git commit -m \"...\"'");
        System.out.println("або одразу 'git commit -am \"...\"' (-a = add tracked files автоматично)");
    }
}
