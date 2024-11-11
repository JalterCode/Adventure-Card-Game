import io.cucumber.java.en.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameSteps {
    private Game game;
    private List<String> inputQueue = new ArrayList<>();

    private Map<String, ArrayList<AdventureCard>> simulatedHands = new HashMap<>();

    Deck simulatedDeck = new Deck();




    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


    @Given("a new game is started")
    public void a_new_game_is_started() {
        game = new Game();
    }
    @And("a randomized hand is dealt")
    public void a_randomized_hand_is_dealt(){
        game.distributeAdventureCards();
    }


    @And("the event deck is setup to draw {string} in that order")
    public void event_deck_draw(String cards){
        Deck eventDeck = new Deck();
        QuestCard Q4 = new QuestCard("quest", "Q4", 4, 1);
        EventCard Plague = new EventCard("event", "Plague", 1);
        EventCard Prosperity = new EventCard("event", "Prosperity", 1);
        EventCard QueenFavor = new EventCard("event", "Queen's Favor", 1);
        QuestCard Q3 = new QuestCard("quest", "Q3", 3, 1);
        QuestCard Q2 = new QuestCard("quest", "Q2", 2, 3);

        ArrayList<String> cardList = parseInputToList(cards);

        for(String card: cardList){
            if(card.equals("Q4")){
                eventDeck.addCard(Q4);
            }

            if(card.equals("Q3")){
                eventDeck.addCard(Q3);
            }

            if(card.equals("Plague")){
                eventDeck.addCard(Plague);
            }

            if(card.equals("Prosperity")){
                eventDeck.addCard(Prosperity);
            }

            if(card.equals("Queen's Favor")){
                eventDeck.addCard(QueenFavor);
            }

            if(card.equals("Q2")){
                eventDeck.addCard(Q2);
            }


        }

        game.setEventDeck(eventDeck);


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
        AdventureCard F5 = new AdventureCard("adventure", "F5", 5, 1, "foe");
        AdventureCard F15 = new AdventureCard("adventure", "F15", 15, 1, "foe");
        AdventureCard F20 = new AdventureCard("adventure", "F20", 20, 1, "foe");
        AdventureCard Dagger = new AdventureCard("adventure", "Dagger", 5, 1, "weapon");

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
        testDeck.addCard(Excalibur);
        testDeck.addCard(Excalibur);

        testDeck.addCard(Excalibur);

        //deck is in this order
        simulatedDeck.addCard(F30);
        simulatedDeck.addCard(Sword);
        simulatedDeck.addCard(BattleAxe);
        simulatedDeck.addCard(F10);
        simulatedDeck.addCard(Lance);
        simulatedDeck.addCard(Lance);
        simulatedDeck.addCard(BattleAxe);
        simulatedDeck.addCard(Sword);
        simulatedDeck.addCard(F30);
        simulatedDeck.addCard(Lance);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(F5);
        simulatedDeck.addCard(F5);
        simulatedDeck.addCard(F5);
        simulatedDeck.addCard(F5);
        simulatedDeck.addCard(F5);
        simulatedDeck.addCard(Dagger);
        simulatedDeck.addCard(F10);
        simulatedDeck.addCard(F15);
        simulatedDeck.addCard(F20);
        simulatedDeck.addCard(F5);
        simulatedDeck.addCard(F5);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);

        simulatedDeck.addCard(Excalibur);

        simulatedDeck.addCard(Excalibur);

        simulatedDeck.addCard(Excalibur);
        simulatedDeck.addCard(Excalibur);















        game.setAdventureDeck(testDeck);
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


        simulatedHands.put("P1", new ArrayList<>(game.P1.getHand()));
        simulatedHands.put("P2", new ArrayList<>(game.P2.getHand()));
        simulatedHands.put("P3", new ArrayList<>(game.P3.getHand()));
        simulatedHands.put("P4", new ArrayList<>(game.P4.getHand()));



    }

    @When("{string} draws a quest of {int} stages and {string}")
    public void player_draws_a_quest(String player, int stages, String answer){
        switch (answer) {
            case "declines":
                inputQueue.add("n\n"); //Declines
                break;
            case "accepts":
                inputQueue.add("y\n"); //Accepts
        }
    }

    @And("{string} chooses to {string}")
    public void player_accepts_declines(String playerID, String answer){

        if(answer.equals("accept")){
            inputQueue.add("y\n");
        }
        else{
            inputQueue.add("n\n");
        }

    }

    @And("{string}, {string}, {string} participate and {string} discards")
    public void threePlayers_participate_discard(String firstPlayer, String secondPlayer, String thirdPlayer, String discardingPlayer){
        ArrayList<Player> players = new ArrayList<>();

        Map<String, Player> playerMap = new HashMap<>();
        playerMap.put("P1", game.P1);
        playerMap.put("P2", game.P2);
        playerMap.put("P3", game.P3);
        playerMap.put("P4", game.P4);

        players.add(playerMap.get(firstPlayer));
        players.add(playerMap.get(secondPlayer));
        players.add(playerMap.get(thirdPlayer));

        for (Player player : players) {
            inputQueue.add("y\n");
            ArrayList<AdventureCard> simulatedHand = simulatedHands.get(player.getID());

            simulatedHand.add((AdventureCard) simulatedDeck.getCards().removeFirst());
            Collections.sort(simulatedHand);


            if (player == playerMap.get(discardingPlayer) || discardingPlayer.equals("everyone")) {
                inputQueue.add("1\n");


                simulatedHand.remove(0);
                simulatedHands.put(player.getID(), simulatedHand);


            }
        }
    }


    @And("{string}, {string}, {string} participate")
    public void threePlayers_participate(String firstPlayer, String secondPlayer, String thirdPlayer){

        ArrayList<Player> players = new ArrayList<>();

        Map<String, Player> playerMap = new HashMap<>();
        playerMap.put("P1", game.P1);
        playerMap.put("P2", game.P2);
        playerMap.put("P3", game.P3);
        playerMap.put("P4", game.P4);

        players.add(playerMap.get(firstPlayer));
        players.add(playerMap.get(secondPlayer));
        players.add(playerMap.get(thirdPlayer));

        for (Player player : players) {
            inputQueue.add("y\n");
            ArrayList<AdventureCard> simulatedHand = simulatedHands.get(player.getID());

            simulatedHand.add((AdventureCard) simulatedDeck.getCards().removeFirst());
            Collections.sort(simulatedHand);
        }


    }


    @And("{string}, {string} participate")
    public void twoPlayers_participate(String firstPlayer, String secondPlayer){

        ArrayList<Player> players = new ArrayList<>();

        Map<String, Player> playerMap = new HashMap<>();
        playerMap.put("P1", game.P1);
        playerMap.put("P2", game.P2);
        playerMap.put("P3", game.P3);
        playerMap.put("P4", game.P4);

        players.add(playerMap.get(firstPlayer));
        players.add(playerMap.get(secondPlayer));

        for (Player player : players) {
            inputQueue.add("y\n");
            ArrayList<AdventureCard> simulatedHand = simulatedHands.get(player.getID());

            simulatedHand.add((AdventureCard) simulatedDeck.getCards().removeFirst());
            Collections.sort(simulatedHand);
        }
    }



    @And("{string} participate")
    public void players_participate(String players){
        ArrayList<String> listPlayers = parseInputToList(players);

        for (String playerID : listPlayers) {
            inputQueue.add("y\n");
            ArrayList<AdventureCard> simulatedHand = simulatedHands.get(playerID);

            simulatedHand.add((AdventureCard) simulatedDeck.getCards().removeFirst());
            Collections.sort(simulatedHand);
            simulatedHands.put(playerID, simulatedHand);
        }
    }






    @And("{string} builds {string} by using the cards {string}")
    public void stage_builder(String playerID, String stage, String cards){

        Player player = selectPlayer(playerID);

        ArrayList<String> listCards = parseInputToList(cards);

        String lastCard = listCards.getLast();

        Set<String> usedCards = new HashSet<>();

        ArrayList<AdventureCard> simulatedHand = simulatedHands.get(playerID);
        
        for(String card: listCards){

            for (int i = 0; i < simulatedHand.size(); i++) {
                if (card.equals(simulatedHand.get(i).getName()) && !usedCards.contains(card)) {

                    simulatedHand.remove(i);
                    simulatedHands.put(player.getID(), simulatedHand);

                    inputQueue.add(i+1 + "\n");

                    usedCards.add(card);

                    if(card.equals(lastCard)){
                        inputQueue.add("quit\n");

                        usedCards.clear();
                        break;
                    }
                }
            }
        }


    }

    private ArrayList<String> parseInputToList(String input){

        String[] parts = input.split(", ");
        ArrayList<String> cards = new ArrayList<>();
        for(String part: parts){
            cards.add(part);
        }

        return cards;

    }

    @And("{string} draws {int} cards from stage being resolved")
    public void stage_builder_draws(String playerID, int amount){
        Player player = selectPlayer(playerID);
        ArrayList<AdventureCard> simulatedHand = simulatedHands.get(player.getID());
        for(int i = 0; i < amount; i++){
            simulatedHand.add((AdventureCard) simulatedDeck.getCards().removeFirst());
        }

        Collections.sort(simulatedHand);
    }

    @And("{string} trims their hand down to 12 cards from {int}")
    public void player_trims_hand(String playerID, int handSize){

        Player player = selectPlayer(playerID);

        ArrayList<AdventureCard> simulatedHand = simulatedHands.get(player.getID());

        simulatedHand.add((AdventureCard) simulatedDeck.getCards().removeFirst());
        Collections.sort(simulatedHand);





        int amountTrim = handSize - 12;

        for(int i = 0; i < amountTrim; i++){
            inputQueue.add("1\n");
            simulatedHand.removeFirst();

        }

        simulatedHands.put(playerID, simulatedHand);
    }


    @And("Quest logic is resolved")
    public void resolve_quest_logic(){
        InputStream inputStream = createInputStreamFromList(inputQueue);
        System.setIn(inputStream);

        game.drawEventCard(game.players[game.currentPlayerNum]);
        game.beginQuest(game.gameStages);
        game.currentPlayerNum = (game.currentPlayerNum + 1) % game.players.length;
        inputQueue.clear();
        game.checkWinners(); // check for winners after each quest
    }

    @And("{string} should have {int} shields")
    public void check_shields(String playerID, int shields){


        ArrayList<String> players = parseInputToList(playerID);

        for(String playerId: players){
            Player player = selectPlayer(playerId);
            assertEquals(shields,player.getShields());
        }

    }

    @And("{string} should have the hand: {string}")
    public void check_hand(String playerID, String hand){

        Player player = selectPlayer(playerID);

        assertTrue(player.getHand().toString().contains(hand));

    }

    @And("{string} should have {int} cards in hand")
    public void check_hand_size(String playerID, int amount){

        Player player = selectPlayer(playerID);


        assertEquals(amount,player.getHand().size());
    }

    @And("the event deck is setup to draw Q2")
    public void event_deck_draw_Q2(){
        Deck eventDeck = new Deck();
        QuestCard Q2 = new QuestCard("quest", "Q2", 2, 3);
        eventDeck.addCard(Q2);
        game.setEventDeck(eventDeck);
    }

    @Then("the quest should end with no winner")
    public void quest_end_no_winner(){

        assertEquals(0, game.getWinners().size()); //ensure no winners
        assertFalse(game.questing); //check that the quest properly ended, aka not questing
    }

    @And("P1 should no longer have any F5")
    public void P1_no_F5(){
        //here we are going to check if the hand contains F5, it shouldn't as all of them would be removed
        //as a result of discarding the excess cards after the quest, NONE were used to build the stages
        assertFalse(game.P1.getHand().toString().contains("F5"));
    }

    @And("P1 should have new cards")
    public void P1_new_cards(){

        //ensure correct hand size
        assertEquals(12,game.P1.getHand().size());

        //the initial hand of P1 only contained 2 battleaxes and 1 lance, with the way the deck is structured
        //in these tests, P1 will always have 2 battleaxes and 3 lance, proving that they drew cards

        int battleAxeCount = 0;
        int lanceCount = 0;

        for(Card card: game.P1.getHand()){
            if(card.getName().equals("Battle Axe")){
                battleAxeCount+=1;
            }
            else if(card.getName().equals("Lance")){
                lanceCount += 1;
            }
        }

        assertEquals(3,battleAxeCount);
        assertEquals(3,lanceCount);

    }

    @And("{string} draws {string}")
    public void eventCard_drawn(String playerID, String eventCard){
        Player player = selectPlayer(playerID);

        if(eventCard.equals("Prosperity")){
            inputQueue.add("1\n");
            inputQueue.add("1\n");
            for (String playerId : simulatedHands.keySet()) {
                ArrayList<AdventureCard> simulatedHand = simulatedHands.get(playerId);

                simulatedHand.add((AdventureCard) simulatedDeck.getCards().removeFirst());
                simulatedHand.add((AdventureCard) simulatedDeck.getCards().removeFirst());

                Collections.sort(simulatedHand);

                simulatedHands.put(playerId, simulatedHand);
            }

            simulatedHands.get("P1").removeFirst();
            simulatedHands.get("P1").removeFirst();

            InputStream inputStream = createInputStreamFromList(inputQueue);
            System.setIn(inputStream);
            inputQueue.clear();
        }

        if(eventCard.equals("Queen's Favor")){
            inputQueue.add("1\n");

            ArrayList<AdventureCard> simulatedHand = simulatedHands.get(playerID);
            simulatedHand.add((AdventureCard) simulatedDeck.getCards().removeFirst());
            simulatedHand.add((AdventureCard) simulatedDeck.getCards().removeFirst());

            Collections.sort(simulatedHand);
            simulatedHand.removeFirst();
            simulatedHands.put(player.getID(), simulatedHand);



            InputStream inputStream = createInputStreamFromList(inputQueue);
            System.setIn(inputStream);
            inputQueue.clear();

        }

        game.drawEventCard(player);
        game.currentPlayerNum = (game.currentPlayerNum + 1) % game.players.length;

    }




    @Then("{string} determined as winner")
    public void determined_as_winner(String playerID) {


        ArrayList<String> winners = parseInputToList(playerID);

        for(String winner: winners){
            Player player = selectPlayer(winner);
            assertTrue(game.getWinners().contains(player));
        }
    }





    private Player selectPlayer(String playerID){
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

        return player;
    }


    private static InputStream createInputStreamFromList(List<String> inputs) {
        InputStream result = new ByteArrayInputStream(inputs.get(0).getBytes());

        for (int i = 1; i < inputs.size(); i++) {
            result = new SequenceInputStream(result, new ByteArrayInputStream(inputs.get(i).getBytes()));
        }

        return result;
    }
}