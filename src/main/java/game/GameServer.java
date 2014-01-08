package game;

import game.protocol.WelcomeOnConnectHandler;
import map.MapGenerator;
import map.MapInfo;
import map.TextMapLoaderGenerator;
import network.Server;
import org.quickserver.net.AppException;

import java.util.HashMap;

public class GameServer {
    MapGenerator mapGenerator;
    MapInfo mapInfo;
    Server  server;

    public void init(){

        mapGenerator = new TextMapLoaderGenerator("src/main/resources/m1", new MapInfo());
        mapInfo = mapGenerator.generate(11);
        server = new Server();
        server.setClientCommandHandler("network.ClientMessageHandler");

        ClientProcessor.getInstance().RegisterProtocolHandler(new WelcomeOnConnectHandler());

    }

    public void  gameStart() throws AppException {
        server.startServer();
    }

    private void realistAllHandlers(){
        ClientProcessor.getInstance().RegisterProtocolHandler(new WelcomeOnConnectHandler());
    }

    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.init();

        try {
            gameServer.gameStart();
        } catch (AppException e) {
            e.printStackTrace();
        }
    }
}
