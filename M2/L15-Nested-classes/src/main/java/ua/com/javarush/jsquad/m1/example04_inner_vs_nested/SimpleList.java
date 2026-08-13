package ua.com.javarush.jsquad.m1.example04_inner_vs_nested;

import java.util.LinkedList;

/**
 * Найпростіший однозв'язний список — і в ньому обидва види класів одразу:
 *                   |
 *    [0] -> [1] -> [2] -> [3] -> [4]
 *
 *             |-------|                            [2]
 *    [0] -> [1] -  - [3] -> [4]
 * <ul>
 *   <li>{@code Node} — <b>вкладений</b> (static): вузлу байдуже, у якому саме
 *       списку він лежить, йому потрібні лише значення й посилання на наступний;</li>
 *   <li>{@code Cursor} — <b>внутрішній</b>: курсор безглуздий без конкретного
 *       списку, він читає його {@code head}, {@code size} і назву.</li>
 * </ul>
 *
 * <p>Це той самий підхід, що й у справжніх колекціях JDK:
 * {@code ArrayList.Itr} — внутрішній, {@code LinkedList.Node} — вкладений.</p>
 */
public class SimpleList {

    private final String listName;
    private Node head;
    private int size;

    public SimpleList(String listName) {
        this.listName = listName;
    }

    /**
     * ВКЛАДЕНИЙ клас: static, без прихованого посилання на список.
     * Мільйон вузлів — мільйон разів зекономлені 4-8 байт на посилання.
     */
    static class Node {
        private final String value;
        private Node next;

        Node(String value) {
            this.value = value;
        }

        // Так не можна — static-класу недоступні поля конкретного об'єкта:
        // void print() { System.out.println(listName); }   // помилка компіляції
    }

    /**
     * ВНУТРІШНІЙ клас: посилання на список (SimpleList.this) є завжди,
     * тому курсор бачить і head, і size, і listName.
     */
    public class Cursor {

        private Node current = head;      // читаємо приватне поле зовнішнього об'єкта

        public boolean hasNext() {
            return current != null;
        }

        public String next() {
            String value = current.value;
            current = current.next;
            return value;
        }

        /** Читає стан зовнішнього об'єкта САМЕ ЗАРАЗ — це живий зв'язок, а не копія. */
        public String describe() {
            return "курсор по списку «" + listName + "», у якому зараз " + size + " елем.";
        }
    }

    public void add(String value) {
        Node node = new Node(value);      // усередині зовнішнього класу — просто new Node()
        if (head == null) {
            head = node;
        } else {
            Node last = head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = node;
        }
        size++;
    }

    /** Фабрика курсорів: кожен курсор прив'язаний до цього об'єкта списку. */
    public Cursor cursor() {
        return new Cursor();
    }

    public int getSize() {
        return size;
    }
}
