import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class  Main {
    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();

        Deck eventDeck = new Deck();
        QuestCard Q4 = new QuestCard("quest", "Q4", 4, 1);
        EventCard Plague = new EventCard("event", "Plague", 1);
        EventCard Prosperity = new EventCard("event", "Prosperity", 1);
        EventCard QueenFavor = new EventCard("event", "Queen's Favor", 1);
        QuestCard Q3 = new QuestCard("quest", "Q3", 3, 1);

        eventDeck.addCard(Q4);
        eventDeck.addCard(Q3);

        game.setEventDeck(eventDeck);

        AdventureCard F5 = new AdventureCard("adventure", "F5", 5, 1, "foe");
        AdventureCard F15 = new AdventureCard("adventure", "F15", 15, 8, "foe");
        AdventureCard F40 = new AdventureCard("adventure", "F40", 40, 2, "foe");
        AdventureCard Dagger = new AdventureCard("adventure", "Dagger", 5, 6, "weapon");
        AdventureCard Horse = new AdventureCard("adventure", "Horse", 10, 12, "weapon");
        AdventureCard Excalibur = new AdventureCard("adventure", "Excalibur", 30, 2, "weapon");
        AdventureCard Sword = new AdventureCard("adventure", "Sword", 10, 1, "weapon");
        AdventureCard BattleAxe = new AdventureCard("adventure", "Battle Axe", 15, 1, "weapon");
        AdventureCard Lance = new AdventureCard("adventure", "Lance", 20, 1, "weapon");

        game.P1.getHand().clear();
        game.P1.getHand().add(F5);
        game.P1.getHand().add(F5);
        game.P1.getHand().add(F15);
        game.P1.getHand().add(F15);
        game.P1.getHand().add(Dagger);
        game.P1.getHand().add(Sword);
        game.P1.getHand().add(Sword);
        game.P1.getHand().add(Horse);
        game.P1.getHand().add(Horse);
        game.P1.getHand().add(BattleAxe);
        game.P1.getHand().add(BattleAxe);
        game.P1.getHand().add(Lance);

        game.P2.getHand().clear();
        game.P2.getHand().add(F5);
        game.P2.getHand().add(F5);
        game.P2.getHand().add(F15);
        game.P2.getHand().add(F15);
        game.P2.getHand().add(F40);
        game.P2.getHand().add(Dagger);
        game.P2.getHand().add(Sword);
        game.P2.getHand().add(Horse);
        game.P2.getHand().add(Horse);
        game.P2.getHand().add(BattleAxe);
        game.P2.getHand().add(BattleAxe);
        game.P2.getHand().add(Excalibur);

        game.P3.getHand().clear();
        game.P3.getHand().add(F5);
        game.P3.getHand().add(F5);
        game.P3.getHand().add(F5);
        game.P3.getHand().add(F15);
        game.P3.getHand().add(Dagger);
        game.P3.getHand().add(Sword);
        game.P3.getHand().add(Sword);
        game.P3.getHand().add(Sword);
        game.P3.getHand().add(Horse);
        game.P3.getHand().add(Horse);
        game.P3.getHand().add(BattleAxe);
        game.P3.getHand().add(Lance);

        game.P4.getHand().clear();
        game.P4.getHand().add(F5);
        game.P4.getHand().add(F15);
        game.P4.getHand().add(F15);
        game.P4.getHand().add(F40);
        game.P4.getHand().add(Dagger);
        game.P4.getHand().add(Dagger);
        game.P4.getHand().add(Sword);
        game.P4.getHand().add(Horse);
        game.P4.getHand().add(Horse);
        game.P4.getHand().add(BattleAxe);
        game.P4.getHand().add(Lance);
        game.P4.getHand().add(Excalibur);

//create new deck to contain the expected cards to be drawn in the correct order
        Deck testDeck = new Deck();
        AdventureCard F20 = new AdventureCard("adventure", "F20", 20, 1, "foe");

        AdventureCard F30 = new AdventureCard("adventure", "F30", 30, 1, "foe");
        AdventureCard F10 = new AdventureCard("adventure", "F10", 10, 1, "foe");

        //deck is in this order
        testDeck.addCard(F30);
        testDeck.addCard(Sword);
        testDeck.addCard(BattleAxe);
        testDeck.addCard(F10);
        testDeck.addCard(Lance);
        testDeck.addCard(Lance);
        testDeck.addCard(BattleAxe);
        testDeck.addCard(Sword);
        testDeck.addCard(F30);
        testDeck.addCard(Lance);
        testDeck.addCard(Excalibur);
        testDeck.addCard(Excalibur);
        testDeck.addCard(Excalibur);
        testDeck.addCard(F5);
        testDeck.addCard(F5);
        testDeck.addCard(F5);
        testDeck.addCard(F5);
        testDeck.addCard(F5);
        testDeck.addCard(Dagger);
        testDeck.addCard(F10);
        testDeck.addCard(F15);
        testDeck.addCard(F20);
        testDeck.addCard(F5);
        testDeck.addCard(F5);
        testDeck.addCard(Excalibur);
        testDeck.addCard(Excalibur);
        testDeck.addCard(Excalibur);
        testDeck.addCard(Excalibur);
        testDeck.addCard(Excalibur);
        testDeck.addCard(Excalibur);
        testDeck.addCard(Excalibur);
        testDeck.addCard(Excalibur);
        testDeck.addCard(Excalibur);
        testDeck.addCard(Excalibur);
        testDeck.addCard(Excalibur);

        game.setAdventureDeck(testDeck);
        game.play();
    }


    private ArrayList<String> parseInputToList(String input){

        String[] parts = input.split(", ");
        ArrayList<String> cards = new ArrayList<>();
        for(String part: parts){
            cards.add(part);
        }

        return cards;

    }
}
