package game;

import network.ClientMessageHandler;
import org.quickserver.net.server.ClientData;

import java.util.HashMap;
import java.util.Map;

public class PlayersDataHolder {
    private static PlayersDataHolder instance = null;
    private Map<String, ClientData> playerData = new HashMap<String, ClientData>();
    private Map<String, ClientMessageHandler> playerClientHandler = new HashMap<String, ClientMessageHandler>();

    private PlayersDataHolder(){}

    public static PlayersDataHolder getInstance(){
        if (null == instance){
            instance = new PlayersDataHolder();
        }
        return instance;
    }

    public void addPlayerData(String playerID, ClientMessageHandler clientMessageHandler){
        playerData.put(playerID, clientMessageHandler.getClientData());
        playerClientHandler.put(playerID, clientMessageHandler);
    }

    public ClientData getPlayerData(String playerID){
        return playerData.get(playerID);
    }

    public ClientMessageHandler getClientMessageHandler(String playerID){
        return playerClientHandler.get(playerID);
    }
}


