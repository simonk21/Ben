package edu.up.cs301.president;

import android.util.Log;

import java.util.ArrayList;

import edu.up.cs301.game.infoMsg.GameState;
import edu.up.cs301.president.CardInfo.Card;
import edu.up.cs301.president.CardInfo.Deck;
import edu.up.cs301.president.CardInfo.Hand;

public class PresidentState extends GameState {

    // to satisfy Serializable interface
    private static final long serialVersionUID = 7737393762469851826L;

    // 52 card deck
    private Deck deck;
    private ArrayList<Card> currentSet;

    private int turn; // the current turn in game

    private int rankCount[];
    private int numRank;
    private int PassAll[];
    private int prev;

    private boolean roundStart;

    private ArrayList<PlayerTracker> players;

    public PresidentState() {
        deck = new Deck(); // initializes deck
        currentSet = new ArrayList<>(); // current set played

//        players = new ArrayList<>();
//        for(int i = 0; i < 4; i++){
//            players.add(new Hand());  // creates player's hand
//        }
        players = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            players.add(new PlayerTracker());
        }

        deck.deal(players); // deals cards (unsorted)

        turn = 0; //(int) (Math.random() * 4 + 1); // selects random player to start
//
//        players = new ArrayList<>();
//        for(int i = 0; i < 4; i++){
//            players.add(new PlayerTracker());
//        }

        rankCount = new int[4];
        PassAll = new int[4];
        for (int i = 0; i < rankCount.length; i++) {
            rankCount[i] = 0;
            PassAll[i] = 0;
        }
        numRank = 0;
        prev = -1;
        roundStart = false;
        startRound();
    }

    public PresidentState(PresidentState orig, int idx) {
        deck = orig.deck;
        currentSet = new ArrayList<>();
        for(int i = 0; i < orig.currentSet.size(); i++){
            currentSet.add(new Card(orig.getCurrentSet().get(i)));
        }
        turn = orig.turn;
        players = new ArrayList<>();
        for(int i = 0; i < orig.players.size(); i++){
            if(i == idx){
                players.add(new PlayerTracker(orig.players.get(i)));
            } else{
                players.add(new PlayerTracker());
            }
        }
    }

    public PresidentState(PresidentState orig){
        currentSet = orig.currentSet;
        turn = orig.turn;
        players = getPlayers();
    } //TODO: copies gamestate to each player but then erases the currentSet ?
    /**
     * startRound
     * if players got rid of hand
     * need to check if game is over, someone reached 11 pts.
     */
    public void startRound() {
        //currentSet.clear();
        if (checkGame() != -1) {
            // TODO: someone won
        } else {
            // TODO: then trade and change score
            // TODO: deal deck and restart
        }
    }

    /**
     * if all players passed then clear board and let
     * last player to put something down start
     * returns if set restarts (true) and not restart (false)
     */
    public boolean setRestart() {
        currentSet.clear();
        int count = 0;
        int lastPlay = -1;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getPass() == 1) {
                count++;
            } else {
                lastPlay = i;
            }
        }
        if (count == 3 && lastPlay != -1) {
            turn = lastPlay;
            return true;
        } else if (count == 4) {
            Log.i("PresidentState.java", "All players passed, shouldn't happen");
        }
        return false;
    }

    /**
     * getters and setters
     */
    public void setTurn(int idx) {
        turn = idx;
    }

    public int getTurn() {
        return turn;
    }

    public int getScore(int idx) {
        return players.get(idx).getScore();
    }
    public ArrayList<Card> getCurrentSet() { return currentSet; }

    public int checkGame() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getScore() >= 11) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<PlayerTracker> getPlayers() {
        return players;
    }

    /**
     * gameWon
     *
     * Checks if player won game
     * Must have reached 11 points
     *
     * @return true (player won game) or false (game continues)
     */
    public boolean gameWon(PlayerTracker player) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) == player) {
                if (player.getScore() >= 11) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * setFinish
     *
     * Checks if someone won the game
     *
     * @return boolean if someone won the game
     */
    public boolean setFinish() {
        /**
         * If all players have played their cards,
         * then the round is over and
         * initialize the trade.
         */
        if (playersWithCards() == 0) {
            setRoundStart(true);
        }
        return false;
    }

    /**
     * playersWithCards
     *
     * @return number of players with cards
     */
    public int playersWithCards() {
        int count = 0;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getHand().size() > 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * setRoundStart
     *
     * Set the round start to be false
     */
    public void setRoundStart(boolean roundStart) {
        this.roundStart = roundStart;
        if(roundStart){
            trade();
        }
    }

    public void checkPresident(int turn) {
        if(rankCount[turn] == -1){
            int count = 0;
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getRank() > players.get(turn).getRank()){
                    count++;
                }
            }
            if(count == 0){
                players.get(turn).setRank(3); // set to president
            }
            else if(count == 1){
                players.get(turn).setRank(2); // set to vp
            }
            else if(count == 2){
                players.get(turn).setRank(1); // set to vs
            }
            else if(count == 3){
                players.get(turn).setRank(0); // set to scum
            }
        }
    }

    /**
     * trade
     *
     * @return true (can trade) or false (cannot trade)
     */
    public boolean trade() {
        return false;
    }

    public int getPrev() { return prev; }
    public void setPrev() { prev = turn; }
    /**
     * checkPass
     *
     * @return boolean if a player can pass
     */
    public boolean checkPass(){
        int count = 0;
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getPass() == 1){
                count++;
            }
        }
        if(count == 3){
            return true;
        }
        return false;
    }

    /**
     * nextPlayer
     *
     * Updates turn
     */
    public void nextPlayer() {
        if (turn == players.size() - 1) {
            turn = 0;
        } else {
            turn++;
        }
    }

    public void setCurrentSet( ArrayList<Card> in) {
        this.currentSet = in;
    }

}
