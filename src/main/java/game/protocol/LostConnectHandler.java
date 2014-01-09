package game.protocol;

import net.sf.json.JSONObject;
import network.ClientMessageHandler;
import network.SpecialMessageID;

public class LostConnectHandler implements ProtocolMessageHandler {
    @Override
    public boolean handle(ClientMessageHandler clientMessageHandler, String command) {
        if (!command.equalsIgnoreCase(SpecialMessageID.LOST_CONNECTION)){
            return false;
        }
        return true;
    }
}
