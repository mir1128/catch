package game.protocol;

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

        jsonObject.clear();
        jsonObject.put("MsgID", ReplyID);

        clientMessageHandler.sendClientMessage(jsonObject.toString());
        System.out.println("PlayerIDInformationHandler " + command);
        return true;
    }

}
