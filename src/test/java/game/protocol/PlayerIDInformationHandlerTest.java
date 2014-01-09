package game.protocol;

import game.GameServer;
import junit.framework.Assert;
import junit.framework.TestCase;
import network.SimpleSocketClient;
import org.quickserver.net.AppException;

import java.io.IOException;

public class PlayerIDInformationHandlerTest extends TestCase{
    private GameServer server;
    private SimpleSocketClient simpleSocketClient;

    protected void setUp() throws Exception {
        server = new GameServer();
        server.init();
        server.gameStart();
    }

    public void testUpdate() throws AppException, IOException {
        simpleSocketClient = new SimpleSocketClient("127.0.0.1", 4321);

        String playerMsg = "{\"MsgID\" : \"001\"}";
        simpleSocketClient.sendMsg(playerMsg);
        String msg = simpleSocketClient.readMsg();
        msg = simpleSocketClient.readMsg();

        Assert.assertTrue(msg.contains(PlayerIDInformationHandler.ReplyID));
        simpleSocketClient.close();
    }
}
