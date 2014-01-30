package game.logic;

public class GameDataCenter {
    private static final GameDataCenter instance = new GameDataCenter();

    private GameDataCenter() {}

    public synchronized static GameDataCenter getInstance(){
        return instance;
    }


}
