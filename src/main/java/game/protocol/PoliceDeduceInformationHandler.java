package game.protocol;

import net.sf.json.JSONObject;
import network.ClientMessageHandler;

public class PoliceDeduceInformationHandler implements ProtocolMessageHandler {
    private static final String MsgID = "302";
    private static final String ReplyID = "103";

    @Override
    public boolean handle(ClientMessageHandler clientMessageHandler, String command) {
        JSONObject jsonObject = JSONObject.fromObject(command);
        if (!jsonObject.getString("MsgID").equals(MsgID)){
            return false;
        }

        System.out.println("PoliceDeduceInformationHandler " + command);
        return true;
    }
}
