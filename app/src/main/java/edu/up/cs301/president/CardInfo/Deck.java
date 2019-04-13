package edu.up.cs301.president.CardInfo;

import java.util.ArrayList;
import java.util.Collections;

import edu.up.cs301.president.PlayerTracker;

public class Deck {

    private ArrayList<Card> deck;
    public Deck(){
        deck = new ArrayList<>();
        for(Card.Suit s : Card.Suit.values()){
            for(Card.Rank r : Card.Rank.values()){
                Card card = new Card(s,r);
                deck.add(card);
            }
        }

        Collections.shuffle(deck);
    }

    public void deal(ArrayList<PlayerTracker> players){
        for(int i = 0; i < players.size(); i++){
            for(int j = 0; j < 13; j++){
                Card temp = deck.remove(0);
                players.get(i).addCard(temp);
            }
        }
    }

}
