package game;

import game.protocol.*;
import map.*;
import network.Server;
import org.apache.commons.cli.*;
import org.quickserver.net.AppException;


public class GameServer {
    MapGenerator mapGenerator;
    MapInfo mapInfo;
    Server  server;

    public void init(){
//        mapGenerator = new TextMapLoaderGenerator("src/main/resources/m1", new MapInfo());
        MapInfo mapInfo = new MapInfo();
        mapGenerator = new MapAutoGenerator();
        mapGenerator.setMapInfo(mapInfo);
        mapGenerator.generate(25);

        MapHolder.getInstance().setMapInfo(mapInfo);

        server = new Server();
        server.setClientCommandHandler("network.ClientMessageHandler");
        server.setPort(4321);

        realistAllHandlers();
    }

    public void  gameStart() throws AppException {
        server.startServer();
    }

    private void realistAllHandlers(){
        ClientProcessor.getInstance().RegisterProtocolHandler(new WelcomeOnConnectHandler());
        ClientProcessor.getInstance().RegisterProtocolHandler(new LostConnectHandler());
        ClientProcessor.getInstance().RegisterProtocolHandler(new PlayerIDInformationHandler());
        ClientProcessor.getInstance().RegisterProtocolHandler(new ThiefMovementHandler());
        ClientProcessor.getInstance().RegisterProtocolHandler(new PoliceTrafficTypeHandler());
        ClientProcessor.getInstance().RegisterProtocolHandler(new PoliceDeduceInformationHandler());
        ClientProcessor.getInstance().RegisterProtocolHandler(new PoliceProposalHandler());
        ClientProcessor.getInstance().RegisterProtocolHandler(new PoliceVoteInformationHandler());
    }

    public static void main(String[] args) throws ParseException {
        GameCommandLineParser.parseCommandLine(args);

        GameServer gameServer = new GameServer();
        gameServer.init();

        try {
            gameServer.gameStart();
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

}
