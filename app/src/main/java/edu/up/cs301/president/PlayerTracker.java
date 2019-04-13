package edu.up.cs301.president;

import java.util.ArrayList;

import edu.up.cs301.president.CardInfo.Card;

public class PlayerTracker {

    private int score; // player's score
    private int pass; // 0 if didn't pass, 1 if passed

    private ArrayList<Card> playerHand;
    private static int MAX_CARDS = 13;
    /*
       rank:
       -1 : no rank
       0 : scum
       1 : vice scum
       2 : vice pres
       3 : pres
     */
    private int rank;

    public PlayerTracker(){
        playerHand = new ArrayList<>(MAX_CARDS);
        score = 0;
        pass = 0;
        rank = -1;
    }

    public PlayerTracker(PlayerTracker orig){
        score = orig.score;
        pass = orig.pass;
        rank = orig.rank;
    }

    /* player's score */
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    /* player's rank */
    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }

    public ArrayList<Card> getHand() { return playerHand; }
    public void addCard(Card in) { playerHand.add(in); }


    /* player's pass */
    public int getPass() { return pass; }
    public void setPass() { pass = 1; }
    public void resetPass() { pass = 0; }
}
