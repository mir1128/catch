package game;

import java.util.Vector;

public class GameRoleConfig {
    private Vector<String> polices;
    private String      thief;

    public GameRoleConfig() {
        this.polices = new Vector<String>();
    }

    public Vector<String> getPolices() {
        return polices;
    }

    public String getThief() {
        return thief;
    }


    public void addPolicePlayerID(String p) {
        polices.add(p);
    }

    public void addThiefPlayerID(String t) {
        this.thief = t;
    }


}
