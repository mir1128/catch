package game;

import network.ClientMessageHandler;

import java.util.HashMap;
import java.util.Map;

public class PlayersDataHolder {
    private static PlayersDataHolder instance = null;
    private Map<String, PlayerData> playerData = new HashMap<String, PlayerData>();
    private Map<String, ClientMessageHandler> playerClientHandler = new HashMap<String, ClientMessageHandler>();

    private String thiefID;

    private PlayersDataHolder(){}

    public static PlayersDataHolder getInstance(){
        if (null == instance){
            instance = new PlayersDataHolder();
        }
        return instance;
    }

    public void addPlayerData(String playerID, ClientMessageHandler clientMessageHandler){
        PlayerData data =  (PlayerData)clientMessageHandler.getClientData();
        playerData.put(playerID, data);
        playerClientHandler.put(playerID, clientMessageHandler);

        if (data.getRole() == PlayerData.THIEF){
            this.thiefID = playerID;
        }
    }

    public Map<String, PlayerData> getPlayerData(){
        return playerData;
    }


    public PlayerData getPlayerData(String playerID){
        return playerData.get(playerID);
    }

    public ClientMessageHandler getClientMessageHandler(String playerID){
        return playerClientHandler.get(playerID);
    }

    public PlayerData getThiefData(){
        return playerData.get(thiefID);
    }

    public ClientMessageHandler getThiefHandler(){
        return playerClientHandler.get(thiefID);
    }

    public String getThiefID(){
        return thiefID;
    }

    public Map<String, PlayerData> getPoliceData(){
        Map<String, PlayerData> polices = new HashMap<String, PlayerData>();
        for (Map.Entry<String, PlayerData> entry : playerData.entrySet()){
            if (entry.getKey() != thiefID){
                polices.put(entry.getKey(), entry.getValue());
            }
        }
        return polices;
    }

    public Map<String, ClientMessageHandler> getPoliceHandlers(){
        Map<String, ClientMessageHandler> polices = new HashMap<String, ClientMessageHandler>();
        for (Map.Entry<String, ClientMessageHandler> entry : playerClientHandler.entrySet()){
            if (entry.getKey() != thiefID){
                polices.put(entry.getKey(), entry.getValue());
            }
        }
        return polices;
    }
}


