import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    @DisplayName("Check Adventure Deck is 100 Cards")
    void RESP01_test_01(){
        Game game = new Game();
        System.out.println(game.getAdventureDeck().deckSize());
        assertEquals(100,game.getAdventureDeck().deckSize());
    }

    @Test
    @DisplayName("Check Adventure Deck correctly assigned the 50 foe and 50 weapon cards")
    void RESP01_test_02() {
        Game game = new Game();
        assertEquals(100, game.getAdventureDeck().deckSize());

        AdventureCard F5 = new AdventureCard("adventure","F5",5,8, "foe");
        AdventureCard F10 = new AdventureCard("adventure","F10",10,7, "foe");
        AdventureCard F15 = new AdventureCard("adventure","F15",15,8, "foe");
        AdventureCard F20 = new AdventureCard("adventure","F20",20,7, "foe");
        AdventureCard F25 = new AdventureCard("adventure","F25",25,7, "foe");
        AdventureCard F30 = new AdventureCard("adventure","F30",30,4, "foe");
        AdventureCard F35 = new AdventureCard("adventure","F35",35,4, "foe");
        AdventureCard F40 = new AdventureCard("adventure","F40",40,2, "foe");
        AdventureCard F50 = new AdventureCard("adventure","F50",50,2, "foe");
        AdventureCard F70 = new AdventureCard("adventure","F70",70,1, "foe");

        AdventureCard Dagger = new AdventureCard("adventure", "Dagger", 5, 6, "weapon");
        AdventureCard Horse = new AdventureCard("adventure", "Horse", 10, 12, "weapon");
        AdventureCard Sword = new AdventureCard("adventure", "Sword", 10, 16, "weapon");
        AdventureCard BattleAxe = new AdventureCard("adventure", "Battle Axe", 15, 8, "weapon");
        AdventureCard Lance = new AdventureCard("adventure", "Lance", 20, 6, "weapon");
        AdventureCard Excalibur = new AdventureCard("adventure", "Excalibur", 30, 2, "weapon");

// Array to hold all Adventure Cards
        Card[] adventureCards = {
                F5, F10, F15, F20, F25, F30, F35, F40, F50, F70,
                Dagger, Horse, Sword, BattleAxe, Lance, Excalibur
        };

        for (Card card : adventureCards) {
            System.out.println("Testing: " + card.getName());
            assertEquals(card.getAmount(), game.countOccurrence(game.getAdventureDeck(), card));
            //the get amount on the card should match the actual amount setup by the deck
        }
    }

    @Test
    @DisplayName("Check Event Deck correctly assigned the 12Q cards and 5E cards")
    void RESP01_test_03() {
        Game game = new Game();
        QuestCard Q2 = new QuestCard("quest","Q2",2,3);
        QuestCard Q3 = new QuestCard("quest","Q3",3,4);
        QuestCard Q4 = new QuestCard("quest","Q4",4,3);
        QuestCard Q5 = new QuestCard("quest","Q5",5,2);

        EventCard Plague = new EventCard("event","Plague",1);
        EventCard QueenFavor = new EventCard("event","Queen's Favor",2);
        EventCard Prosperity = new EventCard("event","Prosperity",2);

        Card[] cards = { Q2, Q3, Q4, Q5, Plague, QueenFavor, Prosperity };

        for (Card card : cards) {
            System.out.println(card.getAmount() + game.countOccurrence(game.getEventDeck(), card));
            assertEquals(card.getAmount(),game.countOccurrence(game.getEventDeck(),card) );
            //the get amount on the card should match the actual amount setup by the deck
        }
    }
}








