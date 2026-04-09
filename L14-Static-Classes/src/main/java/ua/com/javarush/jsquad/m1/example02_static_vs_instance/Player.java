package ua.com.javarush.jsquad.m1.example02_static_vs_instance;

/**
 * Клас Player — демонстрація різниці між статичними та нестатичними змінними.
 *
 * static totalPlayers — одна на весь клас (спільна для всіх гравців)
 * playerName, score   — у кожного об'єкта свої (нестатичні)
 */
public class Player {

    // Статична змінна — спільна для ВСІХ гравців
    public static int totalPlayers = 0;

    // Нестатичні змінні — у кожного гравця свої
    private String playerName;
    private int score;

    public Player(String playerName) {
        this.playerName = playerName;
        this.score = 0;
        totalPlayers++;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void showStats() {
        System.out.println("  " + playerName + " | Очки: " + score
                + " | Всього гравців у грі: " + totalPlayers);
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }
}
