package game;

import org.quickserver.net.server.ClientData;

import java.util.HashMap;
import java.util.Map;

public class PlayersDataHolder {
    private static PlayersDataHolder instance = null;
    private Map<String, ClientData> playerData = new HashMap<String, ClientData>();

    private PlayersDataHolder(){}

    public static PlayersDataHolder getInstance(){
        if (null == instance){
            instance = new PlayersDataHolder();
        }
        return instance;
    }

    public void addPlayerData(String playerID, ClientData clientData){
        playerData.put(playerID, clientData);
    }

    public ClientData getPlayerData(String playerID){
        return playerData.get(playerID);
    }
}


