package edu.up.cs301.president;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.president.CardInfo.Card;

public class PresidentLocalGame extends LocalGame {

    private PresidentState state;


    private static final int NUM_PLAYERS = 4;

    public PresidentLocalGame() {
        Log.i("SJLocalGame", "creating game");
        // create the state for the beginning of the game
        state = new PresidentState();
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        if(p == null){
            Log.i("PLocalGame.java","GamePlayer is null");
            return;
        }
        if(state == null){
            Log.i("PLocalGame.java", "Gamestate is null");
        }

        PresidentState playerState;
        for (int i = 0; i < this.players.length; i++) {
            if (this.players[i].equals(p)) {
                playerState = new PresidentState(state,i);
                p.sendInfo(playerState);
                break;
            }
        }
    }

    @Override
    protected boolean canMove(int playerIdx) {
        int whoseTurn = state.getTurn();
        if( playerIdx == whoseTurn ){
            return true;
        }
        return false;
    }

    @Override
    protected String checkIfGameOver() {
        int score = state.checkGame();
        if(score == -1) {
            return null;
        }
        return null; // TODO: need to print message for winner
    }

    @Override
    protected boolean makeMove(GameAction action) {
        if(action == null){
            Log.i("PresidentLocalGame.java", "action is null");
            return false;
        }
        if( action instanceof PresidentPassAction ) {
            int playerIdx = getPlayerIdx(action.getPlayer());
            state.pass(playerIdx);
            return true;
        }
        if ( action instanceof PresidentPlayAction ) {
            int playerIdx = getPlayerIdx(action.getPlayer());
            ArrayList<Card> temp = ((PresidentPlayAction) action).getCards();
            play(playerIdx, temp);
            return true;
        }
        if ( action instanceof PresidentTradeAction ) {
            // TODO need to add something
            return true;
        }
        return false; // TODO: need to change this
    }

    private void pass(int idx){
        state.pass(idx);
        // TODO add something
    }

    public void play(int idx, ArrayList<Card> temp) {
        if(state.getCurrentSet().size() != 0) {
            if (temp.get(0).getValue() > state.getCurrentSet().get(0).getValue()){
                state.getCurrentSet().clear();
                state.setCurrentSet(temp);
                for(int i = 0; i < state.getPlayers().get(idx).getHand().size();i++) {
                    if(state.getPlayers().get(idx).getHand().get(i) ==
                            temp.get(0)){
                        state.getPlayers().get(idx).getHand().remove(i);
                    }
                }
                state.nextPlayer();
                return;
            }
        }
        state.setCurrentSet(temp);
        for(int i = 0; i < state.getPlayers().get(idx).getHand().size();i++) {
            if(state.getPlayers().get(idx).getHand().get(i).getValue() ==
                    temp.get(0).getValue() &&
                    state.getPlayers().get(idx).getHand().get(i).getSuit() ==
                    temp.get(0).getSuit()){
                state.getPlayers().get(idx).getHand().remove(i);
            }
        }
        state.nextPlayer();
    }
}
