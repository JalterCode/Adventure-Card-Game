import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

        Deck eventDeck = new Deck();
        EventCard Test = new EventCard("event","Test",5);
        eventDeck.addCard(Test);
        game.setEventDeck(eventDeck);

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

        Deck eventDeck = new Deck();
        EventCard Test = new EventCard("event","Test",1);
        eventDeck.addCard(Test);
        game.setEventDeck(eventDeck);

        pressReturn();
        game.play();

        assertEquals(2,game.getWinners().size());
    }
    @Test
    @DisplayName("Ensure game correctly terminates when winner is assigned")
    void RESP04_test_01(){
        Game game = new Game();
        game.P1.setShields(7);

        Deck eventDeck = new Deck();
        EventCard Test = new EventCard("event","Test",1);
        eventDeck.addCard(Test);
        game.setEventDeck(eventDeck);

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

        Deck eventDeck = new Deck();
        EventCard Test = new EventCard("event","Test1",1);
        EventCard Test2 = new EventCard("event","Test2",3);
        eventDeck.addCard(Test);
        eventDeck.addCard(Test2);
        eventDeck.shuffle();
        game.setEventDeck(eventDeck);

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

        Deck eventDeck = new Deck();
        EventCard Test = new EventCard("event","Test",1);
        eventDeck.addCard(Test);
        game.setEventDeck(eventDeck);


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

    @Test
    @DisplayName("Check if player's hand is displayed at the beginning of turn")
    void RESP11_test_01() {
        Game game = new Game();
        Deck deck = new Deck();

        Deck eventDeck = new Deck();
        EventCard Plague = new EventCard("event","Plague",1);
        eventDeck.addCard(Plague);
        game.setEventDeck(eventDeck);





        game.setAdventureDeck(deck);

        AdventureCard F15 = new AdventureCard("adventure", "test1", 5, 5, "foe");
        AdventureCard F20 = new AdventureCard("adventure", "test2", 5, 5, "foe");
        deck.addCard(F15);
        deck.addCard(F20);


        for (int i = 0; i < 10; i++) {
            game.drawAdventureCard(game.P1);
        }

        pressReturn();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        game.playTurn();

        String output = outputStream.toString();

        assertTrue(output.contains("Hand: [test1, test1, test1, test1, test1, test2, test2, test2, test2, test2]"),
                "Player's hand was not displayed correctly at the beginning of the turn.");
    }

    void setUpCorrect(Game game){

        Deck adventureDeck = new Deck();
        Deck questDeck = new Deck();

        AdventureCard F5 = new AdventureCard("adventure","F5",5,1, "foe");
        AdventureCard Horse = new AdventureCard("adventure", "Horse", 10, 1, "weapon");

        adventureDeck.addCard(F5);
        adventureDeck.addCard(Horse);

        QuestCard quest = new QuestCard("quest", "Q2", 2, 3);
        questDeck.addCard(quest);

        // Set the correct decks
        game.setAdventureDeck(adventureDeck);  // Adventure cards in adventure deck
        game.setEventDeck(questDeck);          // Quest cards in event deck

        // Draw adventure cards for player P2
        game.drawAdventureCard(game.P2);
        game.drawAdventureCard(game.P2);

        // Draw a quest/event card for the current player
        game.drawEventCard(game.players[game.currentPlayerNum]);

    }
    void inputsForQuestValid(){


        InputStream input1 = new ByteArrayInputStream("N\n".getBytes()); // First response
        InputStream input2 = new ByteArrayInputStream("Y\n".getBytes()); // Second response
        InputStream input3 = new ByteArrayInputStream("1\nquit\n".getBytes()); // 3rd response
        InputStream input4 = new ByteArrayInputStream("1\nquit\n".getBytes()); // 4th response


        InputStream combinedInput = new SequenceInputStream(
                new SequenceInputStream(
                        new SequenceInputStream(input1, input2),
                        input3
                ),
                input4
        );


        System.setIn(combinedInput);

    }
    @Test
    @DisplayName("Check if sponsoring player is correctly assigned") //check if quest ended, discarded
    void RESP12_test_01() {
        Game game = new Game();
        inputsForQuestValid();
        setUpCorrect(game);

        // Check that P2 is correctly assigned as the sponsoring player
        assertTrue(game.getSponsoringPlayer().getID().equals(game.P2.getID()));
    }


    @Test
    @DisplayName("Check if game correctly prompts the player")
    void RESP12_test_02() {
        Game game = new Game();
        inputsForQuestValid();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        setUpCorrect(game);

        String output = outputStream.toString();


        assertTrue(output.contains("Would you like to sponsor this quest, P1? (Y/N)"),
                "game did not prompt");

        assertTrue(output.contains("P2 has sponsored the quest"),
                "incorrect sponsor");
    }

    @Test
    @DisplayName("Check if game handles the logic of each player pressing no and ending when it gets back to the player who inititate") //check if quest ended, discarded
    void RESP13_test_01() {

        Game game = new Game();
        Deck deck = new Deck();
        QuestCard Q2 = new QuestCard("quest", "Q2", 2, 3);
        deck.addCard(Q2);
        game.setEventDeck(deck);

        InputStream input1 = new ByteArrayInputStream("N\n".getBytes()); // First response
        InputStream input2 = new ByteArrayInputStream("N\n".getBytes()); // Second response
        InputStream input3 = new ByteArrayInputStream("N\n".getBytes()); // 3rd response
        InputStream input4 = new ByteArrayInputStream("N\n".getBytes()); // 4th response

        InputStream combinedInput = new SequenceInputStream(
                new SequenceInputStream(
                        new SequenceInputStream(input1, input2),
                        input3
                ),
                input4
        );
        System.setIn(combinedInput);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);


        game.drawEventCard(game.players[game.currentPlayerNum]);


        String output = outputStream.toString();
        assertTrue(output.contains("Would you like to sponsor this quest, P1? (Y/N)"),
                "game did not prompt P1");
        assertTrue(output.contains("Would you like to sponsor this quest, P2? (Y/N)"),
                "game did not prompt P2");
        assertTrue(output.contains("Would you like to sponsor this quest, P3? (Y/N)"),
                "game did not prompt P3");
        assertTrue(output.contains("Would you like to sponsor this quest, P4? (Y/N)"),
                "game did not prompt P4");

        assertTrue(output.contains("Nobody sponsored"),
                "game did not prompt");

        assertNull(game.getSponsoringPlayer());

    }
    @Test
    @DisplayName("Check if game correctly discards quest card if nobody sponsors") //check if quest ended, discarded
    void RESP13_test_02() {

        Game game = new Game();
        Deck deck = new Deck();
        QuestCard Q2 = new QuestCard("quest", "Q2", 2, 3);
        deck.addCard(Q2);
        game.setEventDeck(deck);

        Card expectedCard = game.getEventDeck().getCards().get(0);


        InputStream input1 = new ByteArrayInputStream("N\n".getBytes()); // First response
        InputStream input2 = new ByteArrayInputStream("N\n".getBytes()); // Second response
        InputStream input3 = new ByteArrayInputStream("N\n".getBytes()); // 3rd response
        InputStream input4 = new ByteArrayInputStream("N\n".getBytes()); // 4th response

        InputStream combinedInput = new SequenceInputStream(
                new SequenceInputStream(
                        new SequenceInputStream(input1, input2),
                        input3
                ),
                input4
        );
        System.setIn(combinedInput);
        game.drawEventCard(game.players[game.currentPlayerNum]);

        assertTrue(deck.getDiscard().contains(expectedCard));

    }

    @Test
    @DisplayName("Check if game correctly displays players hand") //check if quest ended, discarded
    void RESP14_test_01(){
        Game game = new Game();
        Deck deck = new Deck();
        AdventureCard F15 = new AdventureCard("adventure","F15",15,5, "foe");
        AdventureCard F20 = new AdventureCard("adventure","F20",20,4, "foe");
        deck.addCard(F15);
        deck.addCard(F20);
        game.setAdventureDeck(deck);

        for(int i = 0; i < 9; i++){
            game.drawAdventureCard(game.P1);
        }


        QuestCard Q2 = new QuestCard("quest", "Q2", 1, 3);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        String simulatedInput = "1\nQuit\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        game.buildQuest(game.P1,Q2);

        String output = outputStream.toString();
        assertTrue(output.contains("[F15, F15, F15, F15, F20, F20, F20, F20]"),
                "hand incorrectly displayed");
    }

    @Test
    @DisplayName("Check if game correctly prompts player to quit or select a card") //check if quest ended, discarded
    void RESP14_test_02(){
        Game game = new Game();
        Deck deck = new Deck();
        AdventureCard F15 = new AdventureCard("adventure","F15",15,5, "foe");
        AdventureCard F20 = new AdventureCard("adventure","F20",20,4, "foe");
        deck.addCard(F15);
        deck.addCard(F20);
        game.setAdventureDeck(deck);

        for(int i = 0; i < 9; i++){
            game.drawAdventureCard(game.P1);
        }

        QuestCard Q2 = new QuestCard("quest", "Q2", 1, 3);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        String simulatedInput = "1\nQuit\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        System.setOut(printStream);


        game.buildQuest(game.P1,Q2);

        String output = outputStream.toString();
        assertTrue(output.contains("'Quit' to finish the stage"),
                "not prompted to quit");

        assertTrue(output.contains("Enter the position of the card you want to select (1 to 9)"),
                "player prompted incorrect index of cards to choose from");
    }

    @Test
    @DisplayName("Check if game correctly determines if an integer input is out of bounds") //check if quest ended, discarded
    void RESP15_test_01(){
        Game game = new Game();
        Deck testDeck = new Deck();
        AdventureCard F15 = new AdventureCard("adventure","F15",15,5, "foe");
        AdventureCard F20 = new AdventureCard("adventure","F20",20,4, "foe");
        testDeck.addCard(F15);
        testDeck.addCard(F20);
        game.setAdventureDeck(testDeck);

        for(int i = 0; i < 9; i++){
            game.drawAdventureCard(game.P1);
        }

        QuestCard Q2 = new QuestCard("quest","Q2",1,3);
        testDeck.addCard(Q2);

        InputStream input1 = new ByteArrayInputStream("1000\n1\n".getBytes()); // First response
        InputStream input2 = new ByteArrayInputStream("Quit\n".getBytes()); // Second response

        InputStream combinedInput = new SequenceInputStream(input1, input2);
        System.setIn(combinedInput);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        game.buildQuest(game.P1,Q2);

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid card position. Please try again."),
                "doesn't correctly check if valid");

    }

    @Test
    @DisplayName("Check if game correctly handles when a non integer is entered") //check if quest ended, discarded
    void RESP15_test_02(){
        Game game = new Game();
        Deck testDeck = new Deck();
        AdventureCard F15 = new AdventureCard("adventure","F15",15,5, "foe");
        AdventureCard F20 = new AdventureCard("adventure","F20",20,4, "foe");
        testDeck.addCard(F15);
        testDeck.addCard(F20);
        game.setAdventureDeck(testDeck);

        for(int i = 0; i < 9; i++){
            game.drawAdventureCard(game.P1);
        }

        QuestCard Q2 = new QuestCard("quest","Q2",1,3);
        testDeck.addCard(Q2);

        InputStream input1 = new ByteArrayInputStream("wedwqedwedewdwedwed\n1\n".getBytes()); // First response
        InputStream input2 = new ByteArrayInputStream("Quit\n".getBytes()); // Second response


        InputStream combinedInput = new SequenceInputStream(input1, input2);
        System.setIn(combinedInput);


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        game.buildQuest(game.P1,Q2);

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid input. Input must be an integer, please enter a number or 'Quit'."),
                "doesn't correctly check if valid");

    }

    @Test
    @DisplayName("Check game correctly adds cards to a stage") //check if quest ended, discarded
    void RESP16_test_01(){
        Game game = new Game();
        Deck testDeck = new Deck();
        AdventureCard F15 = new AdventureCard("adventure","F15",15,5, "foe");
        AdventureCard Dagger = new AdventureCard("adventure","Dagger",5,4, "weapon");
        testDeck.addCard(F15);
        testDeck.addCard(Dagger);
        game.setAdventureDeck(testDeck);

        for(int i = 0; i < 9; i++){
            game.drawAdventureCard(game.P1);
        }

        QuestCard Q1 = new QuestCard("quest","Q2",1,3);
        testDeck.addCard(Q1);

        InputStream input1 = new ByteArrayInputStream("1\n".getBytes()); // First response
        InputStream input2 = new ByteArrayInputStream("8\nquit\n".getBytes()); // Second response


        InputStream combinedInput = new SequenceInputStream(input1,input2);

        System.setIn(combinedInput);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        game.buildQuest(game.P1,Q1);

        String output = outputStream.toString();
        assertTrue(output.contains("Final Stage: [F15, Dagger]"),
                "stage not correctly initialized");

    }

    @Test
    @DisplayName("Check initializeStages functionality") //check if quest ended, discarded
    void RESP16_test_02(){
        Game game = new Game();
        QuestCard Q1 = new QuestCard("quest","Q2",3,3);

        ArrayList<ArrayList<AdventureCard>> actualStages = game.initializeStages(Q1.getStages());

        assertEquals(3,actualStages.size()); //3 stages created

    }
    @Test
    @DisplayName("Test whether quit works when the user enters it before adding a card")
    void RESP17_test_01(){
        Game game = new Game();
        Deck testDeck = new Deck();
        AdventureCard F15 = new AdventureCard("adventure","F15",15,5, "foe");
        AdventureCard F20 = new AdventureCard("adventure","F20",20,4, "foe");
        testDeck.addCard(F15);
        testDeck.addCard(F20);
        game.setAdventureDeck(testDeck);

        for(int i = 0; i < 9; i++){
            game.drawAdventureCard(game.P1);
        }

        QuestCard Q1 = new QuestCard("quest","Q2",1,3);
        testDeck.addCard(Q1);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        String simulatedInput = "quit\n1\nquit\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        System.setOut(printStream);

        game.buildQuest(game.P1,Q1);

        String output = outputStream.toString();
        assertTrue(output.contains("A stage cannot be empty"),
                "program successfully quit with 0 cards, bad.");
    }

    @Test
    @DisplayName("Test functionality for isStageGreater than, where current stage is greater")
    public void RESP18_test_01() {
        Game game = new Game();
        ArrayList<AdventureCard> currentStage = new ArrayList<>();
        currentStage.add(new AdventureCard("adventure", "Card1", 10, 5, "foe"));
        currentStage.add(new AdventureCard("adventure", "Card2", 15, 7, "foe"));

        ArrayList<AdventureCard> previousStage = new ArrayList<>();
        previousStage.add(new AdventureCard("adventure", "Card3", 20, 3, "foe"));

        assertTrue(game.isStageGreater(previousStage, currentStage));
    }

    @Test
    @DisplayName("Test functionality for isStageGreater than, where current stage is equal")
    public void RESP18_test_02() {
        Game game = new Game();
        ArrayList<AdventureCard> currentStage = new ArrayList<>();
        currentStage.add(new AdventureCard("adventure", "Card1", 10, 5, "foe"));
        currentStage.add(new AdventureCard("adventure", "Card2", 15, 7, "foe"));

        ArrayList<AdventureCard> previousStage = new ArrayList<>();
        previousStage.add(new AdventureCard("adventure", "Card3", 25, 3, "foe"));

        assertFalse(game.isStageGreater(previousStage, currentStage));
    }

    @Test
    @DisplayName("Test functionality for handleQuitInput, ensuring that is false when new stage has less value")
    public void RESP18_test_03(){
        Game game = new Game();
        ArrayList<ArrayList<AdventureCard>> stages = game.initializeStages(2);

        ArrayList<AdventureCard> currentStage = new ArrayList<>();
        currentStage.add(new AdventureCard("adventure", "Card1", 5, 5, "foe"));
        currentStage.add(new AdventureCard("adventure", "Card2", 15, 7, "foe"));

        ArrayList<AdventureCard> previousStage = new ArrayList<>();
        previousStage.add(new AdventureCard("adventure", "Card3", 25, 3, "foe"));

        stages.add(previousStage);
        stages.add(currentStage);

        assertFalse(game.handleQuitInput(1,stages));
    }

    @Test
    @DisplayName("Test functionality for handleQuitInput, ensuring that is false when new stage has equal value")
    public void RESP18_test_04(){
        Game game = new Game();
        ArrayList<ArrayList<AdventureCard>> stages = game.initializeStages(2);

        ArrayList<AdventureCard> currentStage = new ArrayList<>();
        currentStage.add(new AdventureCard("adventure", "Card1", 10, 5, "foe"));
        currentStage.add(new AdventureCard("adventure", "Card2", 15, 7, "foe"));

        ArrayList<AdventureCard> previousStage = new ArrayList<>();
        previousStage.add(new AdventureCard("adventure", "Card3", 25, 3, "foe"));

        stages.add(previousStage);
        stages.add(currentStage);

        assertFalse(game.handleQuitInput(1,stages));
    }

    @Test
    @DisplayName("Test if game correctly displays that stage of insufficient value is being added when following game logic")
    public void RESP18_test_05(){
        Game game = new Game();
        Deck testDeck = new Deck();
        AdventureCard F15 = new AdventureCard("adventure","F15",15,5, "foe");
        AdventureCard W20 = new AdventureCard("adventure","W20",20,4, "weapon");
        testDeck.addCard(F15);
        testDeck.addCard(W20);
        game.setAdventureDeck(testDeck);

        for(int i = 0; i < 9; i++){
            game.drawAdventureCard(game.P1);
        }
        InputStream input1 = new ByteArrayInputStream("1\nquit\n".getBytes()); // First response
        InputStream input2 = new ByteArrayInputStream("1\nquit\n".getBytes()); // Second response
        InputStream input3 = new ByteArrayInputStream("7\n".getBytes()); // 3rd response
        InputStream input4 = new ByteArrayInputStream("quit\n".getBytes()); // 3rd response

        InputStream combinedInput = new SequenceInputStream(
                new SequenceInputStream(
                        new SequenceInputStream(input1, input2),
                        input3
                ),
                input4
        );
        System.setIn(combinedInput);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        QuestCard Q2 = new QuestCard("quest","Q2",2,3);
        testDeck.addCard(Q2);

        System.setOut(printStream);

        game.buildQuest(game.P1,Q2);

        String output = outputStream.toString();
        assertTrue(output.contains("Insufficient value for this stage"),
                "did not tell the user it was insufficient value");

    }

    @Test
    @DisplayName("Test if game correctly handles the situation where 2 of the same weapon card is added ")
    public void RESP19_test_01(){
        Game game = new Game();
        Deck testDeck = new Deck();
        AdventureCard F15 = new AdventureCard("adventure","F15",15,5, "foe");
        AdventureCard W20 = new AdventureCard("adventure","W20",20,4, "weapon");
        testDeck.addCard(F15);
        testDeck.addCard(W20);
        game.setAdventureDeck(testDeck);

        for(int i = 0; i < 9; i++){
            game.drawAdventureCard(game.P1);
        }

        InputStream input1 = new ByteArrayInputStream("8\n8\n".getBytes()); // First response
        InputStream input2 = new ByteArrayInputStream("1\nquit\n".getBytes()); // Second response

        InputStream combinedInput = new SequenceInputStream(input1,input2);
        System.setIn(combinedInput);
        QuestCard Q1 = new QuestCard("quest","Q1",1,3);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        game.buildQuest(game.P1,Q1);

        String output = outputStream.toString();

        assertTrue(output.contains("This weapon has already been used in this stage. Please choose a different card."),
                "game allowed 2 insertions of the same card");

    }
    @Test
    @DisplayName("Test if game correctly handles the situation where the player attempts to add a foe when one is already in the stage")
    public void RESP19_test_02(){
        Game game = new Game();
        Deck testDeck = new Deck();
        AdventureCard F15 = new AdventureCard("adventure","F15",15,5, "foe");
        AdventureCard W20 = new AdventureCard("adventure","W20",20,4, "weapon");
        testDeck.addCard(F15);
        testDeck.addCard(W20);
        game.setAdventureDeck(testDeck);

        for(int i = 0; i < 9; i++){
            game.drawAdventureCard(game.P1);
        }

        InputStream input1 = new ByteArrayInputStream("1\n1\n".getBytes()); // First response
        InputStream input2 = new ByteArrayInputStream("8\nquit\n".getBytes()); // Second response

        InputStream combinedInput = new SequenceInputStream(input1,input2);
        System.setIn(combinedInput);
        QuestCard Q1 = new QuestCard("quest","Q1",1,3);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        game.buildQuest(game.P1,Q1);

        String output = outputStream.toString();

        assertTrue(output.contains("A foe has already been added to this stage. Please choose a different card."),
                "game allowed 2 insertions of the same card");

    }

    @Test
    @DisplayName("Test if game correctly displays eligible players (every player excluding sponsor")
    public void RESP20_test_01(){
        Game game = new Game();
        game.setSponsoringPlayer(game.P1);
        game.distributeAdventureCards();

        ArrayList<ArrayList<AdventureCard>> stages = new ArrayList<>();
        stages.add(new ArrayList<>());
        AdventureCard F5 = new AdventureCard("adventure","F5",5,8, "foe");
        AdventureCard Dagger = new AdventureCard("adventure", "Dagger", 5, 6, "weapon");

        stages.get(0).add(F5);
        stages.get(0).add(Dagger);

        InputStream input1 = new ByteArrayInputStream("n\n".getBytes()); // First response
        InputStream input2 = new ByteArrayInputStream("n\n".getBytes()); // Second response
        InputStream input3 = new ByteArrayInputStream("n\n".getBytes()); // Second response

        System.setIn(new SequenceInputStream(
                new SequenceInputStream(
                        input1, input2
                ),
                input3
        ));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        game.beginQuest(stages);

        String output = outputStream.toString();

        assertTrue(output.contains("Eligible players: [P2, P3, P4]"),
                "did not correctly display the eligible players");

    }
    @Test
    @DisplayName("Test if game correctly prompts the player in order if they are going to participate")
    public void RESP20_test_02() {
        Game game = new Game();
        game.setSponsoringPlayer(game.P1);
        game.distributeAdventureCards();

        ArrayList<ArrayList<AdventureCard>> stages = new ArrayList<>();
        stages.add(new ArrayList<>());
        AdventureCard F5 = new AdventureCard("adventure", "F5", 5, 8, "foe");
        AdventureCard Dagger = new AdventureCard("adventure", "Dagger", 5, 6, "weapon");

        stages.get(0).add(F5);
        stages.get(0).add(Dagger);

        InputStream input1 = new ByteArrayInputStream("n\n".getBytes()); // First response
        InputStream input2 = new ByteArrayInputStream("n\n".getBytes()); // Second response
        InputStream input3 = new ByteArrayInputStream("n\n".getBytes()); // Second response

        System.setIn(new SequenceInputStream(
                new SequenceInputStream(
                        input1, input2
                ),
                input3
        ));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        game.beginQuest(stages);

        String output = outputStream.toString();


        int indexP2 = output.indexOf("P2, would you like to participate in the current quest?");
        int indexP3 = output.indexOf("P3, would you like to participate in the current quest?");
        int indexP4 = output.indexOf("P4, would you like to participate in the current quest?");

        assertTrue(indexP2 < indexP3);
        assertTrue(indexP3 < indexP4);
    }

    @Test
    @DisplayName("test if game correctly prompts player to trim their hand if and only if they are participating")
    public void RESP21_test_01() {
        Game game = new Game();
        game.setSponsoringPlayer(game.P1);
        game.distributeAdventureCards(); //in this scenario, each player will need to trim as they start with 12

        ArrayList<ArrayList<AdventureCard>> stages = new ArrayList<>();
        stages.add(new ArrayList<>());
        AdventureCard F5 = new AdventureCard("adventure","F5",5,8, "foe");
        AdventureCard Dagger = new AdventureCard("adventure", "Dagger", 5, 6, "weapon");

        stages.get(0).add(F5);
        stages.get(0).add(Dagger);

        InputStream input1 = new ByteArrayInputStream("n\n".getBytes()); // First response
        InputStream input2 = new ByteArrayInputStream("n\n".getBytes()); // Second response
        InputStream input3 = new ByteArrayInputStream("y\n".getBytes()); // p4 participates
        InputStream input4 = new ByteArrayInputStream("1\n".getBytes()); // discards their first card

        InputStream combinedInput = new SequenceInputStream(
                new SequenceInputStream(
                        new SequenceInputStream(input1, input2),
                        input3
                ),
                input4
        );
        System.setIn(combinedInput);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        game.beginQuest(stages);

        String output = outputStream.toString();

        assertTrue(output.contains("You must discard 1 cards"),
                "player was not prompted");
        assertTrue(output.contains("was successfully discarded"),
                "card not discarded");

        assertEquals(1,game.getAdventureDeck().getDiscard().size()); //ensure discard pile is still updated

    }

    @Test
    @DisplayName("Test for if quest correctly terminates of all players decline to participate")
    public void RESP21_test_02() {
        Game game = new Game();
        game.setSponsoringPlayer(game.P1);
        game.distributeAdventureCards(); //in this scenario, each player will need to trim as they start with 12

        ArrayList<ArrayList<AdventureCard>> stages = new ArrayList<>();
        stages.add(new ArrayList<>());
        AdventureCard F5 = new AdventureCard("adventure","F5",5,8, "foe");
        AdventureCard Dagger = new AdventureCard("adventure", "Dagger", 5, 6, "weapon");

        stages.get(0).add(F5);
        stages.get(0).add(Dagger);
        InputStream input1 = new ByteArrayInputStream("n\n".getBytes()); // First response
        InputStream input2 = new ByteArrayInputStream("n\n".getBytes()); // Second response
        InputStream input3 = new ByteArrayInputStream("n\n".getBytes()); // Second response

        System.setIn(new SequenceInputStream(
                new SequenceInputStream(
                        input1, input2
                ),
                input3
        ));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        game.beginQuest(stages);

        String output = outputStream.toString();

        assertTrue(output.contains("There are no more eligible players that can participate. Ending quest."),
                "still eligible players");
        assertTrue(output.contains("Quest ended"),
                "card not discarded");


    }
    @Test
    @DisplayName("Test for if player is correctly prompted to either quit or add a card to their attack ")
    public void RESP22_test_01() {
        Game game = new Game();
        game.setSponsoringPlayer(game.P1);



        ArrayList<ArrayList<AdventureCard>> stages = new ArrayList<>();
        stages.add(new ArrayList<>());
        AdventureCard Excalibur = new AdventureCard("adventure", "Excalibur", 30, 2, "weapon");
        AdventureCard Dagger = new AdventureCard("adventure", "Dagger", 5, 6, "weapon");
        game.P1.addCardToHand((Dagger));
        game.P1.addCardToHand(Excalibur);
        game.P1.addCardToHand(Excalibur);

        stages.get(0).add(Excalibur);
        stages.get(0).add(Dagger);

        InputStream input1 = new ByteArrayInputStream("1\n".getBytes()); // First response
        InputStream input2 = new ByteArrayInputStream("2\n".getBytes()); // Second response
        InputStream input3 = new ByteArrayInputStream("quit\n".getBytes()); // Second response

        System.setIn(new SequenceInputStream(
                new SequenceInputStream(
                        input1, input2
                ),
                input3
        ));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        game.buildAttack(game.P1, stages.get(0));

        String output = outputStream.toString();

        assertTrue(output.contains("Enter the position of the card you want to select (1 to 3) or 'Quit' to end building this attack"),
                "player not prompted");

        assertTrue(output.contains("P1 added Dagger to their attack"),
                "correct card not added");

        assertTrue(output.contains("P1 added Excalibur to their attack"),
                "correct card not added");

        assertTrue(output.contains("P1 has finished building their attack."),
                "quit not functioning");
    }

    public void buildAttackInitialization(Game game){
        game.setSponsoringPlayer(game.P1);


        ArrayList<ArrayList<AdventureCard>> stages = new ArrayList<>();
        stages.add(new ArrayList<>());
        AdventureCard Excalibur = new AdventureCard("adventure", "Excalibur", 30, 2, "weapon");
        AdventureCard Dagger = new AdventureCard("adventure", "Dagger", 5, 6, "weapon");
        game.P1.addCardToHand((Dagger));
        game.P1.addCardToHand(Excalibur);
        game.P1.addCardToHand(Excalibur);

        stages.get(0).add(Excalibur);
        stages.get(0).add(Dagger);

        game.buildAttack(game.P1, stages.get(0));

    }
    @Test
    @DisplayName("Test that player inputs a valid position ")
    public void RESP23_test_01() {
        Game game = new Game();


        InputStream input1 = new ByteArrayInputStream("100\n".getBytes()); // simulate out of range input
        InputStream input2 = new ByteArrayInputStream("quit\n".getBytes()); // Second response
        System.setIn(new SequenceInputStream(input1, input2));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        buildAttackInitialization(game);

        String output = outputStream.toString();

        assertTrue(output.contains("Error: Input out of range."),
                "did not warn the user that the input was out of range");
    }

    @Test
    @DisplayName("Test that player inputs is re-prompted AFTER entering an invalid card")
    public void RESP23_test_02() {
        Game game = new Game();


        InputStream input1 = new ByteArrayInputStream("100\n".getBytes()); // simulate out of range input
        InputStream input2 = new ByteArrayInputStream("1\n".getBytes()); // Second response
        InputStream input3 = new ByteArrayInputStream("quit\n".getBytes());

        System.setIn(new SequenceInputStream(
                new SequenceInputStream(
                        input1, input2
                ),
                input3
        ));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        buildAttackInitialization(game);

        String output = outputStream.toString();

        int indexError = output.indexOf("Error: Input out of range."); //see that this happens before
        int indexFinish = output.indexOf("P1 has finished building their attack."); //this is only triggered when successfully built, meaning they got reprompted

        assertTrue(indexError < indexFinish);
    }
    @Test
    @DisplayName("Test that player cannot enter a foe card")
    public void RESP23_test_03() {
        Game game = new Game();

        InputStream input1 = new ByteArrayInputStream("1\n".getBytes()); // simulate picking a foe
        InputStream input2 = new ByteArrayInputStream("2\n".getBytes()); // select a valid card
        InputStream input3 = new ByteArrayInputStream("quit\n".getBytes()); // finish building

        System.setIn(new SequenceInputStream(
                new SequenceInputStream(
                        input1, input2
                ),
                input3
        ));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        AdventureCard F5 = new AdventureCard("adventure", "F5", 5, 8, "foe");
        game.P1.addCardToHand(F5);
        buildAttackInitialization(game);

        String output = outputStream.toString();

        assertTrue(output.contains("Foes cannot be used in attacks."),
                "did not warn the user that foes cannot be added");

        //ensure foe was not added
        assertTrue(output.contains("Final attack: [Dagger]"), "did not stop player from adding foe");

    }


}










