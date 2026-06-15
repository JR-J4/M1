package ua.com.javarush.jsquad.m1;

/**
 * Лекцiя 28: Git.
 * <p>
 * Тема: Що таке Git i для чого вiн потрiбен. Базова термiнологiя.
 * <p>
 * Git — це розподiлена система контролю версiй (Version Control System).
 * Вона дозволяє вiдстежувати кожну змiну файлiв у проєктi, повертатися
 * назад у часi, працювати над одним проєктом командою, а ще — не боятися
 * експериментувати, бо завжди можна "вiдкотити" невдалий експеримент.
 * <p>
 * Аналогiя: уявiть, що ви пишете дипломну роботу. Без Git у вас на
 * робочому столi купа файлiв: "diplom.docx", "diplom_final.docx",
 * "diplom_final_final.docx", "diplom_правда_final.docx". З Git у вас
 * один файл, а вся iсторiя змiн — усерединi репозиторiю. Можна
 * подивитися: "А що було два тижнi тому? А хто змiнив цей абзац?".
 * <p>
 * Реальне застосування: усi командирозробники тримають код в Git.
 * Без Git неможливо влаштуватися Java-розробником: це базовий
 * iнструмент, як викрутка для електрика.
 */
public class Example01_GitIntro {

    public static void main(String[] args) {
        // === Блок 1: що таке Git ===
        // Сценарiй: пояснюємо студенту "одним абзацом", що це таке.
        System.out.println("=== Що таке Git ===");
        System.out.println("Git — розподiлена система контролю версiй.");
        System.out.println("Зберiгає iсторiю змiн файлiв у спецiальнiй папцi .git");
        System.out.println("у коренi проєкту. Кожна змiна — як 'фотографiя' проєкту.");

        System.out.println();

        // === Блок 2: для чого Git ===
        // Сценарiй: показуємо, якi проблеми вирiшує iнструмент.
        System.out.println("=== Для чого Git ===");
        System.out.println("1) Iсторiя змiн — видно, хто, коли i що змiнив.");
        System.out.println("2) Вiдкат — можна повернутися до будь-якого старого стану.");
        System.out.println("3) Командна робота — кiлька людей працюють паралельно.");
        System.out.println("4) Гiлки — експерименти не ламають основний код.");
        System.out.println("5) Резервна копiя — код є на серверi (GitHub/GitLab).");

        System.out.println();

        // === Блок 3: ключовi термiни ===
        // Сценарiй: словничок, який знадобиться у всiх наступних прикладах.
        System.out.println("=== Термiнологiя ===");
        System.out.println("репозиторiй (repository) — папка з iсторiєю проєкту");
        System.out.println("комiт      (commit)     — збережена 'фотографiя' змiн");
        System.out.println("гiлка      (branch)     — паралельна лiнiя розробки");
        System.out.println("merge      (змерджити)  — злити одну гiлку в iншу");
        System.out.println("conflict   (конфлiкт)   — той самий рядок змiнено двiчi");
        System.out.println("pull       (спулити)    — забрати змiни з сервера");
        System.out.println("push       (запушити)   — вiдправити змiни на сервер");
        System.out.println(".gitignore              — список файлiв, якi Git iгнорує");

        System.out.println();

        // === Блок 4: коротка iсторiя ===
        // Сценарiй: цiкавий факт, який варто знати.
        System.out.println("=== Цiкавий факт ===");
        System.out.println("Git створив Лiнус Торвальдс у 2005 роцi за 2 тижнi,");
        System.out.println("щоб керувати розробкою ядра Linux.");
        System.out.println("Слово 'git' в англiйськiй означає 'неприємна людина' — ");
        System.out.println("Лiнус так пожартував над собою.");
    }
}
