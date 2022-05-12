package it.polimi.ingsw.view.utilities;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TowerColor;
import it.polimi.ingsw.model.Wizard;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is useful to contain player's information. (CLI/GUI)
 */
public class LightPlayer {
    private final String nickname;
    private int coins;
    private List<Assistant> cards;
    private final TowerColor towerColor;
    private Assistant discardPile;
    private final Wizard selectedWizard;

    /**
     * Class constructor.
     * @param p            the Player to simplify.
     */
    LightPlayer(Player p){
        this.nickname = p.getNickname();
        this.coins = p.getCoins();
        cards = new ArrayList<>(p.getCards());
        this.towerColor = p.getTowerColor();
        this.discardPile = p.getDiscardPile();
        this.selectedWizard = p.getSelectedWizard();
    }


    /**
     * Returns this player's nickname.
     *
     * @return             the nickname of this Player.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Returns this player's coins.
     *
     * @return             the coins of this Player.
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Returns this player's assistant cards.
     *
     * @return             the Assistant List of this Player's cards.
     */
    public List<Assistant> getCards() {
        return cards;
    }

    /**
     * Returns the color of this player's towers.
     *
     * @return             the TowerColor of this Player.
     */
    public TowerColor getTowerColor() {
        return towerColor;
    }

    /**
     * Returns the last assistant card this player chose.
     *
     * @return             the Assistant lastly chosen by this Player.
     */
    public Assistant getDiscardPile() {
        return discardPile;
    }

    /**
     * Returns this player's wizard.
     *
     * @return             the Wizard chosen by this Player.
     */
    public Wizard getSelectedWizard() {
        return selectedWizard;
    }
}
