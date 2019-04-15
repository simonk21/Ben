package edu.up.cs301.president.Players;

import android.util.Log;

import java.util.ArrayList;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;
import edu.up.cs301.president.CardInfo.Card;
import edu.up.cs301.president.PresidentPassAction;
import edu.up.cs301.president.PresidentPlayAction;
import edu.up.cs301.president.PresidentState;

public class PresidentSmartAI extends GameComputerPlayer {


    private PresidentState savedState;
    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public PresidentSmartAI(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        sleep(1000);
        if(info == null) {
            Log.i("PresidentSmartAI", "info is null");
        }
        if(info instanceof NotYourTurnInfo || info instanceof IllegalMoveInfo){
            return;
        }
        if(info instanceof PresidentState){
            savedState = (PresidentState) info;
            sleep(1000);
            ArrayList<Card> temp = savedState.getPlayers().get(this.playerNum).getHand();

            // Find the largest valued card in their hand
            Card max = getMax(temp);

            // find the lowest PLAYABLE
            // Has the potential to be null if there is no playable card in hand.
                Card minPlayable = getMinPlayable(temp);



            // Find the lowest valued Card in the players hand
            Card min = getMin(temp);

            // If the current set of cards is clear,
            // And the size of the players hand is less
            // than 6
            // Play the lowest card in its hand.
            if(savedState.getCurrentSet().size() == 0 && temp.size() < 6) {
                sleep(1000);
                temp.clear();
                temp.add(max);
                game.sendAction(new PresidentPlayAction(this, temp));

            // If the stack is just empty, then play the lowest possible card
            }else if(savedState.getCurrentSet().size() == 0){
                    sleep(1000);
                    temp.clear();
                    temp.add(min);
                    game.sendAction(new PresidentPlayAction(this, temp));

            // If hand size is lower than 5 cards
            // Play the highest valued card possible
            } else if(temp.size() < 6){
                sleep(1000);
                temp.clear();
                temp.add(max);
                // If the largest value card in your hand is still smaller
                // than the current sets card, pass the turn
                if(savedState.getCurrentSet().get(0).getValue() >= max.getValue()) {
                    // If you're highest valued card cant be played,
                    // Pass the turn. You can't play.

                    game.sendAction(new PresidentPassAction(this));
                }
                    // Play your highest valued card
                    game.sendAction(new PresidentPlayAction(this, temp));

            // MOST REGULAR TURN OPTION
            // Play the card in hand closest
            // to the one(s) on the stack
            } else {
                sleep(1000);
                temp.clear();

                if(minPlayable != null ) {
                    temp.add(minPlayable);
                }

                // If there is no playable card
                // Pass the turn
                if(temp.size() == 0 || minPlayable.getValue() == -1){
                    game.sendAction(new PresidentPassAction(this));
                } else {
                    game.sendAction(new PresidentPlayAction(this, temp));
                }

            }
        }
    }


    /**
     *  Function to get the highest valued card
     *  in the Players Hand
     */
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

    /**
     *  Function to get the lowest valued card
     *  in the Players Hand
     */
    public Card getMin(ArrayList<Card> temp){
        Card c = new Card(100, "Default");
        for(int i = 0; i < temp.size(); i++){
            if(c.getValue() > temp.get(i).getValue()){
                c.setCardSuit(temp.get(i).getSuit());
                c.setCardVal(temp.get(i).getValue());
            }
        }
        if(c.getValue() == 100 ){
            c.setCardSuit("Default");
            c.setCardVal(-1);
        }
        return c;
    }


    /**
     * Method that finds the lowest playable card
     * from the players hand and plays it.
     * @param temp
     * @return
     */
    public Card getMinPlayable(ArrayList<Card> temp){
        Card max = getMax(temp);
        ArrayList<Card> cardsBetweenMaxAndStack = new ArrayList<>();
        // For all the cards in the Computers Hand, if that
        // Card is in between their max card or the stack card
        // then add it to a hand to be sorted
        // find that hands minimum valued card
        // and return that card

        Card smallestPlayable = new Card(-1, "Default");

        if(savedState.getCurrentSet().size() != 0) {
            for (int i = 0; i < temp.size(); i++) {
                if (savedState.getCurrentSet().get(0).getValue() < temp.get(i).getValue()
                        && temp.get(i).getValue() <= max.getValue()) {
                    // Add that card to the temporary hand
                    cardsBetweenMaxAndStack.add(new Card(temp.get(i).getValue(), temp.get(i).getSuit()));
                }
            }
            smallestPlayable = getMin(cardsBetweenMaxAndStack);
        }
            return smallestPlayable;
    }
}
