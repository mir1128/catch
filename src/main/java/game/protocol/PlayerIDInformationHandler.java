package game.protocol;

import game.GameRoleConfig;
import game.PlayerData;
import game.PlayersDataHolder;
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
        String playerID = (String)jsonObject.get("Msg");

        JSONObject replyObject = buildReplyMessage((String) jsonObject.get("Msg"));
        fillPlayerData(playerID, (JSONObject) replyObject.get("Msg"), clientMessageHandler);

        clientMessageHandler.sendClientMessage(replyObject.toString());
        return true;
    }

    private JSONObject buildReplyMessage(String role) {
        JSONObject replyObject = new JSONObject();
        replyObject.put("MsgID", ReplyID);

        JSONObject msgObject = new JSONObject();
        if (GameRoleConfig.getInstance().getThief().equals(role)){
            msgObject.put("role", "thief");
        }else if (GameRoleConfig.getInstance().getPolices().contains(role)){
            msgObject.put("role", "police");
        }
        else {
            msgObject.put("role", "null");
        }

        fillMapInfo(msgObject);
        replyObject.put("Msg", msgObject);
        return replyObject;
    }

    private void fillPlayerData(String playerID, JSONObject msgObject, ClientMessageHandler clientMessageHandler) {
        PlayerData playerData = (PlayerData) clientMessageHandler.getClientData();

        boolean isPolice = msgObject.get("role").equals("police");
        playerData.setRole(isPolice?PlayerData.POLICE : PlayerData.THIEF);
        playerData.setPosition((String) (isPolice? msgObject.get("police_station"): msgObject.get("thief_position")));
    }

    private void fillMapInfo(JSONObject msgObject) {
        msgObject.put("size", MapHolder.getInstance().getMapInfo().getMapSize());
        msgObject.put("police_station", MapHolder.getInstance().getMapInfo().getPoliceStationPosition());
        msgObject.put("thief_position", MapHolder.getInstance().getMapInfo().getThiefPosition());
        msgObject.put("nodes", MapHolder.getInstance().getMapInfo().toMazeMap());
    }
}
