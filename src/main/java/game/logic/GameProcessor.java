package game.logic;

import game.PlayerData;
import game.PlayersDataHolder;
import game.PoliceStatusSwitcher;
import map.MapHolder;
import map.MapInfo;

import java.util.*;

public class GameProcessor {
    private static final int TIME_ROUND = 3000;
    private Timer timer = new Timer();

    private static GameProcessor instance = null;

    private Vector<GameProcessorListener> listeners = new Vector<GameProcessorListener>();
    private Vector<PoliceStepFinishedListener> policeListeners = new Vector<PoliceStepFinishedListener>();


    private PoliceStatusSwitcher policeStatusSwitcher = new PoliceStatusSwitcher();
    private Map<String, String> policeDeduceMessage = new HashMap<String, String>();
    private Map<String, String> policeProposalMessage = new HashMap<String, String>();
    private Map<String, String> policeVoteMessage = new HashMap<String, String>();

    private Map<Integer, String> thiefTrace = new HashMap<Integer, String>();

    private int currentRound = 0;

    public String getThiefTrace() {
        String traces = "";
        for (String trace : thiefTrace.values()){
            traces += trace+",";
        }
        return traces;
    }

    public void setThiefTrace(String thiefTrace) {
        for (Map.Entry<Integer, String> e : this.thiefTrace.entrySet()){
            if (e.getKey() - getCurrentRound() > 24){
                e.setValue("");
            }
        }
        this.thiefTrace.put(getCurrentRound(), thiefTrace);
    }

    public Map<String, String> getPoliceInsight(String policeID){
        PlayerData playerData = PlayersDataHolder.getInstance().getPlayerData(policeID);
        if (playerData.getRole() != PlayerData.POLICE){
            return null;
        }

        Map<String, String> result = new HashMap<String, String>();
        Vector<String> policeInsight = MapHolder.getInstance().getMapInfo().getInsight(playerData.getPosition(), playerData.getTrafficType());
        for (int i = 0; i < policeInsight.size(); ++i){
            if (PlayersDataHolder.getInstance().getThiefData().getPosition().equals(policeInsight.get(i))){
                result.put(policeInsight.get(i), "thief");
            }else if (isTraceFounded(policeInsight.get(i))){
                result.put(policeInsight.get(i), "trace");
            }else {
                result.put(policeInsight.get(i), "null");
            }
        }
        return result;
    }

    private boolean isTraceFounded(String position) {
        for (Map.Entry<Integer, String> e: thiefTrace.entrySet()){
            if (e.getValue().equals(position)){
                return true;
            }
        }
        return false;
    }

    public void registerGameProcessorListener(GameProcessorListener gameProcessorListener){
        if (!listeners.contains(gameProcessorListener)){
             listeners.add(gameProcessorListener);
        }
    }

    public void registerPoliceListeners(PoliceStepFinishedListener policeStepFinishedListener){
        if (!policeListeners.contains(policeStepFinishedListener)){
            policeListeners.add(policeStepFinishedListener);
        }
    }

    public int getCurrentRound() {
        return currentRound;
    }

    boolean isCurrentRoundFinished(){
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

    public void setRoundFinished(String playerID){
        PlayersDataHolder.getInstance().getPlayerData(playerID).setFinished(true);
        if (isCurrentRoundFinished()){
            notifyAllHandlers();
            increaseCurrentRound();
        }
    }


    private void notifyAllHandlers(){
        for (GameProcessorListener listener : listeners){
            listener.onRoundFinished();
        }
        onRoundFinished();
    }

    private void onRoundFinished() {
        clearPoliceStepData();
        if (getCurrentRound() % 8 == 0){
            setThiefTrace(MapHolder.getInstance().getMapInfo().getThiefPosition());
        }
        else {
            setThiefTrace(null);
        }
        averageBankMoney();
    }

    private void averageBankMoney() {
        Map<String, Integer> bankInfo = MapHolder.getInstance().getMapInfo().getBankInfo();
        Integer sum = 0;
        for (Map.Entry<String, Integer> e : bankInfo.entrySet()){
            sum += e.getValue();
        }

        for (Map.Entry<String, Integer> e : bankInfo.entrySet()){
            e.setValue(sum/bankInfo.size());
        }
    }

    private void clearPoliceStepData() {
        policeStatusSwitcher.resetStatus();
        for (String policeID : PlayersDataHolder.getInstance().getPoliceData().keySet()){
            policeDeduceMessage.put(policeID, "");
            policeProposalMessage.put(policeID, "");
            policeVoteMessage.put(policeID, "");
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

    public void startPoliceTimer(){
        timer.schedule(new PoliceTimeoutChecker(), TIME_ROUND);
    }

    public void resetPoliceTimer(){
        timer.cancel();
        timer.schedule(new PoliceTimeoutChecker(), TIME_ROUND);
    }

    public void setPlayerMovement(String playerID, String movement) {
        if (isMovementValid(playerID, movement)){
            updatePlayerPosition(playerID, movement);

            if (isBankRobbed(playerID, movement)){
                updateBankInfo(movement, 0);
            }
        }
    }

    private void updateBankInfo(String bankPosition, int money) {
        Map<String, Integer> bankInfo = MapHolder.getInstance().getMapInfo().getBankInfo();
        bankInfo.put(bankPosition, money);
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

    private void updatePlayerPosition(String playerID, String movement) {
        PlayersDataHolder.getInstance().getPlayerData(playerID).setPosition(movement);
    }

    private boolean isMovementValid(String playerID, String movement) {
        String currentPosition = PlayersDataHolder.getInstance().getPlayerData(playerID).getPosition();
        int trafficType = PlayersDataHolder.getInstance().getPlayerData(playerID).getTrafficType();

        MapInfo mapInfo = MapHolder.getInstance().getMapInfo();
        return mapInfo.isMovementValid(currentPosition, movement, trafficType);
    }

    public void setPoliceStepFinished(String playerID) {
        PlayersDataHolder.getInstance().getPlayerData(playerID).setPoliceNextStatus();

        if (isPoliceStepFinished()){
            notifyAllPoliceFinished();
            policeStatusSwitcher.nextStatus();
        }
    }

    private void notifyAllPoliceFinished() {
        for(PoliceStepFinishedListener policeStepFinishedListener : policeListeners){
            policeStepFinishedListener.onPoliceStepFinished();
        }
    }

    private boolean isPoliceStepFinished() {
        Map<String, PlayerData> policeData = PlayersDataHolder.getInstance().getPoliceData();
        try {
            for (Map.Entry<String, PlayerData> e : policeData.entrySet()){
                if (e.getValue().getPoliceCurrentStatus() != policeStatusSwitcher.getCurrentStatus()){
                    return false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public Map<String, String> getPoliceDeduceMessage() {
        return policeDeduceMessage;
    }

    public void setPlayerDeduceData(String playerID, String deduceMessage) {
        PlayerData playerData = PlayersDataHolder.getInstance().getPlayerData(playerID);
        if (playerData.getRole() == PlayerData.POLICE){
            policeDeduceMessage.put(playerID, deduceMessage);
        }
    }

    public Map<String, String> getPoliceProposalMessage() {
        return policeProposalMessage;
    }

    public void setPlayerProposalData(String playerID, String proposalMessage) {
        PlayerData playerData = PlayersDataHolder.getInstance().getPlayerData(playerID);
        if (playerData.getRole() == PlayerData.POLICE){
            policeProposalMessage.put(playerID, proposalMessage);
        }
    }

    public Map<String, String> getPoliceVoteMessage() {
        return policeVoteMessage;
    }

    public void setPlayerVoteData(String playerID, String voteMessage) {
        PlayerData playerData = PlayersDataHolder.getInstance().getPlayerData(playerID);
        if (playerData.getRole() == PlayerData.POLICE){
            policeVoteMessage.put(playerID, voteMessage);
        }
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

    class PoliceTimeoutChecker extends TimerTask{

        @Override
        public void run() {
            try {
                for (Map.Entry<String, PlayerData> e : PlayersDataHolder.getInstance().getPoliceData().entrySet()){
                    if (e.getValue().getPoliceCurrentStatus() != policeStatusSwitcher.getCurrentStatus()){
                        e.getValue().increaseOvertimeTimes();
                        e.getValue().setFinished(true);
                    }

                    if (e.getValue().getOvertimeTimes() >= PlayerData.MAX_OVERTIMES){
                        e.getValue().setRejected(true);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void increaseCurrentRound(){
        currentRound++;
        resetTimer();
    }
}

