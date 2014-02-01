package game.logic;

public class GameProcessorThread extends Thread {
    private static final int TIMEOUT = 2000;


    @Override
    public void run(){
        try {
            waitAndProcess(GameStatus.WAIT_PLAYER_ID);
            waitAndProcess(GameStatus.WAIT_POLICE_TRAFFIC_INFO);
            for (int i = 0; i < 200; ++i){
                for (int status = GameStatus.WAIT_POLICE_TRACE_INFO; status  <= GameStatus.WAIT_PLAYER_MOVEMENT_INFO; ++status){
                    waitAndProcess(status);
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void waitAndProcess(int status) throws InterruptedException {
        if (isTimeOut()){
            GameDataCenter.getInstance().processTimeout();
        }
        GameDataCenter.getInstance().sendCollectionMessageToAllPlayers(status);
    }

    private boolean isTimeOut() throws InterruptedException {
        long start = System.currentTimeMillis ();
        GameDataCenter.getInstance().wait(TIMEOUT);
        return System.currentTimeMillis() - start > TIMEOUT;
    }
}
