package game.protocol;

import game.PlayerData;
import game.PlayersDataHolder;
import game.logic.GameDataCenter;
import game.logic.GameStatus;
import game.logic.PoliceStepFinishedListener;
import map.MapHolder;
import net.sf.json.JSONObject;
import network.ClientMessageHandler;

import java.util.Map;

public class PoliceTrafficTypeHandler implements  ProtocolMessageHandler, PoliceStepFinishedListener {
    private static final String MsgID = "301";
    private static final String ReplyID = "102";

    @Override
    public boolean handle(ClientMessageHandler clientMessageHandler, String command) {
        if (GameStatus.getInstance().getCurrentGameStatus() != GameStatus.WAIT_POLICE_TRACE_INFO){
            clientMessageHandler.sendClientMessage("status error.");
            return false;
        }

        JSONObject jsonObject = JSONObject.fromObject(command);
        if (!jsonObject.getString("MsgID").equals(MsgID)){
            return false;
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
    public void onPoliceStepFinished() {
        JSONObject replyObject = new JSONObject();
        replyObject.put("MsgID", ReplyID);

        JSONObject msgObject = new JSONObject();
        for (Map.Entry<String, PlayerData> e : PlayersDataHolder.getInstance().getPoliceData().entrySet()){
            msgObject.put(e.getKey(), e.getValue().getTrafficType() == PlayerData.WALK? "walk" : "car");
        }

        replyObject.put("Msg", msgObject);

        Map<String, ClientMessageHandler> policeData = PlayersDataHolder.getInstance().getPoliceHandlers();
        for (Map.Entry<String, ClientMessageHandler> e : policeData.entrySet()){
            e.getValue().sendClientMessage(replyObject.toString());
        }
    }
}
