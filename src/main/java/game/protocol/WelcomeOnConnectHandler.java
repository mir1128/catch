package game.protocol;

import game.logic.GameDataCenter;
import game.logic.GameStatus;
import net.sf.json.JSONObject;
import network.ClientMessageHandler;
import network.SpecialMessageID;

public class WelcomeOnConnectHandler implements ProtocolMessageHandler {
    public static final String MsgID = "100";

    @Override
    public boolean handle(ClientMessageHandler clientMessageHandler, String command) {
        if (GameStatus.getInstance().getCurrentGameStatus() != GameStatus.WAIT_WELCOME){
            clientMessageHandler.sendClientMessage("status error!");
            return false;
        }

        if (command.equals(SpecialMessageID.ON_CONNECTED)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("MsgID", WelcomeOnConnectHandler.MsgID);
            jsonObject.put("Msg", "welcome");

            clientMessageHandler.sendClientMessage(jsonObject.toString());

            GameDataCenter.getInstance().increasePlayerAvailable();
            return true;
        }
        return false;
    }
}
