package edu.up.cs301.president.Players;

import android.util.Log;

import java.util.ArrayList;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;
import edu.up.cs301.president.CardInfo.Card;
import edu.up.cs301.president.PresidentPassAction;
import edu.up.cs301.president.PresidentPlayAction;
import edu.up.cs301.president.PresidentState;

public class PresidentDumbAI extends GameComputerPlayer {

    private PresidentState savedState;
    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public PresidentDumbAI(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        sleep(1000);

        if(info == null) {
            Log.i("PresidentDumbAI", "info is null");
        }
        if(info instanceof NotYourTurnInfo){
            return;
        }
        if(info instanceof PresidentState){
            sleep(1000);
            if(Math.random() < 0.5){
                game.sendAction(new PresidentPassAction(this));
                savedState.getPlayers().get(this.playerNum).setPass();
            }
            else{
                savedState = (PresidentState) info;
                ArrayList<Card> temp = savedState.getPlayers().get(this.playerNum).getHand();
                Card t = getMax(temp);
                temp.clear();
                temp.add(t);
                if(savedState.getCurrentSet().get(0).getValue() > temp.get(0).getValue()){
                    game.sendAction(new PresidentPassAction(this));
                    savedState.getPlayers().get(this.playerNum).setPass();
                }
                else {
                    game.sendAction(new PresidentPlayAction(this, temp));
                }
            } //TODO: adding a card doesn't work or accessing the state?
        }
    }

    public Card getMax(ArrayList<Card> temp){
        Card c = new Card(-1, "Default");
        for(int i = 0; i < temp.size(); i++){
            if(c.getValue() < temp.get(i).getValue()){
                c.setCardSuit(temp.get(i).getSuit());
                c.setCardVal(temp.get(i).getValue());
            }
        }
        return c;
    }
}
