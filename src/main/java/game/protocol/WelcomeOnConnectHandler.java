package game.protocol;

import net.sf.json.JSONObject;
import network.ClientMessageHandler;
import network.SpecialMessageID;

public class WelcomeOnConnectHandler implements ProtocolMessageHandler {
    private static final String MsgID = "001";

    public WelcomeOnConnectHandler() {
    }

    @Override
    public boolean handle(ClientMessageHandler clientMessageHandler, String command) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("MsgID", WelcomeOnConnectHandler.MsgID);
        jsonObject.put("Msg", "welcome");

        clientMessageHandler.sendClientMessage(jsonObject.toString());
        return true;
    }

}
