package game.logic;

import game.PlayerData;
import game.PlayersDataHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class GameDataCenter {
    private static final GameDataCenter instance = new GameDataCenter();
    private Vector<AllPlayerStepListener> listeners = new Vector<AllPlayerStepListener>();
    private Vector<PoliceStepFinishedListener> policeListeners = new Vector<PoliceStepFinishedListener>();

    private Map<String, String> policeDeduceInformation = new HashMap<String, String>();
    private Map<String, String> policeProposalInformation = new HashMap<String, String>();
    private Map<String, String> policeVoteInformation = new HashMap<String, String>();

    private GameDataCenter() {}

    public synchronized Map<String, String> getPoliceDeduceInformation() {
        return policeDeduceInformation;
    }

    public synchronized Map<String, String> getPoliceProposalInformation() {
        return policeProposalInformation;
    }

    public synchronized Map<String, String> getPoliceVoteInformation() {
        return policeVoteInformation;
    }

    public synchronized static GameDataCenter getInstance(){
        return instance;
    }

    public synchronized void setPlayerDeduceData(String playerID, String deduceInformation){
        policeDeduceInformation.put(playerID, deduceInformation);
    }

    public synchronized void setPlayerProposalInformation(String playerID, String proposalInformation){
        policeProposalInformation.put(playerID, proposalInformation);
    }

    public synchronized void setPlayerVoteInformation(String playerID, String voteInformation){
        policeVoteInformation.put(playerID, voteInformation);
    }

    public synchronized void processTimeout(boolean isPoliceStep) {
        for (Map.Entry<String, PlayerData> e : PlayersDataHolder.getInstance().getPlayerData().entrySet()){
            if (e.getValue().isRejected()){
                continue;
            }

            if (isPoliceStep && (e.getValue().getRole() == PlayerData.THIEF)){
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

    public synchronized void sendCollectionMessageToAllPlayers(int status, boolean isPoliceStep) {
        if (isPoliceStep){
            for (PoliceStepFinishedListener policeListener : policeListeners){
                policeListener.onPoliceStepFinished();
            }
        }
        else {
            for (AllPlayerStepListener listener: listeners){
                listener.onRoundFinished(status);
            }
        }
    }

    public synchronized void setPlayerFinished(String playerID, boolean isPoliceStep) {
        PlayersDataHolder.getInstance().getPlayerData(playerID).setFinished(true);
        if (allPlayerFinished(isPoliceStep)){
            this.notifyAll();
        }
    }

    public synchronized void registerPoliceListener(PoliceStepFinishedListener listener){
        policeListeners.add(listener);
    }

    public synchronized void registerPlayerListener(AllPlayerStepListener listener){
        listeners.add(listener);
    }

    private boolean allPlayerFinished(boolean isPoliceStep) {
        for (Map.Entry<String, PlayerData> e : PlayersDataHolder.getInstance().getPlayerData().entrySet()){
            if (e.getValue().isRejected()){
                continue;
            }

            if (isPoliceStep && (e.getValue().getRole() == PlayerData.THIEF)){
                continue;
            }

            if (!e.getValue().isFinished()){
                return false;
            }
        }
        return true;
    }

    public synchronized void setPlayerMovement(String playerID, String movement) {


    }

    public synchronized Object getPoliceInsight(String key) {
        return null;
    }
}
