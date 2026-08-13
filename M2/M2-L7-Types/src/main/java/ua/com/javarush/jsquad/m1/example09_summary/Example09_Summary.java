package ua.com.javarush.jsquad.m1.example09_summary;

/**
 * Модуль 2. Рівень 7. Приведення типів — ПІДСУМОК
 * <hr>
 * <h3>Тема: instanceof + приведення типів + switch expression разом</h3>
 *
 * <p>Один життєвий сценарій, у якому працюють усі теми лекції — <b>калькулятор фігур</b>.
 * Різні фігури складаємо в один масив загального типу й обробляємо в циклі:</p>
 *
 * <ol>
 *   <li><b>Розширення (upcasting):</b> {@code Figure[]} зберігає {@code Circle},
 *       {@code Rectangle}, {@code Square} — усі як {@code Figure}.</li>
 *   <li><b>instanceof зі зразком + звуження (downcasting):</b> щоб показати
 *       розміри конкретної фігури, перевіряємо тип і звужуємо.</li>
 *   <li><b>switch expression:</b> за назвою фігури повертаємо її категорію.</li>
 * </ol>
 *
 * <p><b>Увага до порядку:</b> {@code Square} успадкований від {@code Rectangle},
 * тож {@code square instanceof Rectangle} теж {@code true}. Тому конкретніший тип
 * ({@code Square}) перевіряємо ПЕРШИМ.</p>
 */
public class Example09_Summary {

    public static void main(String[] args) {

        // === 1. Розширення: різні фігури в одному масиві Figure[] ===
        System.out.println("=== Різні фігури в одному масиві Figure[] ===");
        Figure[] figures = {
                new Circle(3),
                new Rectangle(4, 5),
                new Square(2)
        };

        // === 2. Обробка: площа (поліморфізм) + деталі (instanceof + звуження) + категорія (switch) ===
        for (Figure figure : figures) {
            System.out.println(figure.getName() + ":");

            // площа рахується поліморфно — без жодного приведення типів
            System.out.printf("  площа = %.2f%n", figure.area());

            // instanceof зі зразком: конкретніший Square — перед Rectangle!
            if (figure instanceof Circle c) {
                System.out.println("  радіус = " + c.getRadius());
            } else if (figure instanceof Square s) {
                System.out.println("  сторона = " + s.getSide());
            } else if (figure instanceof Rectangle r) {
                System.out.println("  сторони = " + r.getWidth() + " x " + r.getHeight());
            }

            // switch expression: назва -> категорія фігури
            String category = switch (figure.getName()) {
                case "Коло" -> {
                    Circle circle = (Circle) figure;
                    yield "заокруглена фігура";
                }
                case "Квадрат", "Прямокутник" -> "чотирикутник";
                default -> "інша фігура";
            };

            FigureType type = figure.getType();

            Double s1 = switch (type) {
                case CIRCLE -> {
                    Circle circleCasted = (Circle) figure;
                    yield circleCasted.getRadius();
                }
                case Rectangle -> {
                    Rectangle rectangle1 = (Rectangle) figure;
                    yield rectangle1.getWidth();
                }
                case Square -> {
                    Square square1 = (Square) figure;
                    yield square1.getSide();
                }
              case NewFigure -> null;
            };

            FigureType typeToCheck = FigureType.CIRCLE;



            System.out.println("  категорія: " + category);
            System.out.println();
        }

        // === 3. Порахуємо сумарну площу всіх фігур ===
        System.out.println("=== Сумарна площа ===");
        double total = 0;
        for (Figure figure : figures) {
            total += figure.area();
        }
        System.out.printf("  разом = %.2f%n", total);
        System.out.println();

        System.out.println("Підсумок: upcasting збирає різне разом, instanceof+downcasting дістає деталі,");
        System.out.println("а switch expression компактно перетворює значення на результат.");
    }
}
