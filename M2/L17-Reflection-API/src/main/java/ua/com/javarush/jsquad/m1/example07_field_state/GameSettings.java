package ua.com.javarush.jsquad.m1.example07_field_state;

/**
 * Налаштування гри — по одному полю кожного примітивного типу
 * плюс посилальні. Саме на них зручно показати всю родину
 * {@code setByte()/setShort()/setInt()/.../set()}.
 */
public class GameSettings {

    public byte difficulty;        // 1..5
    public short maxPlayers;
    public int screenWidth;
    public long seed;
    public float volume;
    public double gameSpeed;
    public char controlKey;
    public boolean fullScreen;

    public String playerName;      // посилальний тип
    public int[] highScores;       // масив

    @Override
    public String toString() {
        return "GameSettings {\n"
                + "   difficulty  = " + difficulty + "\n"
                + "   maxPlayers  = " + maxPlayers + "\n"
                + "   screenWidth = " + screenWidth + "\n"
                + "   seed        = " + seed + "\n"
                + "   volume      = " + volume + "\n"
                + "   gameSpeed   = " + gameSpeed + "\n"
                + "   controlKey  = " + controlKey + "\n"
                + "   fullScreen  = " + fullScreen + "\n"
                + "   playerName  = " + playerName + "\n"
                + "   highScores  = " + java.util.Arrays.toString(highScores) + "\n"
                + "}";
    }
}
