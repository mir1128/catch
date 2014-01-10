package game.protocol;

import net.sf.json.JSONObject;
import network.ClientMessageHandler;

public class ThiefMovementHandler implements ProtocolMessageHandler {
    private static final String MsgID = "201";
    private static final String ReplyID = "401";

    @Override
    public boolean handle(ClientMessageHandler clientMessageHandler, String command) {
        JSONObject jsonObject = JSONObject.fromObject(command);
        if (!jsonObject.getString("MsgID").equals(MsgID)){
            return false;
        }

        return true;
    }
}
