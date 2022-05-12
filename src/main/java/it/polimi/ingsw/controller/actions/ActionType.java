package it.polimi.ingsw.controller.actions;

public enum ActionType {
    CHOOSE_ASSISTANT_ACTION ("Choose assistant"),
    MOVE_STUDENT_ACTION ("Move student"),
    MOVE_MN_ACTION ("Move mother nature"),
    SELECT_CLOUD_ACTION ("Choose cloud"),
    BUY_CHARACTER_ACTION ("Buy a character to use their effect"),
    END_TURN_ACTION ("End Turn");

    private final String action;

    /**
     * Constructs each action.
     *
     * @param action           the string of the action.
     */
    ActionType(String action) {
        this.action = action;
    }

    /**
     * Returns the string of this action type.
     *
     * @return                 the string of the action.
     */
    public String getAction() {
        return action;
    }
}
