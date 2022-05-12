package it.polimi.ingsw.network.messages;

/**
 * This enumeration lists all the message type available and used by the server and clients.
 */
public enum MessageType {
    GAMENAME_REQUEST, GAMENAME_REPLY,
    GAMEMODE_REQUEST, GAMEMODE_REPLY,
    PLAYERNUMBER_REQUEST, PLAYERNUMBER_REPLY,
    NICKNAME_REQUEST, NICKNAME_REPLY,
    WIZARD_REQUEST, WIZARD_REPLY,

    PHASE_ENTERING,
    START_TURN,
    CHOOSE_ASSISTANT_REQUEST, CHOOSE_ASSISTANT_REPLY,

    ACTION_REQUEST, ACTION_REPLY,
    STUDENT_REQUEST, STUDENT_REPLY,
    PLACE_REQUEST, PLACE_REPLY,
    CHARACTER_REQUEST, CHARACTER_REPLY,

    MNSTEPS_REQUEST, MNSTEPS_REPLY,

    CLOUD_REQUEST, CLOUD_REPLY,
    END_TURN,

    RESULT,

    //utility messages:
    PING,
    DISCONNECTION,
    TEXT
}