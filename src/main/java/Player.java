import java.util.ArrayList;

public class Player {
    private int shields;

    private ArrayList<AdventureCard> hand;
    private String ID;

    public Player(String ID){
        this.hand = new ArrayList<>();
        this.ID = ID;
    }

    public void addCardToHand(AdventureCard card){
        hand.add(card);
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



}
