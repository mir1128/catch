package game.logic;

public class GameStatus {
    public static final int WAIT_WELCOME = 0;
    public static final int WAIT_PLAYER_ID = 1;
    public static final int WAIT_POLICE_TRAFFIC_INFO = 2;
    public static final int WAIT_POLICE_TRACE_INFO = 3;
    public static final int WAIT_POLICE_DEDUCE_INFO = 4;
    public static final int WAIT_POLICE_PROPOSAL_INFO = 5;
    public static final int WAIT_POLICE_VOTE_INFO = 6;
    public static final int WAIT_PLAYER_MOVEMENT_INFO = 7;

    private int currentGameStatus;
    private static GameStatus instance = null;

    private GameStatus(){
        this.currentGameStatus = WAIT_WELCOME;
    }

    public synchronized static GameStatus getInstance(){
        if (instance == null){
            instance = new GameStatus();
        }
        return instance;
    }

    public synchronized int getCurrentGameStatus(){
        return currentGameStatus;
    }

    public synchronized void setCurrentGameStatus(int gameStatus){
        this.currentGameStatus = gameStatus;
    }
}

