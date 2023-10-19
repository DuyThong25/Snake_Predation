package snakepredation.DataHolder_Singleton;

public class DataHolder {

    private static DataHolder instance = new DataHolder();

    private int playerID;
    private int playerID2;
    private int gameID;

    private DataHolder() {
    }

    public static DataHolder getInstance() {
        return instance;
    }

    public static DataHolder getInstance2() {
        return instance;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getPlayerID2() {
        return playerID2;
    }

    public void setPlayerID2(int playerID2) {
        this.playerID2 = playerID2;
    }
    
     public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
