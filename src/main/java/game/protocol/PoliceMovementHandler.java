package game.protocol;

import net.sf.json.JSONObject;
import network.ClientMessageHandler;

public class PoliceMovementHandler implements ProtocolMessageHandler {
    private static final String MsgID = "305";
    private static final String ReplyID = "106";

    @Override
    public boolean handle(ClientMessageHandler clientMessageHandler, String command) {
        JSONObject jsonObject = JSONObject.fromObject(command);
        if (!jsonObject.getString("MsgID").equals(MsgID)){
            return false;
        }

        System.out.println("PoliceVoteInformationHandler " + command);
        return true;
    }
}
