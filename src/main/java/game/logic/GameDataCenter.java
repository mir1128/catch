package game.logic;

import game.PlayerData;
import game.PlayersDataHolder;
import map.MapHolder;
import map.MapInfo;

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

    private int playerAvailable = 0;

    public int getPlayerAvailable() {
        return playerAvailable;
    }

    public void increasePlayerAvailable() {
        this.playerAvailable++;
        if (playerAvailable >= 6){
            this.notifyAll();
        }
    }

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
        resetFinishInformation();
    }

    private void resetFinishInformation() {
        for (Map.Entry<String, PlayerData> e : PlayersDataHolder.getInstance().getPlayerData().entrySet()){
            e.getValue().setFinished(false);
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
        if (isMovementValid(playerID, movement)){
            updatePlayerPosition(playerID, movement);

            if (isBankRobbed(playerID, movement)){
                updateBankInfo(movement, 0);
            }
        }
    }

    public synchronized Object getPoliceInsight(String key) {
        return null;
    }

    private boolean isMovementValid(String playerID, String movement) {
        String currentPosition = PlayersDataHolder.getInstance().getPlayerData(playerID).getPosition();
        int trafficType = PlayersDataHolder.getInstance().getPlayerData(playerID).getTrafficType();

        MapInfo mapInfo = MapHolder.getInstance().getMapInfo();
        return mapInfo.isMovementValid(currentPosition, movement, trafficType);
    }

    private void updatePlayerPosition(String playerID, String movement) {
        PlayersDataHolder.getInstance().getPlayerData(playerID).setPosition(movement);
    }

    private boolean isBankRobbed(String playerID, String movement) {
        if (playerID != PlayersDataHolder.getInstance().getThiefID()){
            return false;
        }

        Map<String, Integer> bankInfo = MapHolder.getInstance().getMapInfo().getBankInfo();
        if (bankInfo.keySet().contains(movement)){
            return true;
        }
        return false;
    }

    private void updateBankInfo(String bankPosition, int money) {
        Map<String, Integer> bankInfo = MapHolder.getInstance().getMapInfo().getBankInfo();
        bankInfo.put(bankPosition, money);
    }
}
