package game.protocol;

import game.GameServer;
import junit.framework.Assert;
import junit.framework.TestCase;
import network.ClientMessageHandler;
import network.Server;
import network.SimpleSocketClient;
import network.SpecialMessageID;
import org.quickserver.net.AppException;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WelcomeOnConnectHandlerTest extends TestCase{
    private GameServer server;
    private SimpleSocketClient simpleSocketClient;

    protected void setUp() throws Exception {
        server = new GameServer();
        server.init();
        server.gameStart();
    }

    protected void tearDown() throws Exception {

    }


    public void testUpdate() throws AppException, IOException {
        simpleSocketClient = new SimpleSocketClient("127.0.0.1", 4321);

        String msg = simpleSocketClient.readMsg();

        Assert.assertTrue(msg.contains(WelcomeOnConnectHandler.MsgID));
        simpleSocketClient.close();
    }
}

