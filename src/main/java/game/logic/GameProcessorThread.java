package game.logic;

public class GameProcessorThread extends Thread {
    private static final int TIMEOUT = 2000*600;


    @Override
    public void run(){
        try {
            waitAllPlayerAvailable();

            GameStatus.getInstance().setCurrentGameStatus(GameStatus.WAIT_PLAYER_ID);
            GameDataCenter.getInstance().resetPlayerAvailable();

            waitAllPlayerAvailable();

            GameStatus.getInstance().setCurrentGameStatus(GameStatus.WAIT_POLICE_TRAFFIC_INFO);
            waitAndProcess(GameStatus.WAIT_POLICE_TRAFFIC_INFO);
            for (int i = 0; i < 200; ++i){
                for (int status = GameStatus.WAIT_POLICE_DEDUCE_INFO; status  <= GameStatus.WAIT_PLAYER_MOVEMENT_INFO; ++status){
                    GameStatus.getInstance().setCurrentGameStatus(status);
                    waitAndProcess(status);
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void waitAllPlayerAvailable() throws InterruptedException {
        GameDataCenter instance = GameDataCenter.getInstance();
        synchronized (instance){
            instance.wait();
        }
    }

    private boolean isPoliceOnlyStep(int status){
        return GameStatus.WAIT_POLICE_TRAFFIC_INFO <= status && status < GameStatus.WAIT_PLAYER_MOVEMENT_INFO;
    }

    private void waitAndProcess(int status) throws InterruptedException {
        if (isTimeOut()){
            GameDataCenter.getInstance().processTimeout(isPoliceOnlyStep(status));
        }

        GameDataCenter.getInstance().sendCollectionMessageToAllPlayers(status, isPoliceOnlyStep(status));
    }

    private boolean isTimeOut() throws InterruptedException {
        long start = System.currentTimeMillis ();
        GameDataCenter instance = GameDataCenter.getInstance();
        synchronized (instance){
            instance.wait(TIMEOUT);
        }
        return System.currentTimeMillis() - start > TIMEOUT;
    }
}
