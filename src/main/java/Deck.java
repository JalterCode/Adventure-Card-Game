import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> cards;

    private ArrayList<Card> discard;


    public Deck(){
        this.cards = new ArrayList<>();
        this.discard = new ArrayList<>();
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public int deckSize(){
        return cards.size();
    }

    public int discardSize(){
        return discard.size();
    }
    public void addCard(Card card){
        for(int i=0;i<card.getAmount();i++){
            cards.add(card);
        }
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }


    public ArrayList<Card> getDiscard(){
        return discard;
    }
}
