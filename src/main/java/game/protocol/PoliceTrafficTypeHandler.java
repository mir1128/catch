package game.protocol;

import game.PlayerData;
import game.PlayersDataHolder;
import game.logic.GameDataCenter;
import game.logic.GameStatus;
import game.logic.PoliceStepFinishedListener;
import map.MapHolder;
import net.sf.json.JSONObject;
import network.ClientMessageHandler;
import org.quickserver.net.server.ClientHandler;

import java.io.IOException;
import java.util.Map;

public class PoliceTrafficTypeHandler implements  ProtocolMessageHandler, PoliceStepFinishedListener {
    private static final String MsgID = "301";

    private static final String ReplyID = "102";

    public PoliceTrafficTypeHandler() {
        GameDataCenter.getInstance().registerPoliceListener(this);
    }

    @Override
    public boolean handle(ClientMessageHandler clientMessageHandler, String command) {
        JSONObject jsonObject = JSONObject.fromObject(command);
        if (!jsonObject.getString("MsgID").equals(MsgID)){
            return false;
        }

        if (GameStatus.getInstance().getCurrentGameStatus() != GameStatus.WAIT_POLICE_TRAFFIC_INFO){
            clientMessageHandler.sendClientMessage("PoliceTrafficTypeHandler status error. current status is " + GameStatus.getInstance().getCurrentGameStatus());
            return true;
        }

        PlayerData playerData = (PlayerData) clientMessageHandler.getClientData();
        String traffic = jsonObject.getString("Msg");

        if (playerData.getPosition().equals(MapHolder.getInstance().getMapInfo().getPoliceStationPosition())){
            playerData.setTrafficType(traffic.equalsIgnoreCase("walk")? PlayerData.WALK:PlayerData.CAR);
        }

        GameDataCenter.getInstance().setPlayerFinished(playerData.getPlayerID(), true);

        return true;
    }


    @Override
    public void onPoliceStepFinished(int status) {
        if (status != GameStatus.WAIT_POLICE_TRAFFIC_INFO){
            return;
        }
        JSONObject replyObject = new JSONObject();
        replyObject.put("MsgID", ReplyID);

        JSONObject msgObject = new JSONObject();
        for (Map.Entry<String, PlayerData> e : PlayersDataHolder.getInstance().getPoliceData().entrySet()){
            msgObject.put(e.getKey(), e.getValue().getTrafficType() == PlayerData.WALK? "walk" : "car");
        }

        replyObject.put("Msg", msgObject);

        Map<String, ClientHandler> policeData = PlayersDataHolder.getInstance().getPoliceHandlers();
        for (Map.Entry<String, ClientHandler> e : policeData.entrySet()){
            try {
                e.getValue().sendClientMsg(replyObject.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
