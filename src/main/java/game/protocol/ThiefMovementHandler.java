package game.protocol;

import game.PlayerData;
import game.logic.GameProcessofListener;
import game.logic.GameProcessor;
import net.sf.json.JSONObject;
import network.ClientMessageHandler;

import java.util.Observable;

public class ThiefMovementHandler implements ProtocolMessageHandler, GameProcessofListener {
    private static final String MsgID = "201";
    private static final String ReplyID = "401";

    @Override
    public boolean handle(ClientMessageHandler clientMessageHandler, String command) {
        JSONObject jsonObject = JSONObject.fromObject(command);
        if (!jsonObject.getString("MsgID").equals(MsgID)){
            return false;
        }

        PlayerData playerData = (PlayerData) clientMessageHandler.getClientData();
        GameProcessor.getInstance().setRoundFinished(playerData.getPlayerID());

        String movement = jsonObject.getString("Msg");
        GameProcessor.getInstance().setPlayerMovement(playerData.getPlayerID(), movement);

        return true;
    }

    @Override
    public void onRoundFinished() {

    }
}

