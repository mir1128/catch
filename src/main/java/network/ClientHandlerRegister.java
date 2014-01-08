package network;

import java.util.Observer;
import java.util.Vector;

public class ClientHandlerRegister {
    private static ClientHandlerRegister instance = null;

    private Vector<Observer> obs = new Vector<Observer>();

    public Vector<Observer> getObs() {
        return obs;
    }

    private ClientHandlerRegister(){}

    public static ClientHandlerRegister getInstance(){
        if (instance == null){
            instance = new ClientHandlerRegister();
        }
        return instance;
    }

    public void register(Observer o){
        obs.add(o);
    }
}
