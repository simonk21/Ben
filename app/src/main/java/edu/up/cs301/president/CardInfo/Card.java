package edu.up.cs301.president.CardInfo;

import java.io.Serializable;

public class Card implements Serializable {

    public enum Suit{
        DIAMONDS,
        SPADES,
        CLUBS,
        HEARTS;

        public static final int NUM_SUITS = 4;

        @Override
        public String toString() { return this.name(); }
    }

    public enum Rank {
        THREE(1),
        FOUR(2),
        FIVE(3),
        SIX(4),
        SEVEN(5),
        EIGHT(6),
        NINE(7),
        TEN(8),
        JACK(9),
        QUEEN(10),
        KING(11),
        ACE(12),
        TWO(13);

        public static final int NUM_RANK = 13;
        private int rankVal;

        Rank(int rankVal) { this.rankVal = rankVal; }

        public int getValue() { return rankVal; }
        public void setValue(int val) { rankVal = val; }

        public String toString() { return this.name(); }
    }

    private Suit suit;
    private Rank rank;

    public Card(Suit s, Rank r){
        suit = s;
        rank = r;
    }

    public Card(Card orig){
        suit = orig.getSuit();
        rank = orig.getRank();
    }

    public Suit getSuit() { return suit; }

    public Rank getRank() { return rank; }

    //TODO: Citation needed
    //https://stackoverflow.com/questions/23329132/building-a-deck-of-cards-in-java-using-2-different-enums
}
