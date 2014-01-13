package game;

import org.quickserver.net.server.ClientData;

public class PlayerData implements ClientData {
    public static final int THIEF = 0;
    public static final int POLICE = 1;

    public static final int WALK = 1;
    public static final int CAR = 2;
    public static final int MAX_OVERTIMES = 3;

    private String  playerID;

    private int     overtimeTimes;

    private int     score;
    private int     role;
    private int     trafficType;

    private boolean finished;
    private boolean rejected;

    private String  position;

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getPlayerID() {
        return playerID;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public PlayerData() {
        this.overtimeTimes = 0;
        this.score = 0;
        this.trafficType = WALK;
        this.rejected = false;
        this.playerID = "";
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(int trafficType) {
        this.trafficType = trafficType;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getOvertimeTimes() {
        return overtimeTimes;
    }

    public void setOvertimeTimes(int overtimeTimes) {
        this.overtimeTimes = overtimeTimes;
    }

    public void increaseOvertimeTimes(){
        this.overtimeTimes++;
    }

    public void clearOvertimeTimes(){
        this.overtimeTimes = 0;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
