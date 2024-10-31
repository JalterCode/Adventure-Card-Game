import io.cucumber.java.en.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameSteps {
    private Game game;
    private List<String> inputQueue = new ArrayList<>();

    @Given("a new game is started")
    public void a_new_game_is_started() {
        game = new Game();
        List<String> inputQueue = new ArrayList<>();

    }
    @And("a randomized hand is dealt")
    public void a_randomized_hand_is_dealt(){
        game.distributeAdventureCards();

    }
    @And("the event deck is setup to draw the Q4 card first")
    public void event_deck_draw_Q4_card_first(){
        Deck eventDeck = new Deck();
        QuestCard Q4 = new QuestCard("quest", "Q4", 4, 3);
        eventDeck.addCard(Q4); //THIS IS THE ONLY EVENT CARD USED IN THE A-TEST, SO THIS IS OUR DECK, IT DOESNT MATTER
        game.setEventDeck(eventDeck);
        System.out.println("Top Card of Event Deck is: " + eventDeck.getCards().getFirst().getName());
    }

    @And ("adventure deck is set up to ensure players draw the correct cards")
    public void the_event_deck_is_set_up_to_ensure_players_draw_the_correct_cards(){
        //create new deck to contain the expected cards to be drawn in the correct order
        Deck testDeck = new Deck();

        AdventureCard F30 = new AdventureCard("adventure", "F30", 30, 1, "foe");
        AdventureCard Sword = new AdventureCard("adventure", "Sword", 10, 1, "weapon");
        AdventureCard BattleAxe = new AdventureCard("adventure", "Battle Axe", 15, 1, "weapon");
        AdventureCard F10 = new AdventureCard("adventure", "F10", 10, 1, "foe");
        AdventureCard Lance = new AdventureCard("adventure", "Lance", 20, 1, "weapon");
        AdventureCard Excalibur = new AdventureCard("adventure", "Excalibur", 30, 1, "weapon");

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

        Deck newDeck = new Deck();

        // Add cards from testDeck
        for (Card card : testDeck.getCards()) {
            newDeck.addCard(card);
        }

        // Add cards from the adventure deck
        for (Card card : game.getAdventureDeck().getCards()) {
            newDeck.addCard(card);
        }

        //set the new deck to be the deck that combines the deck from the test deck and new one
        game.setAdventureDeck(newDeck);
        System.out.println("Combined Deck: " + newDeck.getCards());
    }

    @And("the players are dealt their correct initial hands")
    public void the_players_are_dealt_their_correct_initial_hands(){
        AdventureCard F5 = new AdventureCard("adventure", "F5", 5, 8, "foe");
        AdventureCard F15 = new AdventureCard("adventure", "F15", 15, 8, "foe");
        AdventureCard F40 = new AdventureCard("adventure", "F40", 40, 2, "foe");
        AdventureCard Dagger = new AdventureCard("adventure", "Dagger", 5, 6, "weapon");
        AdventureCard Horse = new AdventureCard("adventure", "Horse", 10, 12, "weapon");
        AdventureCard Excalibur = new AdventureCard("adventure", "Excalibur", 30, 2, "weapon");
        AdventureCard Sword = new AdventureCard("adventure", "Sword", 10, 16, "weapon");
        AdventureCard BattleAxe = new AdventureCard("adventure", "Battle Axe", 15, 8, "weapon");
        AdventureCard Lance = new AdventureCard("adventure", "Lance", 20, 6, "weapon");

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
    }

    @When("{string} draws a quest of {int} stages and {string}")
    public void player_draws_a_quest_of_4_stages_and_chooses(String player, int stages, String answer){
        switch (answer) {
            case "declines":
                inputQueue.add("n\n"); //Declines
                break;
            case "accepts":
                inputQueue.add("y\n"); //Accepts
        }
    }

    @And("{string} accepts the sponsor")
    public void player_accepts_sponsor(String player){
        inputQueue.add("y\n");
    }

    @And("P2 builds their stages")
    public void P2_builds_their_stages(){
        List<String> inputs = List.of(
                "1\n",   //player 2 chooses F5 to add to first stage
                "7\n", //player 2 adds horse to first stage
                "quit\n", //complete stage building
                "2\n",   //add F15 stage 2
                "5\n",  //add Sword stage 2
                "quit\n",  //finish building stage 2
                "2\n", //add F15 to stage 3
                "3\n",  //add dagger to stage 3
                "4\n",  //add battle axe to stage 3
                "quit\n", //finish building stage 3
                "2\n",  //add F40 to stage 4
                "3\n", //add battle axe to stage 4
                "quit\n"//finish building stage 4
        );

        inputQueue.addAll(inputs);
    }

    @And("P1, P3, P4 participate, discard cards, build and resolve attacks for stage 1")
    public void P1_P3_P4_participate_resolve_stage_1(){
        List<String> inputs = List.of(
                "y\n", //player 1 decides to participate
                "1\n",  //discard F5
                "y\n", //player 3 decided to participate
                "1\n", //player 3 discards F5
                "y\n", //player 4 decides to participate
                "1\n",  //player 4 discards F5
                "5\n", //player 1 chooses a Dagger
                "5\n", //player 1 chooses a Sword
                "quit\n", //player 1 finishes attack, value 15
                "5\n", //P3 chooses a sword
                "4\n", //P3 chooses a Dagger
                "quit\n", //P3 completes attack
                "4\n", //P4 adds dagger
                "6\n", //P4 adds horse
                "quit\n" //P4 finishes attack
        );

        inputQueue.addAll(inputs);

    }

    @And("P1, P3, P4 participate, discard cards, build and resolve attacks for stage 2")
    public void P1_P3_P4_participate_resolve_stage_2(){

        List<String> inputs = List.of(
                "y\n", //P1 participates
                "y\n", //P3 participates
                "y\n",  //P4 participates
                "7\n", //P1 chooses horse
                "6\n",  //P1 chooses sword
                "quit\n", //P1 finishing building attack
                "9\n", //P3 chooses axe
                "4\n", //P3 chooses sword
                "quit\n", //P3 finishes attack
                "6\n", //P4 chooses horse
                "6\n", // p4 chooses axe
                "quit\n"//p4 finishes attack
        );

        inputQueue.addAll(inputs);
    }

    @And("P3, P4 participate, discard cards, build and resolve attacks for stage 3")
    public void P3_P4_participate_resolve_stage_3(){
        List<String> inputs = List.of(
                "y\n", //both players participate
                "y\n",
                "10\n", //lance
                "6\n", //horse
                "4\n", //sword
                "quit\n", //P3 finishes building attack
                "7\n", // axe
                "5\n", //sword
                "6\n", //lance
                "quit\n" //P4 finishes building attack
        );

        inputQueue.addAll(inputs);

    }

    @And("P3, P4 participate, discard cards, build and resolve attacks for stage 4")
    public void P3_P4_participate_resolve_stage_4(){
        List<String> inputs = List.of(
                "y\n", //both participate
                "y\n",
                "7\n", //axe
                "6\n", //horse
                "6\n", //Lance
                "quit\n", //P3 finishes building attack
                "4\n", //dagger
                "4\n", //sword
                "4\n", //lance
                "5\n", //excalibur
                "quit\n" //P4 finishes building attack
        );

        inputQueue.addAll(inputs);
    }

    @And("{string} trims their hand down to 12 cards from {int}")
    public void player_trims_hand(String playerID, int handSize){

        int amountTrim = handSize - 12;

        for(int i = 0; i < amountTrim; i++){
            inputQueue.add("1\n");
        }
    }


    @And("Quest logic is resolved")
    public void resolve_quest_logic(){
        InputStream inputStream = createInputStreamFromList(inputQueue);
        System.setIn(inputStream);

        game.drawEventCard(game.players[game.currentPlayerNum]);
        game.beginQuest(game.gameStages);
        game.currentPlayerNum += 1; //need to go to the next player, the logic for this is normally in the play function
        inputQueue.clear();
        game.checkWinners(); // check for winners after each quest
    }

    @Then("{string} wins the quest, with {int} shields awarded")
    public void check_winner_quest(String playerID, int shields){
        Player player;
        switch (playerID) {
            case "P1":
                player = game.P1;
                break;
            case "P2":
                player = game.P2;
                break;
            case "P3":
                player = game.P3;
                break;
            case "P4":
                player = game.P4;
                break;
            default: throw new IllegalArgumentException("Invalid player ID: " + playerID);
        }

        assertEquals(shields,player.getShields());

    }

    @And("P1 should have no shields and the correct hand")
    public void P1_no_shields_correct_hand(){
        assertTrue(game.P1.getHand().toString().contains("[F5, F10, F15, F15, F30, Horse, Battle Axe, Battle Axe, Lance]"), "P1 hand incorrectly displayed");
        assertEquals(0, game.P1.getShields());
    }

    @And("P2 should have 12 cards in hand")
    public void P2_12_cards(){
        assertEquals(12,game.P2.getHand().size());
    }

    @And("P3 should have no shields and the correct hand")
    public void P3_no_shields_correct_hand(){
        assertEquals(0, game.P3.getShields());
        assertTrue(game.P3.getHand().toString().contains("[F5, F5, F15, F30, Sword]"), "P3 hand incorrectly displayed");
    }

    @And("P4 should have 4 shields and the correct hand")
    public void P4_4_shields_correct_hand(){
        assertEquals(4,game.P4.getShields());
        assertTrue(game.P4.getHand().toString().contains("[F15, F15, F40, Lance]"), "P4 hand incorrectly displayed");
    }


    private static InputStream createInputStreamFromList(List<String> inputs) {
        InputStream result = new ByteArrayInputStream(inputs.get(0).getBytes());

        for (int i = 1; i < inputs.size(); i++) {
            result = new SequenceInputStream(result, new ByteArrayInputStream(inputs.get(i).getBytes()));
        }

        return result;
    }
}