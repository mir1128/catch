package game;

import junit.framework.TestCase;
import network.ClientMessageHandler;
import network.Server;
import network.SimpleSocketClient;

public class ClientProcesserTest extends TestCase{
    private Server server;
    private ClientMessageHandler clientMessageHandler;

    protected void setUp() throws Exception {
        server = new Server();
        server.setPort(4321);
    }

    protected void tearDown() throws Exception {
        server.stopServer();
    }

    public void testUpdate() throws Exception {
        server.setClientCommandHandler("network.ClientMessageHandler");
        server.startServer();

        SimpleSocketClient simpleSocketClient = new SimpleSocketClient("127.0.0.1", 4321);

        simpleSocketClient.sendMsg("hello");
        String recvMsg = simpleSocketClient.readMsg();
        simpleSocketClient.close();
    }
}
