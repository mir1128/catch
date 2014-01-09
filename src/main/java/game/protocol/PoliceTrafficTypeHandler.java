package game.protocol;

import net.sf.json.JSONObject;
import network.ClientMessageHandler;

public class PoliceTrafficTypeHandler implements  ProtocolMessageHandler{
    private static final String MsgID = "301";
    private static final String ReplyID = "102";

    @Override
    public boolean handle(ClientMessageHandler clientMessageHandler, String command) {
        JSONObject jsonObject = JSONObject.fromObject(command);
        if (!jsonObject.getString("MsgID").equals(MsgID)){
            return false;
        }

        System.out.println("PoliceTrafficTypeHandler " + command);
        return true;
    }
}
