package game;

public enum MessageID {
    S_C_CONNECTED,                  // client connected to server
    C_S_PLAYER_ID,                  // client send player id to server
    S_T_GAME_WORLD,                 // server send game world message to thief.
    S_P_GAME_WORLD,                 // server send game world message to police.
    T_S_MOVE,                       // thief send move info to server.
    P_S_MOVE_TYPE,                  // police send move type to server.
    S_P_OTHER_POLICE_INFO,          // server send other police info to police.
    P_S_THIEF_TRACE_INFO,           // police send thief trace info to server.
    S_P_THIEF_TRACE_INFO_COLLECTED, // server send collected thief trace info to other police.
    P_S_PROPOSAL,                   // police send proposal to server
    S_P_PROPOSAL_COLLECTED,         // server send collected proposal to polices.
    P_S_VOTE,                       // police send vote message to server.
    S_P_VOTE_RESULT,                // server send vote result to police.
    P_S_MOVE,                       // police send move info to server.
}



