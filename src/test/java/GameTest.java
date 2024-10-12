import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    public void pressReturn(){
        String simulatedInput = "\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);
    }
    @Test
    @DisplayName("Check Adventure Deck is 100 Cards")
    void RESP01_test_01(){
        Game game = new Game();
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
            assertEquals(card.getAmount(),game.countOccurrence(game.getEventDeck(),card) );
            //the get amount on the card should match the actual amount setup by the deck
        }
    }
    @Test
    @DisplayName("Check that each player got the correct amount of cards")
    void RESP02_test_01(){
        Game game = new Game();
        game.distributeAdventureCards();

        for(Player player : game.players){
            assertEquals(12,player.getHand().size());
        }
    }

    @Test
    @DisplayName("Check that the adventure deck correctly updates the amount of cards")
    void RESP02_test_02(){
        Game game = new Game();
        game.distributeAdventureCards();
        assertEquals(52,game.getAdventureDeck().deckSize());
    }
    @Test
    @DisplayName("Ensure deck correctly shuffles")
    void RESP02_test_03(){
        Game game = new Game();
        List<Card> originalDeck = new ArrayList<>(game.getAdventureDeck().getCards());

        game.getAdventureDeck().shuffle();

        List<Card> shuffledDeck = game.getAdventureDeck().getCards();
        assertNotEquals(originalDeck,shuffledDeck);
    }

    @Test
    @DisplayName("Ensure each player takes a turn, with it looping back to P1")
    void RESP03_test_01(){
        int numTurns = 5;
        List<String> actualOrder = new ArrayList<>();
        Game game = new Game();
        String[] expectedOrder = {"P1","P2","P3","P4","P1"};
        for(int i =0; i<numTurns; i++){
            Player currentPlayer = game.players[game.currentPlayerNum];
            actualOrder.add(currentPlayer.getID());
            pressReturn();
            game.playTurn();
        }

        assertEquals(Arrays.asList(expectedOrder), actualOrder);

    }

    @Test
    @DisplayName("Ensure game determines if one or more players have 7 shields")
    void RESP03_test_02(){
        Game game = new Game();

        game.P1.setShields(7);
        game.P2.setShields(5);
        game.P3.setShields(2);
        game.P4.setShields(7);

        pressReturn();
        game.play();

        assertEquals(2,game.getWinners().size());
    }
    @Test
    @DisplayName("Ensure game correctly terminates when winner is assigned")
    void RESP04_test_01(){
        Game game = new Game();
        game.P1.setShields(7);

        pressReturn();

        game.play();
        assertTrue(game.isGameFinished()); //terminate when winner
        assertEquals(1,game.getWinners().size()); //should only be one winner
        assertTrue(game.getWinners().contains(game.P1));
    }

    @Test
    @DisplayName("Ensure game correctly assigns an event card")
    void RESP05_test_01(){
        Game game = new Game();

        Deck testDeck = new Deck();
        Player P1 = new Player("P1");

        EventCard Plague = new EventCard("event","Plague",1);
        QuestCard Q2 = new QuestCard("quest","Q2",2,3);
        QuestCard Q5 = new QuestCard("quest","Q5",5,2);

        testDeck.addCard(Plague);
        testDeck.addCard(Q2);
        testDeck.addCard(Q5);

        game.setEventDeck(testDeck);

        Card expectedCard = testDeck.getCards().get(0);
        Card actualCard = game.drawEventCard(P1);

        assertEquals(expectedCard,actualCard);

    }

    @Test
    @DisplayName("Ensure game correctly updates the event deck and its discard pile when an event card is drawn")
    void RESP05_test_02(){

        Game game = new Game();

        int initialSize = game.getEventDeck().deckSize();

        Card expectedCard = game.getEventDeck().getCards().get(0);

        pressReturn();

        game.playTurn();

        int updatedSize = game.getEventDeck().deckSize();

        assertEquals(initialSize - 1, updatedSize);

        assertTrue(game.getEventDeck().getDiscard().contains(expectedCard));

    }

    @Test
    @DisplayName("Test if Plague card correctly removes shields from the player who draws the card")

    void RESP06_test_01(){
        Game game = new Game();
        game.P1.setShields(2);
        Deck testDeck = new Deck();

        EventCard Plague = new EventCard("event","Plague",1);

        testDeck.addCard(Plague);

        game.setEventDeck(testDeck);

        pressReturn();

        game.playTurn();

        assertEquals(0, game.P1.getShields());

    }

    @Test
    @DisplayName("Test if Plague card does not make the players shield go below 0")

    void RESP06_test_02(){
        Game game = new Game();
        game.P1.setShields(1);
        Deck testDeck = new Deck();

        EventCard Plague = new EventCard("event","Plague",1);

        testDeck.addCard(Plague);

        game.setEventDeck(testDeck);

        pressReturn();

        game.playTurn();

        assertEquals(0, game.P1.getShields());

    }

    @Test
    @DisplayName("Test if Queen's Favor correctly adds 2 cards to players hand")
    void RESP07_test_01(){
        Game game = new Game();
        Deck testDeck = new Deck();

        game.setEventDeck(testDeck);

        assertEquals(0, game.P1.getHand().size());

        EventCard Queen = new EventCard("event","Queen's Favor",1);
        testDeck.addCard(Queen);

        pressReturn();

        game.playTurn();




        assertEquals(2,game.P1.getHand().size());
    }

    @Test
    @DisplayName("Test if Prosperity adds two cards to each players hand")
    void RESP07_test_02(){
        Game game = new Game();
        Deck testDeck = new Deck();

        game.setEventDeck(testDeck);


        assertEquals(0, game.P1.getHand().size());
        assertEquals(0, game.P2.getHand().size());
        assertEquals(0, game.P3.getHand().size());
        assertEquals(0, game.P4.getHand().size());

        pressReturn();


        EventCard Queen = new EventCard("event","Prosperity",1);
        testDeck.addCard(Queen);
        game.playTurn();

        assertEquals(2,game.P1.getHand().size());
        assertEquals(2,game.P2.getHand().size());
        assertEquals(2,game.P3.getHand().size());
        assertEquals(2,game.P4.getHand().size());
    }
    @Test
    @DisplayName("Test if game properly computes the amount of cards to discard from the player's hand")
    void RESP08_test_01() {

        Game game = new Game();
        game.distributeAdventureCards();


        game.drawAdventureCard(game.P1);
        game.drawAdventureCard(game.P1);

        String simulatedInput = "1\n1\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in); //

        int cardsToDiscard = game.trimHand(game.P1);

        assertEquals(2, cardsToDiscard);

    }
    @Test
    @DisplayName("Test if game properly displays the players hand")
    void RESP08_test_02() {
        Game game = new Game();
        Deck deck = new Deck();

        game.setAdventureDeck(deck);

        AdventureCard F5 = new AdventureCard("adventure", "F5", 5, 7, "foe");
        deck.addCard(F5);
        AdventureCard F10 = new AdventureCard("adventure", "F10", 10, 6, "foe");
        deck.addCard(F10);

        for(int i = 0; i < 13; i++){
            game.drawAdventureCard(game.P1);
        }

        String simulatedInput = "1";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        game.trimHand(game.P1);

        String output = outputStream.toString(); // Get the output as a string

        String expectedOutput = "You must discard 1 cards.";
        assertTrue(output.contains(expectedOutput), "Output does not contain expected message.");

        String expectedHandOutput = "[F5, F5, F5, F5, F5, F5, F5, F10, F10, F10, F10, F10, F10]";
        assertTrue(output.contains(expectedHandOutput), "Player's hand output does not match expected.");
    }

    @Test
    @DisplayName("Test if game properly removes the selected card")
    void RESP09_test_01() {

        Game game = new Game();
        Deck deck = new Deck();

        game.setAdventureDeck(deck);

        AdventureCard F15 = new AdventureCard("adventure","F15",15,5, "foe");
        AdventureCard F20 = new AdventureCard("adventure","F20",20,4, "foe");
        AdventureCard F25 = new AdventureCard("adventure","F25",25,2, "foe");
        AdventureCard F70 = new AdventureCard("adventure","F70",70,2, "foe");
        deck.addCard(F15);
        deck.addCard(F20);
        deck.addCard(F25);
        deck.addCard(F70);

        for(int i = 0; i < 13; i++){
            game.drawAdventureCard(game.P1);
        }

        String simulatedInput = "13";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        game.trimHand(game.P1);

        String output = outputStream.toString(); // Get the output as a string

        String expectedHandOutput = "F70 was successfully discarded";
        assertTrue(output.contains(expectedHandOutput), "Incorrect card was discarded.");
    }

    @Test
    @DisplayName("Test if user input is correctly handled if beyond range")
    void RESP09_test_02() {
        Game game = new Game();
        game.distributeAdventureCards();
        game.drawAdventureCard(game.P1);

        String simulatedInput = "100\n1"; //first input invalid, then enter valid after to ensure that it still works
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));


        game.trimHand(game.P1);

        String output = outputStream.toString(); // Get the output as a string

        String expectedHandOutput = "Invalid input. Please enter a valid position.";
        assertTrue(output.contains(expectedHandOutput), "incorrectly checked");
    }

    @Test
    @DisplayName("Test if user input is correctly handled if NOT a number")
    void RESP09_test_03() {

        Game game = new Game();
        game.distributeAdventureCards();
        game.drawAdventureCard(game.P1);

        String simulatedInput = "w\n1"; //first input invalid, then enter valid after to ensure that it still works
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));


        game.trimHand(game.P1);

        String output = outputStream.toString(); // Get the output as a string

        String expectedHandOutput = "Invalid input. Please enter a number.";
        assertTrue(output.contains(expectedHandOutput), "incorrectly checked");

    }

    @Test
    @DisplayName("Test the card that the player discards is actually added to the decks discard pile")
    void RESP09_test_04() {
        Game game = new Game();
        game.distributeAdventureCards();
        game.drawAdventureCard(game.P1);

        String simulatedInput = "1";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        game.trimHand(game.P1);

        assertEquals(1,game.getAdventureDeck().discardSize());

    }

    @Test
    @DisplayName("Test that the game correctly passes the turn to the next player after pressing enter")
    void RESP10_test_01() {
        Game game = new Game();
        game.P2.setShields(7); //simply here to end the game as the loop would keep running

        Player originalPlayer = game.players[game.currentPlayerNum];

        assertEquals(originalPlayer,game.players[game.currentPlayerNum]);

        pressReturn();

        game.play();

        Player currentPlayer = game.players[game.currentPlayerNum];

        assertEquals(game.P2,currentPlayer);
    }
    @Test
    @DisplayName("Test that checks if the display is cleared after pressing enter")
    void RESP10_test_02(){
        Game game = new Game();
        Deck deck = new Deck();

        EventCard Plague = new EventCard("event","Plague",16); //fill the event deck with plagues so player doesnt overcap on cards
        deck.addCard(Plague);
        game.setEventDeck(deck);

        pressReturn();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        game.playTurn();

        String output = outputStream.toString();

        assertTrue(output.contains("\n\n\n"), // Check that a number of newlines were printed
                "The display was not cleared after the player ended their turn.");

    }

}