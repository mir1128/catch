package game.protocol;

import game.PlayerData;
import game.PlayersDataHolder;
import game.logic.GameDataListener;
import map.MapHolder;
import net.sf.json.JSONObject;
import network.ClientMessageHandler;

import java.util.Map;

public class PoliceMovementHandler implements ProtocolMessageHandler, GameDataListener {
    private static final String MsgID = "305";
    private static final String ReplyID = "106";

    public PoliceMovementHandler() {
        GameProcessor.getInstance().registerGameProcessorListener(this);
    }

    @Override
    public boolean handle(ClientMessageHandler clientMessageHandler, String command) {
        JSONObject jsonObject = JSONObject.fromObject(command);
        if (!jsonObject.getString("MsgID").equals(MsgID)){
            return false;
        }

        PlayerData playerData = (PlayerData) clientMessageHandler.getClientData();

        String movement = jsonObject.getString("Msg");
        GameProcessor.getInstance().setPlayerMovement(playerData.getPlayerID(), movement);
        GameProcessor.getInstance().setRoundFinished(playerData.getPlayerID());

        return true;
    }

    @Override
    public void onRoundFinished() {
        JSONObject replyObject = new JSONObject();
        replyObject.put("MsgID", ReplyID);

        JSONObject msgObject = new JSONObject();

        JSONObject policePosition = new JSONObject();
        for (Map.Entry<String, PlayerData> e: PlayersDataHolder.getInstance().getPoliceData().entrySet()){
            policePosition.put(e.getKey(), e.getValue().getPosition());
        }
        msgObject.put("police_position", policePosition);

        JSONObject bankInfo = new JSONObject();
        for (Map.Entry<String, Integer> e : MapHolder.getInstance().getMapInfo().getBankInfo().entrySet()){
            bankInfo.put(e.getKey(), e.getValue());
        }
        msgObject.put("bank", bankInfo);

        Map<String, ClientMessageHandler> policeData = PlayersDataHolder.getInstance().getPoliceHandlers();
        for (Map.Entry<String, ClientMessageHandler> e : policeData.entrySet()){
            msgObject.put("insight", GameProcessor.getInstance().getPoliceInsight(e.getKey()));
            replyObject.put("Msg", msgObject);
            e.getValue().sendClientMessage(replyObject.toString());
        }
    }
}
