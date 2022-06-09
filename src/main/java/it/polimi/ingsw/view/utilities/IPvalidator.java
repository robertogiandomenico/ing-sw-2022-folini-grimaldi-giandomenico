package it.polimi.ingsw.view.utilities;

/**
 * This class contains utility methods regarding IP validation
 * for game access in {@link it.polimi.ingsw.view.cli.CLI CLI} and
 * {@link it.polimi.ingsw.view.gui.GUI GUI}.
 */
public class IPvalidator {

    /**
     * States whether the given address is valid or not.
     *
     * @param address       the inserted IP address.
     * @return              a boolean whose value is:
     *                      <p>
     *                      -{@code true} if the address is valid;
     *                      </p><p>
     *                      -{@code false} otherwise.
     *                      </p>
     */
    public static boolean validateIP(String address) {
        String zeroTo255 = "([01]?\\d{1,2}|2[0-4]\\d|25[0-5])";
        String IP_REGEX = "^(" + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + ")$";
        return address.matches(IP_REGEX);
    }

}