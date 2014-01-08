package network;

import org.quickserver.net.server.ClientCommandHandler;
import org.quickserver.net.server.ClientHandler;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Random;

public class SpecificMessageReplier implements ClientCommandHandler {
    public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    public void handleCommand(ClientHandler handler, String s) throws SocketTimeoutException, IOException {
        if (s.equalsIgnoreCase("specific")) {
            StringBuffer stringBuffer = new StringBuffer();
            Random random = new Random();
            for (int i = 0;  i < 2049; ++i){
                stringBuffer.append(allChar.charAt(random.nextInt(allChar.length())));
            }

            handler.sendClientMsg(stringBuffer.toString());
        }
    }
}
