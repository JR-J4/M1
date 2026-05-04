package ua.com.javarush.jsquad.m1;

import java.util.Stack;

/**
 * Лекція 22: Винятки 2 — Стек (Stack)
 *
 * Стек (Stack) — це структура даних типу LIFO (Last In, First Out):
 * останній доданий елемент забирається першим.
 *
 * Основні методи Stack:
 *   push(T obj)         — додає елемент на вершину стека
 *   T pop()             — забирає елемент з вершини (стек зменшується)
 *   T peek()            — повертає верхній елемент (стек не змінюється)
 *   boolean empty()     — перевіряє, чи стек порожній
 *   int search(Object)  — шукає обʼєкт, повертає позицію від вершини (1-based)
 *
 * Аналогія з життя: стопка тарілок. Ставиш тарілку зверху (push),
 * берешь тарілку зверху (pop). Не можна взяти тарілку знизу,
 * не прибравши всі верхні.
 *
 * Реальне застосування: відміна дій (Undo), навігація «Назад» у браузері,
 * обчислення виразів, перевірка збалансованості дужок, виклики методів (call stack).
 */
public class Example03_StackDataStructure {

    public static void main(String[] args) {

        // ============================================================
        //   Блок 1: Основні операції Stack
        // ============================================================
        System.out.println("=== Блок 1: push, pop, peek ===");

        // Сценарій: стопка книг на столі студента
        Stack<String> books = new Stack<>();

        System.out.println("Стек порожній? " + books.empty()); // true

        books.push("Java для початківців");
        books.push("Чистий код");
        books.push("Алгоритми");
        System.out.println("Додали 3 книги: " + books);

        System.out.println("Верхня книга (peek): " + books.peek());     // Алгоритми
        System.out.println("Стек після peek: " + books);                  // не змінився

        String removed = books.pop();
        System.out.println("Забрали (pop): " + removed);                  // Алгоритми
        System.out.println("Стек після pop: " + books);

        System.out.println("Стек порожній? " + books.empty());           // false

        System.out.println();

        // ============================================================
        //   Блок 2: Метод search()
        // ============================================================
        System.out.println("=== Блок 2: search() ===");

        // Сценарій: шукаємо книгу в стопці
        Stack<String> stack = new Stack<>();
        stack.push("Фізика");
        stack.push("Математика");
        stack.push("Історія");
        stack.push("Хімія");

        System.out.println("Стек: " + stack);
        System.out.println("Позиція 'Хімія': " + stack.search("Хімія"));       // 1 (на вершині)
        System.out.println("Позиція 'Історія': " + stack.search("Історія"));   // 2
        System.out.println("Позиція 'Фізика': " + stack.search("Фізика"));     // 4 (на дні)
        System.out.println("Позиція 'Біологія': " + stack.search("Біологія")); // -1 (не знайдено)

        System.out.println();

        // ============================================================
        //   Блок 3: Stack для кнопки «Назад» у браузері
        // ============================================================
        System.out.println("=== Блок 3: Практика — історія браузера ===");

        // Сценарій: імітація кнопки «Назад» в браузері
        Stack<String> history = new Stack<>();

        // Користувач переходить по сторінках
        history.push("google.com");
        history.push("stackoverflow.com");
        history.push("github.com");
        history.push("javarush.com");

        System.out.println("Поточна сторінка: " + history.peek());

        // Натискає «Назад» 2 рази
        System.out.println("← Назад: покидаємо " + history.pop());
        System.out.println("Поточна: " + history.peek());

        System.out.println("← Назад: покидаємо " + history.pop());
        System.out.println("Поточна: " + history.peek());

        System.out.println();

        // ============================================================
        //   Блок 4: Stack для Undo
        // ============================================================
        System.out.println("=== Блок 4: Практика — скасування дій (Undo) ===");

        // Сценарій: текстовий редактор — кожна дія зберігається в стеку
        Stack<String> actions = new Stack<>();

        actions.push("Написав 'Привіт'");
        actions.push("Видалив слово");
        actions.push("Вставив картинку");
        actions.push("Змінив шрифт");

        System.out.println("Історія дій: " + actions);

        System.out.println("Скасувати (Undo): " + actions.pop());
        System.out.println("Скасувати (Undo): " + actions.pop());
        System.out.println("Залишились дії: " + actions);

        System.out.println();
        System.out.println("Stack — LIFO: останній прийшов — перший вийшов.");
        System.out.println("Саме так працює стек викликів методів у Java (call stack).");
    }
}
