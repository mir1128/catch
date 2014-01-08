package game.protocol;

import network.ClientMessageHandler;

public interface ProtocolMessageHandler {
    boolean handle(ClientMessageHandler clientMessageHandler, String command);
}
