package snakepredation.DataHolder_Singleton;

public class DataHolder {

    private static DataHolder instance = new DataHolder();

    private int playerID;

    private DataHolder() {
    }

    public static DataHolder getInstance() {
        return instance;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
}
