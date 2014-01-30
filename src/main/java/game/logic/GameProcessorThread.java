package game.logic;

public class GameProcessorThread extends Thread {
    private static final int waitOverTimes = 2000;

    @Override
    public void run(){
        waitForPlayerIDInformation();

        waitForPoliceTrafficInformation();

        for (int i = 0; i < 200; ++i){
            waitForPoliceTraceInformation();
            waitForPoliceDeduceInformation();
            waitForPoliceProposalInformation();
            waitForPoliceVoteInformation();
            waitForAllPlayerMovementInformation();
        }
    }

    private void waitForAllPlayerMovementInformation() {
    }

    private void waitForPoliceVoteInformation() {
    }

    private void waitForPoliceProposalInformation() {
    }

    private void waitForPoliceDeduceInformation() {
    }

    private void waitForPoliceTraceInformation() {
    }

    private void waitForPoliceTrafficInformation() {
    }

    private void waitForPlayerIDInformation() {
    }
}
