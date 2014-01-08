package network;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.quickserver.net.AppException;

import java.io.IOException;

public class ServerTest extends TestCase{
    private Server server;
    private SimpleSocketClient simpleSocketClient;

    protected void setUp() throws Exception {
        server = new Server();
        server.setPort(4321);
    }

    protected void tearDown() throws Exception {
        server.stopServer();
    }

    public void testStartServer() throws Exception {
        server.setClientCommandHandler("network.ClientMessageHandler");
        server.startServer();

        simpleSocketClient = new SimpleSocketClient("127.0.0.1", 4321);
        String msg = simpleSocketClient.readMsg();
        Assert.assertEquals(msg.trim(), "hello: 127.0.0.1");

        simpleSocketClient.close();
    }

    public void testLongMsg() throws AppException, IOException {
        server.setClientCommandHandler("network.SpecificMessageReplier");
        server.startServer();
        simpleSocketClient = new SimpleSocketClient("127.0.0.1", 4321);

        simpleSocketClient.sendMsg("specific");
        String msg = simpleSocketClient.readMsg();
        Assert.assertEquals(msg.length(), 2051);

        simpleSocketClient.close();
    }
}

