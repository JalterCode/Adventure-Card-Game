package A3;

import A3.AdventureCard;
import A3.Card;
import A3.Deck;
import A3.EventCard;

import java.util.*;
import java.util.concurrent.BlockingQueue;

public class Game {

    private BlockingQueue<String> inputQueue;
    private BlockingQueue<String> outputQueue;


    public void setIOQueues(BlockingQueue<String> inputQueue, BlockingQueue<String> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }


    private void output(String message) {
        if (outputQueue != null) {
            outputQueue.add(message);
        } else {
            System.out.println(message);
        }
    }


    private String getInput() {
        try {
            if (inputQueue != null) {
                return inputQueue.take(); // Waits for input
            } else {
                return new Scanner(System.in).nextLine();
            }
        } catch (InterruptedException e) {
            return "";
        }
    }




    private Deck adventureDeck = new Deck();

    private Deck eventDeck = new Deck();

    Player P1 = new Player("P1");
    Player P2 = new Player("P2");
    Player P3 = new Player("P3");
    Player P4 = new Player("P4");

    Player[] players = {P1, P2, P3, P4};

    private Set<Player> winners = new HashSet<>();

    private Player sponsoringPlayer = null;

    public int currentPlayerNum = 0;
    boolean finished = false;

    ArrayList<ArrayList<AdventureCard>> gameStages;

    Boolean questing;


    public Game() {
        adventureDeckSetup();
        eventDeckSetup();
    }

    public void play() {
        winners.clear();
        while (!finished) {
            questing = false;
            if(playTurn()) {
                if (questing) {
                    beginQuest(gameStages);
                    output(players[currentPlayerNum] + "'s turn has ended.");
                    output("Press send to end and pass your turn.");

                    getInput(); // Wait for enter key

                    clearDisplay();
                    currentPlayerNum = (currentPlayerNum + 1) % players.length;
                    finished = checkWinners();
                    if(finished) break;
                } else {
                    output(players[currentPlayerNum] + "'s turn has ended.");
                    output("Press send to end and pass your turn.");

                    getInput(); // Wait for enter key

                    clearDisplay();
                    currentPlayerNum = (currentPlayerNum + 1) % players.length;
                    finished = checkWinners();
                }
            }
            finished = checkWinners();
            if(finished) break;
        }
    }


    public boolean checkWinners() {
        for (Player player : players) {
            if (player.getShields() >= 7) {
                winners.add(player);
            }
        }
        if (!winners.isEmpty()) {
            output("Winners: " + winners);
            output("Now ending game...");
            return true;
        }
        return false;
    }

    public boolean playTurn() {
        output("It is now " + players[currentPlayerNum].getID() + "'s turn.");
        output("Hand: " + players[currentPlayerNum].getHand());

        Card card = drawEventCard(players[currentPlayerNum]);

        if (players[currentPlayerNum].getHand().size() > 12) {
            trimHand(players[currentPlayerNum]);
        }

        if(card instanceof QuestCard){
            return true;
        }

        output(players[currentPlayerNum] + "'s turn has ended.");
        output("Press <return> to end and pass your turn.");

        getInput(); // substitue for scanner

        clearDisplay();

        currentPlayerNum = (currentPlayerNum + 1) % players.length;

        return false;
    }

    public void adventureDeckSetup() {
        //Foe cards
        AdventureCard F5 = new AdventureCard("adventure", "F5", 5, 8, "foe");
        AdventureCard F10 = new AdventureCard("adventure", "F10", 10, 7, "foe");
        AdventureCard F15 = new AdventureCard("adventure", "F15", 15, 8, "foe");
        AdventureCard F20 = new AdventureCard("adventure", "F20", 20, 7, "foe");
        AdventureCard F25 = new AdventureCard("adventure", "F25", 25, 7, "foe");
        AdventureCard F30 = new AdventureCard("adventure", "F30", 30, 4, "foe");
        AdventureCard F35 = new AdventureCard("adventure", "F35", 35, 4, "foe");
        AdventureCard F40 = new AdventureCard("adventure", "F40", 40, 2, "foe");
        AdventureCard F50 = new AdventureCard("adventure", "F50", 50, 2, "foe");
        AdventureCard F70 = new AdventureCard("adventure", "F70", 70, 1, "foe");

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
            adventureDeck.addCard(card);
        }
        adventureDeck.shuffle();
    }


    public void eventDeckSetup() {
        QuestCard Q2 = new QuestCard("quest", "Q2", 2, 3);
        QuestCard Q3 = new QuestCard("quest", "Q3", 3, 4);
        QuestCard Q4 = new QuestCard("quest", "Q4", 4, 3);
        QuestCard Q5 = new QuestCard("quest", "Q5", 5, 2);

        EventCard Plague = new EventCard("event", "Plague", 1);
        EventCard QueenFavor = new EventCard("event", "Queen's Favor", 2);
        EventCard Prosperity = new EventCard("event", "Prosperity", 2);

        Card[] cards = {Q2, Q3, Q4, Q5, Plague, QueenFavor, Prosperity};

        for (Card card : cards) {
            eventDeck.addCard(card);
        }
        eventDeck.shuffle();
    }

    public int countOccurrence(Deck deck, Card card) {
        int count = 0;

        for (Card searchCard : deck.getCards()) {
            if (searchCard.getName().equals(card.getName())) {
                count++;
            }
        }
        return count;
    }

    public void drawAdventureCard(Player player) {
        if (adventureDeck.deckSize() == 0) {
            adventureDeck.reShuffle();
        }
        AdventureCard card = (AdventureCard) adventureDeck.getCards().remove(0);
        player.addCardToHand(card);
        Collections.sort(player.getHand());

    }

    public Card drawEventCard(Player player) {
        if (eventDeck.deckSize() == 0) {
            eventDeck.reShuffle();
        }

        Card card = eventDeck.getCards().remove(0);

        output(player.getID() + " Draws the card: " + card);

        if (card instanceof EventCard) {
            ((EventCard) card).handleEvent((EventCard) card, player, this);
            eventDeck.discard(card); //event card needs to be discarded after effect is resolved
        }

        if (card instanceof QuestCard) {
            questing = true;
            sponsorQuest(card);
        }

        return card;
    }


    public void sponsorQuest(Card card) {
        QuestCard questCard = (QuestCard) card;
        output("\nQuest card drawn: " + questCard);
        boolean sponsored = false;

        int startingPlayer = currentPlayerNum;

        for (int i = startingPlayer; i < startingPlayer + players.length; i++) {
            Player player = players[i % players.length];

            if (!sponsored && askToSponsorQuest(player)) {
                sponsoringPlayer = player;
                sponsored = true;
                gameStages = buildQuest(player, questCard);
                if (gameStages != null) {
                    questing = true;
                    return;
                }
            }
        }

        output("No one sponsored the quest.");
        questing = false;
    }


    public boolean askToSponsorQuest(Player player) {
        while (true) {  // Keep asking until valid input is received
            output("Would you like to sponsor this quest, " + player.getID() + "? (Y/N)");
            String input = getInput().trim().toUpperCase();

            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            } else {
                output("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
            }
        }
    }


    public boolean askToParticipate(Player player) {
        boolean validInput = false;
        while (!validInput) {
            String input = getInput().trim().toUpperCase();
            if (input.equals("Y")) {
                output(player.getID() + " has chosen to participate");
                return true;
            } else if (input.equals("N")) {
                output(player.getID() + " has chosen to forfeit");
                return false;
            } else {
                output("Invalid Input, Y/N");
            }
        }
        return false;
    }

    public void beginQuest(ArrayList<ArrayList<AdventureCard>> stages) {
        ArrayList<Player> initialPlayers = new ArrayList<>();
        ArrayList<ArrayList<AdventureCard>> attacks = new ArrayList<>();
        ArrayList<AdventureCard> currentAttack;

        // Set all eligible players as initially participating
        for (Player player : players) {
            if (!(player.getID().equals(sponsoringPlayer.getID()))) {
                initialPlayers.add(player);
                player.setParticipating(true);
            }
        }

        output("Eligible players: " + initialPlayers);

        for (int stageIndex = 0; stageIndex < stages.size(); stageIndex++) {
            ArrayList<Player> toRemove = new ArrayList<>();
            output("Current stage: " + (stageIndex + 1));

            // Determine if player wants to participate
            for (Player player : initialPlayers) {
                if (!player.isParticipating()) {
                    toRemove.add(player);
                    continue; // Skip to the next player
                }

                output(player.getID() + ", would you like to participate in the current quest? (Y/N)");
                player.setParticipating(askToParticipate(player));

                //DRAW CARD ONLY IF THEY CHOOSE TO PARTICIPATE
                if(player.isParticipating()){
                    drawAdventureCard(player);
                    if (player.getHand().size() > 12) {
                        trimHand(player);
                    }
                }

                // Update list to remove player if they press no
                if (!player.isParticipating()) {
                    toRemove.add(player);
                }
            }

            initialPlayers.removeAll(toRemove);

            // End the quest if there are no players
            if (initialPlayers.size() == 0) {
                output("There are no more eligible players that can participate. Ending quest.");
                break;
            }

            // Draw adventure card and possibly trim hand
            for (Player player : initialPlayers) {
                output("\n" + player.getID() + " please setup an attack.");

                currentAttack = buildAttack(player);
                attacks.add(currentAttack);

                // Compare and determine if current attack is of less value than the current stage
                if (calculateTotalValue(currentAttack) < calculateTotalValue(stages.get(stageIndex))) {
                    output(player.getID() + "'s attack was insufficient");
                    player.setParticipating(false); // Mark as not participating
                    toRemove.add(player); // Remove player immediately after failing attack
                    clearDisplay();
                } else {
                    output(player.getID() + "'s attack was sufficient");
                    clearDisplay();
                }
            }

            // Remove players who failed the attack or declined participation
            initialPlayers.removeAll(toRemove);

            // If all players are out, end the quest
            if (initialPlayers.size() == 0) {
                output("There are no more eligible players that can participate. Ending quest.");
                break;
            }
            //DISCARD CARDS AFTER FIRST STAGE IS OVER
            discardMultipleCards(attacks);
        }

        for(Player player: initialPlayers){
            output(player.getID() + " is awarded with " + stages.size() + " shields");
            int award = player.getShields() + stages.size();
            player.setShields(award);
        }

        discardMultipleCards(stages);
        for(int i = 0; i < stages.size();i++) { //loop for each stage
            for(int m = 0; m < stages.get(i).size(); m++){ //loop for each card in the stage
                drawAdventureCard(sponsoringPlayer);
            }
            drawAdventureCard(sponsoringPlayer);
        }

        if(sponsoringPlayer.getHand().size() > 12){
            trimHand(sponsoringPlayer);
        }

        output("Quest ended.");
        questing = false;
    }


    public void discardMultipleCards(ArrayList<ArrayList<AdventureCard>> cardList){
        for(int i = 0; i < cardList.size();i++){
            for(AdventureCard card: cardList.get(i)){
                adventureDeck.discard(card);
            }
        }
    }
    public int calculateTotalValue(ArrayList<AdventureCard> adventureList){
        int total = 0;
        for(AdventureCard adventureCard: adventureList){
            total += adventureCard.getValue();
        }
        return total;
    }

    public void distributeAdventureCards(){
        for (Player player: players){
            for(int i = 0; i<12;i++){
                drawAdventureCard(player);
                Collections.sort(player.getHand());
            }
        }
    }

    public ArrayList<ArrayList<AdventureCard>> buildQuest(Player sponsorPlayer, QuestCard quest) {
        ArrayList<ArrayList<AdventureCard>> stages = initializeStages(quest.getStages());

        for (int i = 0; i < quest.getStages(); i++) {
            boolean quit = false;
            boolean foeAdded = false;
            Set<String> usedWeapons = new HashSet<>();

            while (!quit) {
                output("Now building Stage " + (i + 1));
                output(sponsorPlayer.getHand().toString());
                output("Enter the position of the card you want to select (1 to " + sponsorPlayer.getHand().size() + ") or 'Quit' to finish the stage:");

                String input = getInput().trim();

                if ("Quit".equalsIgnoreCase(input)) {
                    quit = handleQuitInput(i, stages, foeAdded);
                } else {
                    try {
                        int pos = Integer.parseInt(input) - 1;
                        if (pos >= 0 && pos < sponsorPlayer.getHand().size()) {
                            // Get the selected card
                            AdventureCard card = sponsorPlayer.getHand().get(pos);

                            // If the card is a foe, check if it has already been added (only one foe allowed)
                            if (card.getSubType().equals("foe")) {
                                if (handleFoeCard(card, sponsorPlayer, stages, i, foeAdded)) {
                                    foeAdded = true; // Set foeAdded to true after successfully adding a foe
                                }
                            } else if (card.getSubType().equals("weapon")) {
                                handleWeaponCard(card, sponsorPlayer, stages, i, usedWeapons);
                            } else {
                                output("Invalid card type for this stage. You can only add a foe or weapon.");
                            }
                            output("Stage " + (i + 1) + ":" + stages.get(i) + "\n");
                        } else {
                            output("Invalid card position. Please try again.");
                        }
                    } catch (NumberFormatException e) {
                        output("Invalid input. Input must be an integer, please enter a number or 'Quit'.");
                    }
                }
            }
        }

        gameStages = stages;
        clearDisplay();
        output("Built all stages. Now starting quest: " + quest.getName());
        questing = true;
        return stages;
    }


    public ArrayList<AdventureCard> buildAttack(Player player) {
        HashSet<String> usedWeapons = new HashSet<>();
        ArrayList<AdventureCard> attack = new ArrayList<>();

        output("Now initiating " + player.getID() + "'s attack.");
        output("Enter 'Quit' to quit.");

        boolean quit = false;
        while (!quit) {
            output(player.getHand().toString());
            output("Enter the position of the card you want to select (1 to " + player.getHand().size() + ") or 'Quit' to end building this attack");

            String input = getInput().trim();

            if ("Quit".equalsIgnoreCase(input)) {
                quit = true;
            } else {
                try {
                    int pos = Integer.parseInt(input) - 1;
                    // Check if the input is within the valid range
                    if (pos < 0 || pos >= player.getHand().size()) {
                        output("Error: Input out of range. Please enter a number between 1 and " + player.getHand().size() + ".");
                    } else {
                        // Get the selected card
                        AdventureCard card = player.getHand().get(pos);
                        if (card.getSubType().equals("foe")) {
                            output("Foes cannot be used in attacks.");
                        } else if (card.getSubType().equals("weapon") && usedWeapons.contains(card.getName())) {
                            output("Cannot use two of the same weapon in an attack.");
                        } else {
                            player.discardHand(player.getHand().indexOf(card));
                            output(player.getID() + " added " + card.getName() + " to their attack.");

                            // Add the weapon to the usedWeapons set to track it
                            usedWeapons.add(card.getName());

                            // Add the card to the attack
                            attack.add(card);

                           output("Current attack: " + attack);
                        }
                    }
                } catch (NumberFormatException e) {
                    output("Invalid input. Please enter a number or 'Quit' to finish.");
                }
            }
        }
        output(player.getID() + " has finished building their attack.");
        output("Final attack: " + attack);
        return attack;
    }


    public boolean handleFoeCard(AdventureCard card, Player sponsorPlayer, ArrayList<ArrayList<AdventureCard>> stages, int stageIndex, boolean foeAdded){
        if (foeAdded) {
            output("A foe has already been added to this stage. Please choose a different card.");
            return false;
        }
        // Add foe to the stage
        sponsorPlayer.discardHand(sponsorPlayer.getHand().indexOf(card));
        stages.get(stageIndex).add(card);
        output("Added foe: " + card);
        return true;
    }

    private boolean handleWeaponCard(AdventureCard card, Player sponsorPlayer, ArrayList<ArrayList<AdventureCard>> stages, int stageIndex, Set<String> usedWeapons) {
        if (usedWeapons.contains(card.getName())) {
            output("This weapon has already been used in this stage. Please choose a different card.");
            return false;
        }
        // Add weapon to the stage
        sponsorPlayer.discardHand(sponsorPlayer.getHand().indexOf(card));
        stages.get(stageIndex).add(card);
        usedWeapons.add(card.getName());
        output("Added weapon: " + card);
        return true;
    }

    public boolean handleQuitInput(int stageIndex, ArrayList<ArrayList<AdventureCard>> stages, boolean foeAdded) {
        if (stages.get(stageIndex).isEmpty()) {
            output("A stage cannot be empty");
            return false;
        } else if (stageIndex > 0 && !isStageGreater(stages.get(stageIndex - 1),stages.get(stageIndex))) {
            output("Insufficient value for this stage\n");
            return false;
        } else if(!foeAdded){
            output("There must be at least one foe per stage\n");
            return false;
        }else {
            output("Finished building stage " + (stageIndex + 1));
            output("Final Stage: " + stages.get(stageIndex) + "\n\n");
            return true;
        }
    }

    public ArrayList<ArrayList<AdventureCard>> initializeStages(int numStages) {
        // All stages will be represented using a list of lists, containing the adventure cards that make up that stage
        ArrayList<ArrayList<AdventureCard>> stages = new ArrayList<>();
        for (int i = 0; i < numStages; i++) {
            stages.add(new ArrayList<>());
        }
        return stages;
    }

    public boolean isStageGreater(ArrayList<AdventureCard> previousStage, ArrayList<AdventureCard> currentStage){
        int previousStageTotal = 0;
        int currentStageTotal = 0;

        for(AdventureCard card: previousStage){
            previousStageTotal+= card.getValue();
        }

        for(AdventureCard card: currentStage){
            currentStageTotal+= card.getValue();
        }

        if(currentStageTotal > previousStageTotal){
            return true;
        }

        return false;
    }


    public int trimHand(Player player) {
        int n = player.getHand().size() - 12;
        output(player.getID() + ": ");
        output("You must discard " + n + " cards.");

        for (int i = 0; i < n; i++) {
            output(player.getHand().toString());
            int pos = -1;

            do {
                output("Enter the position of the card you want to select (1 to " + player.getHand().size() + "):");

                try {
                    String input = getInput();
                    pos = Integer.parseInt(input) - 1;

                    if (pos < 0 || pos >= player.getHand().size()) {
                        output("Invalid input. Please enter a valid position.");
                        pos = -1;
                    }
                } catch (NumberFormatException e) {
                    output("Invalid input. Please enter a number.");
                    pos = -1;
                }
            } while (pos == -1);

            Card card = player.discardHand(pos);
            adventureDeck.discard(card);
            output(card + " was successfully discarded");
            output("Updated Hand: " + player.getHand() + "\n\n");
            clearDisplay();
        }

        return n;
    }



    public Deck getAdventureDeck(){
        return adventureDeck;
    }

    public void setAdventureDeck(Deck deck){
        this.adventureDeck = deck;
    }

    public Deck getEventDeck(){
        return eventDeck;
    }

    public Set<Player> getWinners(){
        return winners;
    }

    public void displayHand(Player player){


    }

    public void setEventDeck(Deck eventDeck){
        this.eventDeck = eventDeck;
    }

    public boolean isGameFinished(){
        return finished;
    }
    public void clearDisplay() {
        // for (int i = 0; i < 50; i++) {
        //     System.out.print("\n");
        // }
    }

    public Player getSponsoringPlayer(){
        return sponsoringPlayer;
    }
    public Player setSponsoringPlayer(Player sponsoringPlayer){
        return this.sponsoringPlayer = sponsoringPlayer;
    }

}