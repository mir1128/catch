package game.protocol;

import game.PlayerData;
import game.logic.PoliceStepFinishedListener;
import map.MapHolder;
import net.sf.json.JSONObject;
import network.ClientMessageHandler;

public class PoliceTrafficTypeHandler implements  ProtocolMessageHandler, PoliceStepFinishedListener {
    private static final String MsgID = "301";
    private static final String ReplyID = "102";

    @Override
    public boolean handle(ClientMessageHandler clientMessageHandler, String command) {
        JSONObject jsonObject = JSONObject.fromObject(command);
        if (!jsonObject.getString("MsgID").equals(MsgID)){
            return false;
        }

        PlayerData playerData = (PlayerData) clientMessageHandler.getClientData();
        String traffic = jsonObject.getString("Msg");

        if (playerData.getPosition().equals(MapHolder.getInstance().getMapInfo().getPoliceStationPosition())){
            playerData.setTrafficType(traffic.equalsIgnoreCase("walk")? PlayerData.WALK:PlayerData.CAR);
        }

        GameProcessor.getInstance().setPoliceStepFinished(playerData.getPlayerID());

        return true;
    }


    @Override
    public void onPoliceStepFinished() {

    }
}
