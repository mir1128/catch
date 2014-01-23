package game.protocol;

import game.GameServer;
import game.PlayerData;
import junit.framework.Assert;
import junit.framework.TestCase;
import network.ClientMessageHandler;
import network.SimpleSocketClient;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ThiefMovementHandlerTest extends TestCase{
    private GameServer server;
    private PlayerData playerData;
    private ClientMessageHandler clientMessageHandler;

    protected void setUp() throws Exception {
        server = new GameServer();
        server.init();

        clientMessageHandler = mock(ClientMessageHandler.class);
        playerData = new PlayerData();
        playerData.setPlayerID("1001");
        playerData.setRole(PlayerData.THIEF);
    }

    public void testHandle() throws Exception {
        when(clientMessageHandler.getClientData()).thenReturn(playerData);

        playerData.setPosition("(2,3)");
        String playerMsg = "{\"MsgID\" : \"201\", \"Msg\" : \"(2,2)\"}";

        ThiefMovementHandler thiefMovementHandler = new ThiefMovementHandler();
        thiefMovementHandler.handle(clientMessageHandler, playerMsg);
    }
}
