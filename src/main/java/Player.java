import java.util.ArrayList;

public class Player {

    private ArrayList<AdventureCard> hand;

    public Player(){
        this.hand = new ArrayList<>();
    }

    public void addCardToHand(AdventureCard card){
        hand.add(card);
    }

    public ArrayList<AdventureCard> getHand(){
        return hand;
    }



}
