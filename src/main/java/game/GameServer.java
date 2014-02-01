package game;

import game.logic.GameProcessorThread;
import game.protocol.*;
import map.*;
import network.Server;
import org.apache.commons.cli.*;
import org.quickserver.net.AppException;

public class GameServer {
    MapGenerator mapGenerator;
    Server  server;

    public void init(){
        MapInfo mapInfo = new MapInfo();
        mapGenerator = new MapAutoGenerator();
        mapGenerator.setMapInfo(mapInfo);
        mapGenerator.generate(25);

        MapHolder.getInstance().setMapInfo(mapInfo);

        server = new Server();
        server.setClientCommandHandler("network.ClientMessageHandler");
        server.setClientData("game.PlayerData");
        server.setTimeout(1000*60*10);
        server.setTimeoutMsg("chao shi le");
        server.setPort(4321);

        realistAllHandlers();
    }

    public void  gameStart() throws AppException {
        server.startServer();
        GameProcessor.getInstance().startTimer();
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

        GameProcessorThread gameProcessorThread = new GameProcessorThread();
        gameProcessorThread.start();


        try {
            gameServer.gameStart();
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

}
