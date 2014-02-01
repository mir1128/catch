package game.logic;

import game.PlayerData;
import game.PlayersDataHolder;

import java.util.Map;
import java.util.Vector;

public class GameDataCenter {
    private static final GameDataCenter instance = new GameDataCenter();
    private Vector<GameDataListener> listeners = new Vector<GameDataListener>();

    private GameDataCenter() {}

    public synchronized static GameDataCenter getInstance(){
        return instance;
    }


    public void processTimeout() {
        for (Map.Entry<String, PlayerData> e : PlayersDataHolder.getInstance().getPlayerData().entrySet()){
            if (e.getValue().isRejected()){
                continue;
            }

            if (!e.getValue().isFinished()){
                e.getValue().increaseOvertimeTimes();
                if (e.getValue().getOvertimeTimes() >= PlayerData.MAX_OVERTIMES){
                    e.getValue().setRejected(true);
                }
            }
        }
    }

    public void sendCollectionMessageToAllPlayers(int status) {
        for (GameDataListener listener: listeners){
            listener.onRoundFinished(status);
        }
    }

    public void setPlayerIDFinished(String playerID) {
        PlayersDataHolder.getInstance().getPlayerData(playerID).setFinished(true);
        if (allPlayerFinished()){
            this.notifyAll();
        }
    }

    private boolean allPlayerFinished() {
        for (Map.Entry<String, PlayerData> e : PlayersDataHolder.getInstance().getPlayerData().entrySet()){
            if (e.getValue().isRejected()){
                continue;
            }

            if (!e.getValue().isFinished()){
                return false;
            }
        }
        return true;
    }
}
