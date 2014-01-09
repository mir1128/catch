package game.protocol;

import game.GameRoleConfig;
import map.MapHolder;
import net.sf.json.JSONObject;
import network.ClientMessageHandler;

public class PlayerIDInformationHandler implements ProtocolMessageHandler {
    public static final String MsgID = "001";
    public static final String ReplyID = "101";

    @Override
    public boolean handle(ClientMessageHandler clientMessageHandler, String command) {
        JSONObject jsonObject = JSONObject.fromObject(command);
        if (!jsonObject.getString("MsgID").equals(MsgID)){
            return false;
        }

        JSONObject replyObject = new JSONObject();
        replyObject.put("MsgID", ReplyID);

        JSONObject msgObject = new JSONObject();

        if (GameRoleConfig.getInstance().getThief().equals(jsonObject.get("Msg"))){
            msgObject.put("role", "thief");
        }else if (GameRoleConfig.getInstance().getPolices().contains(jsonObject.get("Msg"))){
            msgObject.put("role", "police");
        }
        else {
            msgObject.put("role", "null");
        }

        fillMapInfo(msgObject);
        replyObject.put("Msg", msgObject);

        clientMessageHandler.sendClientMessage(replyObject.toString());
        return true;
    }

    private void fillMapInfo(JSONObject msgObject) {
        msgObject.put("size", MapHolder.getInstance().getMapInfo().getMapSize());
        msgObject.put("police_station", MapHolder.getInstance().getMapInfo().getPoliceStationPosition());
        msgObject.put("thief_position", MapHolder.getInstance().getMapInfo().getThiefPosition());
        msgObject.put("nodes", MapHolder.getInstance().getMapInfo().toMazeMap());
    }
}
