package network;

import org.quickserver.net.server.ClientCommandHandler;
import org.quickserver.net.server.ClientData;
import org.quickserver.net.server.ClientHandler;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Observable;
import java.util.Observer;

public class ClientMessageHandler extends Observable implements ClientCommandHandler {
    private ClientHandler handler;

    {
        for (Observer o : ClientHandlerRegister.getInstance().getObs()){
            this.addObserver(o);
        }
    }

    public void sendClientMessage(String message){
        try {
            handler.sendClientMsg(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setHandler(ClientHandler handler) {
        this.handler = handler;
    }

    public ClientData getClientData(){
        return handler.getClientData();
    }


    private void handle(ClientHandler handler, String command) {
        setHandler(handler);
        setChanged();
        notifyObservers(command);
    }

    @Override
    public void handleCommand(ClientHandler handler, String command) throws SocketTimeoutException, IOException {
        handle(handler, command);
    }

    public void gotConnected(ClientHandler handler) throws SocketTimeoutException, IOException {
        handle(handler, SpecialMessageID.ON_CONNECTED);
    }

    public void lostConnection(ClientHandler handler)throws IOException {
        handle(handler, SpecialMessageID.LOST_CONNECTION);
    }

    public void closingConnection(ClientHandler handler) throws IOException {
        handle(handler, SpecialMessageID.ON_CLOSE);
    }
}
