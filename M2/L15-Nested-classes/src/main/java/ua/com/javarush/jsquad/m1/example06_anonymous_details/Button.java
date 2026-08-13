package ua.com.javarush.jsquad.m1.example06_anonymous_details;

/**
 * Проста "кнопка": знає свою назву і того, кого повідомити про натискання.
 */
public class Button {

    private final String title;
    private ClickListener listener;

    public Button(String title) {
        this.title = title;
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    /** Імітація натискання користувачем. */
    public void click() {
        if (listener != null) {
            listener.onClick(title);
        } else {
            System.out.println("   Кнопка «" + title + "» натиснута, але слухача немає");
        }
    }
}
