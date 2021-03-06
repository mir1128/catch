package game.protocol;

import game.PlayerData;
import game.PlayersDataHolder;
import game.logic.GameDataCenter;
import game.logic.GameStatus;
import game.logic.PoliceStepFinishedListener;
import net.sf.json.JSONObject;
import network.ClientMessageHandler;
import org.quickserver.net.server.ClientHandler;

import java.io.IOException;
import java.util.Map;

public class PoliceProposalHandler implements ProtocolMessageHandler, PoliceStepFinishedListener {
    private static final String MsgID = "303";
    private static final String ReplyID = "104";

    public PoliceProposalHandler() {
        GameDataCenter.getInstance().registerPoliceListener(this);
    }

    @Override
    public boolean handle(ClientMessageHandler clientMessageHandler, String command) {
        JSONObject jsonObject = JSONObject.fromObject(command);
        if (!jsonObject.getString("MsgID").equals(MsgID)){
            return false;
        }

        if (GameStatus.getInstance().getCurrentGameStatus() != GameStatus.WAIT_POLICE_PROPOSAL_INFO){
            clientMessageHandler.sendClientMessage("PoliceProposalHandler status error. current status is " + GameStatus.getInstance().getCurrentGameStatus());
            return true;
        }

        String proposalMessage = jsonObject.getString("Msg");
        PlayerData playerData = (PlayerData) clientMessageHandler.getClientData();

        GameDataCenter.getInstance().setPlayerProposalInformation(playerData.getPlayerID(), proposalMessage);
        GameDataCenter.getInstance().setPlayerFinished(playerData.getPlayerID(), true);

        return true;
    }

    @Override
    public void onPoliceStepFinished(int status) {
        if (status != GameStatus.WAIT_POLICE_PROPOSAL_INFO){
            return;
        }

        JSONObject replyObject = new JSONObject();
        replyObject.put("MsgID", ReplyID);

        JSONObject msgObject = new JSONObject();
        Map<String, String> deduceMessage = GameDataCenter.getInstance().getPoliceProposalInformation();
        for (Map.Entry<String, String> e : deduceMessage.entrySet()){
            msgObject.put(e.getKey(), e.getValue());
        }

        replyObject.put("Msg", msgObject);

        Map<String, ClientHandler> policeData = PlayersDataHolder.getInstance().getPoliceHandlers();
        for (Map.Entry<String, ClientHandler> e : policeData.entrySet()){
            try {
                e.getValue().sendClientMsg(replyObject.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
