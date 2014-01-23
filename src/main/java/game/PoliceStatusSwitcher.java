package game;

public class PoliceStatusSwitcher {
    public static int NONE = 0;
    public static int REPORT_TRAFFIC_TYPE_FINISHED = 1;
    public static int REPORT_THIEF_INFO_FINISHED = 2;
    public static int REPORT_PROPOSAL_FINISHED = 3;
    public static int REPORT_VOTE_FINISHED = 4;
    public static int ROUND_FINISHED = 5;

    private int currentStatus = 0;

    public void nextStatus(){
        currentStatus = (currentStatus + 1) % 6;
    }

    public int getCurrentStatus(){
        return currentStatus;
    }

    public void resetStatus(){
        currentStatus = NONE;
    }
}
