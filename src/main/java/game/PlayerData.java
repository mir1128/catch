package game;

import org.quickserver.net.server.ClientData;

public class PlayerData implements ClientData {
    public static final int THIEF = 0;
    public static final int POLICE = 1;

    public static final int WALK = 0;
    public static final int CAR = 1;

    private String  position;
    private int     overtimeTimes;
    private int     score;

    private int     role;
    private int     trafficType;

    public PlayerData() {
        this.overtimeTimes = 0;
        this.score = 0;
        this.trafficType = WALK;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
