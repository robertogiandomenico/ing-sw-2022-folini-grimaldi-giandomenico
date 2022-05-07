package it.polimi.ingsw.view.cli;

/**
 * This enumeration lists the colors that can be used
 * to print text and backgrounds in the CLI.
 */

public enum CliColor {
    //Color string, color reset
    RESET("\033[0m"),
    RESET_LINE("\033[<1000>D"),
    CLEAR_ALL("\033[H\033[2J"),

    //Regular Colors
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    YELLOW("\033[0;33m"),
    BLUE("\033[0;34m"),
    PINK("\033[0;35m"),


    //Bold Colors
    BOLD("\033[1m"),
    BOLDRED("\033[1;31m"),
    BOLDGREEN("\033[1;32m"),
    BOLDYELLOW("\033[1;33m"),
    BOLDBLUE("\033[1;34m"),
    BOLDPINK("\033[1;35m"),

    //Background Colors
    BRED("\033[41m"),
    BGREEN("\033[42m"),
    BYELLOW("\033[43m"),
    BBLUE("\033[44m"),
    BPINK("\033[45m"),
    BLIGHT_GREEN("\033[92m"),
    BLIGHT_CYAN("\033[96m");

    private final String code;

    /**
     * Constructs each color.
     *
     * @param code         the code of the color.
     */
    CliColor(String code) {
        this.code = code;
    }

    /**
     * Returns the code of the color.
     *
     * @return              the code of the color.
     */
    @Override
    public String toString() {
        return code;
    }
}