package A3;

import A3.AdventureCard;

import java.util.ArrayList;

public class Player {
    private int shields;

    private ArrayList<AdventureCard> hand;
    private String ID;

    private boolean participating = false;

    public Player(String ID){
        this.hand = new ArrayList<>();
        this.ID = ID;
    }

    public void addCardToHand(AdventureCard card){
        hand.add(card);
    }

    public AdventureCard discardHand(int position){
        return hand.remove(position);
    }

    public ArrayList<AdventureCard> getHand(){
        return hand;
    }

    public int getShields(){
        return shields;
    }

    public String getID(){
        return ID;
    }

    public void setShields(int amount){
        this.shields = amount;
    }

    public void setParticipating(boolean participating) {
        this.participating = participating;
    }

    public boolean isParticipating(){
        return participating;
    }

    @Override
    public String toString() {
        return ID;
    }



}
