package ua.com.javarush.jsquad.m1;

/**
 * Лекцiя 28: Git.
 * <p>
 * Тема: Робота з вiддаленими репозиторiями (GitHub, GitLab, BitBucket).
 * <p>
 * До цього моменту все вiдбувалося локально, на вашому комп'ютерi. Але
 * команда не може працювати в iзоляцiї — потрiбен спiльний сервер, де
 * усi бачать код одне одного. Цей сервер називають "вiддалений
 * репозиторiй" (remote). Найпопулярнiшi: GitHub, GitLab, BitBucket.
 * <p>
 * Основнi команди:
 * <pre>
 *   git clone <url>           — завантажити проєкт iз сервера на свiй комп
 *   git pull                  — забрати свiжi змiни з сервера
 *   git push                  — вiдправити свої комiти на сервер
 *   git remote -v             — показати, який вiддалений репо прив'язаний
 *   git remote add origin URL — прив'язати локальний репо до сервера
 * </pre>
 * <p>
 * Аналогiя: локальний репо — це ваш робочий зошит, вiддалений — це
 * хмарний диск у Google Drive. git push = "завантажити зошит у хмару",
 * git pull = "скачати свiжу версiю з хмари". Команда працює iз спiльною
 * хмарою, а кожен має свою локальну копiю на ноутбуку.
 */
public class Example09_GitRemote {

    public static void main(String[] args) {
        // === Блок 1: вiддаленi сервiси ===
        // Сценарiй: огляд популярних платформ.
        System.out.println("=== Популярнi платформи для вiддалених репо ===");
        System.out.println("GitHub    — найбiльша платформа, де живуть мiльйони open-source проєктiв.");
        System.out.println("            Безкоштовнi публiчнi i приватнi репо.");
        System.out.println("GitLab    — корпоративний варiант з вбудованим CI/CD pipeline.");
        System.out.println("            Можна розгорнути на власному серверi.");
        System.out.println("BitBucket — вiд Atlassian, добре iнтегрується з Jira.");
        System.out.println("Codeberg, Gitea, SourceHut — менш популярнi альтернативи.");

        System.out.println();

        // === Блок 2: типовий робочий день ===
        // Сценарiй: послiдовнiсть команд, з якою працює розробник щодня.
        System.out.println("=== Типовий робочий день розробника ===");
        System.out.println("Ранок:");
        System.out.println("  git pull                    # забрати, що колеги залили вночi");
        System.out.println();
        System.out.println("День (пишемо код):");
        System.out.println("  git status                  # подивитись, що змiнили");
        System.out.println("  git add -A                  # додати все у staged");
        System.out.println("  git commit -m \"feat: ...\"   # зробити комiт");
        System.out.println();
        System.out.println("Вечiр:");
        System.out.println("  git pull                    # ще раз забрати свiжi змiни");
        System.out.println("  git push                    # вiдправити свої на сервер");

        System.out.println();

        // === Блок 3: git clone ===
        // Сценарiй: як скачати проєкт з GitHub.
        System.out.println("=== git clone — скачати проєкт iз сервера ===");
        System.out.println();
        System.out.println("  git clone https://github.com/user/repo.git");
        System.out.println();
        System.out.println("Створить папку 'repo' iз повною копiєю проєкту + iсторiєю.");
        System.out.println("Усерединi вже налаштовано remote 'origin' — нiчого додатково не треба.");

        System.out.println();

        // === Блок 4: pull i push ===
        // Сценарiй: основнi двi команди для синхронiзацiї.
        System.out.println("=== git pull (взяти змiни) i git push (вiдправити) ===");
        System.out.println();
        System.out.println("  git pull");
        System.out.println("    = git fetch (взяти з сервера) + git merge (влити у локальну гiлку)");
        System.out.println();
        System.out.println("  git push");
        System.out.println("    вiдправляє вашi локальнi комiти на сервер.");
        System.out.println();
        System.out.println("  git push -u origin feature/login");
        System.out.println("    -u запам'ятовує зв'язок: 'ця локальна гiлка <-> ця на серверi'.");
        System.out.println("    Пiсля цього досить просто 'git push' / 'git pull'.");

        System.out.println();

        // === Блок 5: початок нового проєкту з GitHub ===
        // Сценарiй: ви створили проєкт локально i хочете вiдправити на сервер.
        System.out.println("=== Пов'язати iснуючий локальний проєкт iз GitHub ===");
        System.out.println("1) На сайтi github.com -> New repository -> 'my-app' (без README, без .gitignore)");
        System.out.println("2) Локально у папцi проєкту:");
        System.out.println();
        System.out.println("  git init                                            # якщо ще не init");
        System.out.println("  git add -A");
        System.out.println("  git commit -m \"Initial commit\"");
        System.out.println("  git remote add origin https://github.com/USER/my-app.git");
        System.out.println("  git branch -M main                                  # перейменувати master->main");
        System.out.println("  git push -u origin main");

        System.out.println();

        // === Блок 6: типовi проблеми ===
        // Сценарiй: те, з чим стикається кожен новачок.
        System.out.println("=== Типовi проблеми ===");
        System.out.println();
        System.out.println("'rejected non-fast-forward' при push:");
        System.out.println("  -> на серверi є комiти, яких немає у вас. Спочатку git pull.");
        System.out.println();
        System.out.println("'fatal: Authentication failed':");
        System.out.println("  -> GitHub з 2021 року бiльше НЕ приймає пароль. Створiть Personal Access Token");
        System.out.println("     у Settings -> Developer settings -> Personal access tokens.");
        System.out.println();
        System.out.println("Конфлiкти при pull:");
        System.out.println("  -> вирiшуються так само, як при merge (див. Example08).");
    }
}
