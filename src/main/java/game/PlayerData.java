package game;

import org.quickserver.net.server.ClientData;

public class PlayerData implements ClientData {
    private String  position;
    private int     overtimeTimes;
    private int     score;

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
