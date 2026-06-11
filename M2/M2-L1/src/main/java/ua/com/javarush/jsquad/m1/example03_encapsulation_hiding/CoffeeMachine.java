package ua.com.javarush.jsquad.m1.example03_encapsulation_hiding;

/**
 * Кавомашина — приклад <b>приховування реалізації</b>.
 *
 * <p>Користувач натискає одну кнопку — {@code makeCappuccino()}. Усі складні
 * кроки (помел, нагрів води, збивання молока) приховані як {@code private}.
 * Для користувача ця інформація зайва — йому потрібен результат, а не процес.</p>
 *
 * <pre>
 *   Користувач бачить:           Усередині (приховано):
 *   ┌─────────────────┐          ┌──────────────────────────┐
 *   │ makeCappuccino()│ ──────▶  │ private grindBeans()     │
 *   │   (одна кнопка) │          │ private heatWater()      │
 *   │                 │          │ private frothMilk()      │
 *   └─────────────────┘          └──────────────────────────┘
 *        public                          private «нутрощі»
 * </pre>
 */
public class CoffeeMachine {

    // --- Приховані "нутрощі": користувач їх не бачить і не викликає ---
    private void grindBeans() {
        System.out.println("    [крок 1] мелемо зерна... ⚙");
    }

    private void heatWater() {
        System.out.println("    [крок 2] нагріваємо воду до 92°C... 🔥");
    }

    private void frothMilk() {
        System.out.println("    [крок 3] збиваємо молоко... 🥛");
    }

    // --- Публічний інтерфейс: одна проста кнопка для користувача ---
    public void makeCappuccino() {
        System.out.println("  ▶ Натиснуто кнопку \"Капучино\"");
        grindBeans(); // ці виклики сховані від зовнішнього світу
        heatWater();
        frothMilk();
        System.out.println("  ✔ Капучино готове! ☕");
    }
}
