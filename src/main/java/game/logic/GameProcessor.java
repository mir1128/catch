package game.logic;

import game.PlayerData;
import game.PlayersDataHolder;
import map.MapHolder;
import map.MapInfo;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class GameProcessor {
    private static final int TIME_ROUND = 3000;
    private Timer timer = new Timer();

    private static GameProcessor instance = null;

    private Vector<GameProcessofListener> listeners = new Vector<GameProcessofListener>();

    private int currentRound = 0;

    public int getCurrentRound() {
        return currentRound;
    }

    boolean isCurrentRoundFinished(){
        for (Map.Entry<String, PlayerData> e : PlayersDataHolder.getInstance().getPlayerData().entrySet()){
            if (!e.getValue().isFinished()){
                 return false;
            }
        }
        return true;
    }

    public void setRoundFinished(String playerID){
        PlayersDataHolder.getInstance().getPlayerData(playerID).setFinished(true);
        if (isCurrentRoundFinished()){
            notifyAllHandlers();
            increaseCurrentRound();
        }
    }


    private void notifyAllHandlers(){
        for (GameProcessofListener listener : listeners){
            listener.onRoundFinished();
        }
    }

    private GameProcessor(){
        this.currentRound = 0;
    }

    public static GameProcessor getInstance(){
        if (instance == null){
            instance = new GameProcessor();
        }
        return instance;
    }

    public void startTimer(){
        timer.schedule(new TimeoutChecker(), TIME_ROUND);
    }

    public void resetTimer(){
        timer.cancel();
        timer.schedule(new TimeoutChecker(), TIME_ROUND);
    }

    public void setPlayerMovement(String playerID, String movement) {
        if (isMovementValid(playerID, movement)){
            updatePlayerPosition(playerID, movement);
        }
    }

    private void updatePlayerPosition(String playerID, String movement) {
        PlayersDataHolder.getInstance().getPlayerData(playerID).setPosition(movement);
    }

    private boolean isMovementValid(String playerID, String movement) {
        String currentPosition = PlayersDataHolder.getInstance().getPlayerData(playerID).getPosition();
        int trafficType = PlayersDataHolder.getInstance().getPlayerData(playerID).getTrafficType();

        MapInfo mapInfo = MapHolder.getInstance().getMapInfo();
        return mapInfo.isMovementValid(currentPosition, movement, trafficType);
    }

    class TimeoutChecker extends TimerTask{

        @Override
        public void run() {
            for (Map.Entry<String, PlayerData> e : PlayersDataHolder.getInstance().getPlayerData().entrySet()){
                if (!e.getValue().isFinished()){
                     e.getValue().increaseOvertimeTimes();
                }

                if (e.getValue().getOvertimeTimes() >= PlayerData.MAX_OVERTIMES){
                    e.getValue().setRejected(true);
                }
            }
            notifyAllHandlers();
            increaseCurrentRound();
        }
    }

    private void increaseCurrentRound(){
        currentRound++;
        resetTimer();
    }
}

