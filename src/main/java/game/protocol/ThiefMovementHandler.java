package game.protocol;

import game.PlayerData;
import game.PlayersDataHolder;
import game.logic.GameProcessorListener;
import game.logic.GameProcessor;
import map.MapHolder;
import net.sf.json.JSONObject;
import network.ClientMessageHandler;

import java.util.Map;

public class ThiefMovementHandler implements ProtocolMessageHandler, GameProcessorListener {
    private static final String MsgID = "201";
    private static final String ReplyID = "401";

    public ThiefMovementHandler() {
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
        msgObject.put("thief_position", MapHolder.getInstance().getMapInfo().getThiefPosition());

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

        replyObject.put("Msg", msgObject);
        PlayersDataHolder.getInstance().getThiefHandler().sendClientMessage(replyObject.toString());
    }
}

