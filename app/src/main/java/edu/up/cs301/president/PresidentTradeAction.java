package edu.up.cs301.president;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

public class PresidentTradeAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public PresidentTradeAction(GamePlayer player) {
        super(player);
    }
}
