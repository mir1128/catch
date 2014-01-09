package game.protocol;

import net.sf.json.JSONObject;
import network.ClientMessageHandler;

public class PoliceProposalHandler implements ProtocolMessageHandler {
    private static final String MsgID = "303";
    private static final String ReplyID = "104";

    @Override
    public boolean handle(ClientMessageHandler clientMessageHandler, String command) {
        JSONObject jsonObject = JSONObject.fromObject(command);
        if (!jsonObject.getString("MsgID").equals(MsgID)){
            return false;
        }

        System.out.println("PoliceProposalHandler " + command);
        return true;
    }
}
