package game.protocol;

import game.PlayerData;
import game.PlayersDataHolder;
import game.logic.AllPlayerStepListener;
import game.logic.GameDataCenter;
import game.logic.GameStatus;
import map.MapHolder;
import net.sf.json.JSONObject;
import network.ClientMessageHandler;

import java.util.Map;

public class PoliceMovementHandler implements ProtocolMessageHandler, AllPlayerStepListener {
    private static final String MsgID = "305";
    private static final String ReplyID = "106";

    public PoliceMovementHandler() {
        GameDataCenter.getInstance().registerPlayerListener(this);
    }

    @Override
    public boolean handle(ClientMessageHandler clientMessageHandler, String command) {
        if (GameStatus.getInstance().getCurrentGameStatus() != GameStatus.WAIT_PLAYER_MOVEMENT_INFO){
            clientMessageHandler.sendClientMessage("status error.");
            return false;
        }

        JSONObject jsonObject = JSONObject.fromObject(command);
        if (!jsonObject.getString("MsgID").equals(MsgID)){
            return false;
        }

        PlayerData playerData = (PlayerData) clientMessageHandler.getClientData();

        String movement = jsonObject.getString("Msg");
        GameDataCenter.getInstance().setPlayerMovement(playerData.getPlayerID(), movement);
        GameDataCenter.getInstance().setPlayerFinished(playerData.getPlayerID(), false);

        return true;
    }

    @Override
    public void onRoundFinished(int status) {
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
            msgObject.put("insight", GameDataCenter.getInstance().getPoliceInsight(e.getKey()));
            replyObject.put("Msg", msgObject);
            e.getValue().sendClientMessage(replyObject.toString());
        }
    }
}
