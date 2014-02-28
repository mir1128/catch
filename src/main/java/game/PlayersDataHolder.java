package game;

import network.ClientMessageHandler;
import org.quickserver.net.server.ClientHandler;

import java.util.HashMap;
import java.util.Map;

public class PlayersDataHolder {
    private static PlayersDataHolder instance = null;
    private Map<String, PlayerData> playerData = new HashMap<String, PlayerData>();
    private Map<String, ClientHandler> playerClientHandler = new HashMap<String, ClientHandler>();

    private String thiefID;

    private PlayersDataHolder(){}

    public static PlayersDataHolder getInstance(){
        if (null == instance){
            instance = new PlayersDataHolder();
        }
        return instance;
    }

    public void addPlayerData(String playerID, ClientHandler clientHandler){
        PlayerData data =  (PlayerData)clientHandler.getClientData();
        playerData.put(playerID, data);
        playerClientHandler.put(playerID, clientHandler);

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

    public ClientHandler getClientMessageHandler(String playerID){
        return playerClientHandler.get(playerID);
    }

    public PlayerData getThiefData(){
        return playerData.get(thiefID);
    }

    public ClientHandler getThiefHandler(){
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

    public Map<String, ClientHandler> getPoliceHandlers(){
        Map<String, ClientHandler> polices = new HashMap<String, ClientHandler>();
        for (Map.Entry<String, ClientHandler> entry : playerClientHandler.entrySet()){
            if (entry.getKey() != thiefID){
                polices.put(entry.getKey(), entry.getValue());
            }
        }
        return polices;
    }
}


