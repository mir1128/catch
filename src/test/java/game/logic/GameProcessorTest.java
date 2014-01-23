package game.logic;

import game.PlayerData;
import game.PlayersDataHolder;
import junit.framework.Assert;
import junit.framework.TestCase;
import network.ClientMessageHandler;
import org.quickserver.net.server.ClientHandler;

import java.util.Iterator;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameProcessorTest extends TestCase{

    private PlayerData buildAPlayer(String id){
        PlayerData playerData = new PlayerData();
        playerData.setPlayerID(id);
        playerData.setPosition("(10,10)");
        playerData.setRole(PlayerData.POLICE);
        return playerData;
    }

    protected void setUp() throws Exception {
        ClientHandler[] clientHandlers = new ClientHandler[6];

        PlayerData[] playersData = new PlayerData[6];

        ClientMessageHandler[] handlers = new ClientMessageHandler[6];
        for (int i = 0; i < 6; ++i){
            handlers[i] = new ClientMessageHandler();
            clientHandlers[i] = mock(ClientHandler.class);
            playersData[i] = buildAPlayer("100"+i);
            handlers[i].setHandler(clientHandlers[i]);

            when(clientHandlers[i].getClientData()).thenReturn(playersData[i]);

            PlayersDataHolder.getInstance().addPlayerData("100"+i, handlers[i]);
        }

        playersData[0].setRole(PlayerData.THIEF);
        playersData[0].setPosition("(2,3)");
    }

    public void testIsCurrentRoundFinishedWhenNobodyFinished() throws Exception {
        boolean isFinished = GameProcessor.getInstance().isCurrentRoundFinished();
        Assert.assertFalse(isFinished);
    }

    public void testIsCurrentRoundFinishedWhenAllPlayerFinished() throws Exception {
        Map<String, PlayerData> playersData = PlayersDataHolder.getInstance().getPoliceData();
        for (Map.Entry<String, PlayerData> entry : playersData.entrySet()){
            entry.getValue().setFinished(true);
        }

        boolean isFinished = GameProcessor.getInstance().isCurrentRoundFinished();
        Assert.assertTrue(isFinished);
    }

    public void testIsCurrentRoundFinishedWhenSomeBodyOverTimes() throws Exception {
        Map<String, PlayerData> playersData = PlayersDataHolder.getInstance().getPoliceData();

        for (Map.Entry<String, PlayerData> entry : playersData.entrySet()){
            if (!entry.getKey().equals("1001")){
                entry.getValue().setFinished(true);
            }else {
                entry.getValue().setRejected(true);
            }
        }

        boolean isFinished = GameProcessor.getInstance().isCurrentRoundFinished();
        Assert.assertTrue(isFinished);
    }

    public void testSetRoundFinished() throws Exception {
        GameProcessor.getInstance().startTimer();
        for (int i = 0; i < 6; ++i){
            GameProcessor.getInstance().setRoundFinished("100"+i);
        }

        Assert.assertTrue(GameProcessor.getInstance().isCurrentRoundFinished());
        Assert.assertEquals(GameProcessor.getInstance().getCurrentRound(), 1);
    }

    public void testStartTimer() throws Exception {

    }

    public void testResetTimer() throws Exception {

    }

    public void testSetPlayerMovement() throws Exception {

    }
}
