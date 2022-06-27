package it.polimi.ingsw.controller.actions;

/**
 * This enumeration lists all the possible game turn actions.
 */
public enum ActionType {
    CHOOSE_ASSISTANT_ACTION ("Choose assistant"),
    MOVE_STUDENT_ACTION ("Move student"),
    MOVE_MN_ACTION ("Move mother nature"),
    SELECT_CLOUD_ACTION ("Choose cloud"),
    BUY_CHARACTER_ACTION ("Buy a character to use their effect"),
    NEXT_PHASE_ACTION ("Go to next phase if you don't want to buy a character");

    private final String action;

    /**
     * Constructs each action.
     *
     * @param action           the string of the Action.
     */
    ActionType(String action) {
        this.action = action;
    }

    /**
     * Returns the string of this action type.
     *
     * @return                 the string of the Action.
     */
    public String getAction() {
        return action;
    }
}
