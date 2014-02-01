package game.protocol;

import game.PlayerData;
import game.PlayersDataHolder;
import game.logic.GameDataCenter;
import game.logic.PoliceStepFinishedListener;
import net.sf.json.JSONObject;
import network.ClientMessageHandler;

import java.util.Map;

public class PoliceDeduceInformationHandler implements ProtocolMessageHandler, PoliceStepFinishedListener{
    private static final String MsgID = "302";
    private static final String ReplyID = "103";

    public PoliceDeduceInformationHandler() {
        GameDataCenter.getInstance().registerPoliceListener(this);
    }

    @Override
    public boolean handle(ClientMessageHandler clientMessageHandler, String command) {
        JSONObject jsonObject = JSONObject.fromObject(command);
        if (!jsonObject.getString("MsgID").equals(MsgID)){
            return false;
        }

        String deduceMessage = jsonObject.getString("Msg");
        PlayerData playerData = (PlayerData) clientMessageHandler.getClientData();
        GameDataCenter.getInstance().setPlayerDeduceData(playerData.getPlayerID(), deduceMessage);
        GameDataCenter.getInstance().setPlayerFinished(playerData.getPlayerID(), true);

        return true;
    }

    @Override
    public void onPoliceStepFinished() {
        JSONObject replyObject = new JSONObject();
        replyObject.put("MsgID", ReplyID);

        JSONObject msgObject = new JSONObject();
        Map<String, String> deduceMessage = GameDataCenter.getInstance().getPoliceDeduceInformation();
        for (Map.Entry<String, String> e : deduceMessage.entrySet()){
            msgObject.put(e.getKey(), e.getValue());
        }

        replyObject.put("Msg", msgObject);

        Map<String, ClientMessageHandler> policeData = PlayersDataHolder.getInstance().getPoliceHandlers();
        for (Map.Entry<String, ClientMessageHandler> e : policeData.entrySet()){
            e.getValue().sendClientMessage(replyObject.toString());
        }
    }
}
