package game;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import game.protocol.ProtocolMessageHandler;
import network.ClientHandlerRegister;
import network.ClientMessageHandler;
import sun.security.jca.GetInstance;

import java.util.*;

public class ClientProcessor implements Observer {
    private static  ClientProcessor instance = null;

    private Vector<ProtocolMessageHandler> handlers;


    protected ClientProcessor() {
        handlers = new Vector<ProtocolMessageHandler>();
        ClientHandlerRegister.getInstance().register(this);
        handlers = new Vector<ProtocolMessageHandler>();
    }

    public static ClientProcessor getInstance(){
        if (instance == null){
            instance = new  ClientProcessor();
        }

        return instance;
    }

    public void RegisterProtocolHandler(ProtocolMessageHandler protocolMessageHandler){
        handlers.add(protocolMessageHandler);
    }

    @Override
    public void update(Observable clientHandler, Object arg) {
        ClientMessageHandler clientMessageHandler  = (ClientMessageHandler)clientHandler;
        String command = (String)arg;

        for (ProtocolMessageHandler protocolMessageHandler : handlers){
            if (protocolMessageHandler.handle(clientMessageHandler, command)){
                break;
            }
        }
    }
}
