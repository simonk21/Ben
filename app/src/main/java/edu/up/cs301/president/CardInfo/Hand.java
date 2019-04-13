package edu.up.cs301.president.CardInfo;
// TODO: will probably remove, using PlayerTracker to hold hand (card arraylist)
import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> playersHand;

    public Hand(){
        this.playersHand = new ArrayList<>();
    }

    public void addCard(Card temp){
        playersHand.add(temp);
    }

    public void removeCard(Card temp){

    }

    public ArrayList<Card> getHand(){
        return playersHand;
    }

}
