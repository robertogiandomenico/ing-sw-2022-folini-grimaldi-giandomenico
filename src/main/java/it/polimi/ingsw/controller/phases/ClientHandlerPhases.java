package it.polimi.ingsw.controller.phases;

/**
 * This enumeration lists all the possible phases for the
 * {@link it.polimi.ingsw.network.server.ClientHandler Client Handler}.
 */
public enum ClientHandlerPhases {
    WAITING_IN_LOBBY,
    WAITING_GAMENAME,
    WAITING_GAMEMODE,
    WAITING_PLAYERNUMBER,
    WAITING_NICKNAME,
    WAITING_WIZARD,
    WAITING_PHASE_CHANGE,
    WAITING_ASSISTANT,
    WAITING_ACTION,
}
