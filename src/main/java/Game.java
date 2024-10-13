import java.util.*;

public class Game {

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


    public Game() {
        adventureDeckSetup();
        eventDeckSetup();
    }

    public void play() {
        winners.clear();
        while (!finished) {
            playTurn();
            for (Player player : players) {
                if (player.getShields() >= 7) {
                    winners.add(player);
                }
            }
            if (!winners.isEmpty()) {
                System.out.println("Winners: " + winners);
                System.out.println("Now ending game...");
                finished = true;
            }

        }
    }

    public void playTurn() {
        System.out.println("It is now " + players[currentPlayerNum].getID() + "'s turn.");
        System.out.println("Hand: " + players[currentPlayerNum].getHand());
        drawEventCard(players[currentPlayerNum]);
        if (players[currentPlayerNum].getHand().size() > 12) {
            trimHand(players[currentPlayerNum]);
        }
        System.out.println(players[currentPlayerNum] + "'s turn has ended.");

        System.out.println("Press <return> to end and pass your turn.");

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // This waits for the player to press <return>

        clearDisplay();

        currentPlayerNum = (currentPlayerNum + 1) % players.length;
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
        if (adventureDeck.deckSize() > 0) {
            AdventureCard card = (AdventureCard) adventureDeck.getCards().remove(0);
            player.addCardToHand(card);
            Collections.sort(player.getHand());

        }
    }

    public Card drawEventCard(Player player) {
        if (eventDeck.deckSize() == 0) {
            eventDeck.reShuffle();
        }

        Card card = eventDeck.getCards().remove(0);

        System.out.println(player.getID() + " Draws the card: " + card);

        if (card instanceof EventCard) {
            ((EventCard) card).handleEvent((EventCard) card, player, this);
            eventDeck.discard(card); //event card needs to be discarded after effect is resolved
        }

        if (card instanceof QuestCard) {
            sponsorQuest(card);
        }


        return card;
    }

    public void sponsorQuest(Card card) {
        Player startingPlayer = players[currentPlayerNum];

        boolean questSponsored = false;

        while (!questSponsored) {
            questSponsored = askToSponsorQuest(players[currentPlayerNum]);
            if (questSponsored) {
                System.out.println(players[currentPlayerNum].getID() + " has sponsored the quest");
                ((QuestCard) card).setSponsored(true);
                sponsoringPlayer = players[currentPlayerNum];
                buildQuest(sponsoringPlayer, (QuestCard) card);
                break;
            }
            currentPlayerNum = (currentPlayerNum + 1) % players.length;

            if (players[currentPlayerNum].getID().equals(startingPlayer.getID())) {
                break;
            }
        }
        if (!(((QuestCard) card).isSponsored())) {
            eventDeck.discard(card);
            System.out.println("Nobody sponsored");
            System.out.println("Discarding the quest: " + card.getName());
        }
    }

    public boolean askToSponsorQuest(Player player) {
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.println("Would you like to sponsor this quest, " + player.getID() + "? (Y/N)");

            input = scanner.nextLine().trim().toUpperCase();

            // check valid input
            if (!input.equals("Y") && !input.equals("N")) {
                System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
            }

        } while (!input.equals("Y") && !input.equals("N"));

        return input.equals("Y"); //returns true if wants to sponsor
    }

    public boolean askToParticipate(Player player) {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        String input = null;

        while (!validInput) {
            input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y")) {
                System.out.println(player.getID() + " has chosen to participate");
                return true;
            } else if (input.equals("N")) {
                System.out.println(player.getID() + " has chosen to forfeit");
                return false;
            } else {
                System.out.println("Invalid Input, Y/N");
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

        System.out.println("Eligible players: " + initialPlayers);

        for (int stageIndex = 0; stageIndex < stages.size(); stageIndex++) {
            ArrayList<Player> toRemove = new ArrayList<>();
            System.out.println("Current stage: " + (stageIndex + 1));

            // Determine if player wants to participate
            for (Player player : initialPlayers) {
                if (!player.isParticipating()) {
                    toRemove.add(player);
                    continue; // Skip to the next player
                }

                System.out.println(player.getID() + ", would you like to participate in the current quest?");
                player.setParticipating(askToParticipate(player));

                // Update list to remove player if they press no
                if (!player.isParticipating()) {
                    toRemove.add(player);
                }
            } //

            initialPlayers.removeAll(toRemove);

            // End the quest if there are no players
            if (initialPlayers.size() == 0) {
                System.out.println("There are no more eligible players that can participate. Ending quest.");
                break;
            }

            // Draw adventure card and possibly trim hand
            for (Player player : initialPlayers) {
                System.out.println("\n" + player.getID() + " please setup an attack.");
                drawAdventureCard(player);
                if (player.getHand().size() > 12) {
                    trimHand(player);
                }
                currentAttack = buildAttack(player);
                attacks.add(currentAttack);

                // Compare and determine if current attack is of less value than the current stage
                if (calculateTotalValue(currentAttack) < calculateTotalValue(stages.get(stageIndex))) {
                    System.out.println(player.getID() + "'s attack was insufficient");
                    player.setParticipating(false); // Mark as not participating
                    toRemove.add(player); // Remove player immediately after failing attack
                } else {
                    System.out.println(player.getID() + "'s attack was sufficient");
                }
            }

            // Remove players who failed the attack or declined participation
            initialPlayers.removeAll(toRemove);

            // If all players are out, end the quest
            if (initialPlayers.size() == 0) {
                System.out.println("There are no more eligible players that can participate. Ending quest.");
                break;
            }
        }

        for(Player player: initialPlayers){
            System.out.println(player.getID() + " is awarded with " + stages.size() + " shields");
            player.setShields(stages.size());
        }
        System.out.println("Quest ended.");
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
        Scanner scanner = new Scanner(System.in);
        ArrayList<ArrayList<AdventureCard>> stages = initializeStages(quest.getStages());

        for (int i = 0; i < quest.getStages(); i++) {
            boolean quit = false;
            boolean foeAdded = false; // Track if a foe has been added to the current stage
            Set<String> usedWeapons = new HashSet<>(); // Track used weapon names for the current stage

            while (!quit) {
                System.out.println("Now building Stage " + (i + 1));
                System.out.println(sponsorPlayer.getHand());
                System.out.println("Enter the position of the card you want to select (1 to " + sponsorPlayer.getHand().size() + ") or 'Quit' to finish the stage:");
                String input = scanner.next().trim();

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
                                System.out.println("Invalid card type for this stage. You can only add a foe or weapon.");
                            }
                            System.out.println("Stage " + (i + 1) + ":" + stages.get(i) + "\n");
                        } else {
                            System.out.println("Invalid card position. Please try again.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Input must be an integer, please enter a number or 'Quit'.");
                    }
                }
            }
        }


        clearDisplay();
        System.out.println("Built all stages. Now starting quest: " + quest.getName());
        return stages;
    }

    /**
     *
     *make this return arraylist so that the cards can all be removed in one shot later
     */
    public ArrayList<AdventureCard> buildAttack(Player player) {
        Scanner scanner = new Scanner(System.in);
        HashSet<String> usedWeapons = new HashSet<>();
        ArrayList<AdventureCard> attack = new ArrayList<>();

        System.out.println("Now initiating " + player.getID() + "'s attack.");
        System.out.println("Enter 'Quit' to quit.");

        boolean quit = false;

        while (!quit) {
            System.out.println(player.getHand());
            System.out.println("Enter the position of the card you want to select (1 to " + player.getHand().size() + ") or 'Quit' to end building this attack");
            String input = scanner.next().trim();
            if ("Quit".equalsIgnoreCase(input)) {
                quit = true;
            } else {
                try {
                    int pos = Integer.parseInt(input) - 1;
                    // Check if the input is within the valid range
                    if (pos < 0 || pos >= player.getHand().size()) {
                        System.out.println("Error: Input out of range. Please enter a number between 1 and " + player.getHand().size() + ".");
                    } else {
                        // Get the selected card
                        AdventureCard card = player.getHand().get(pos);
                        if (card.getSubType().equals("foe")) {
                            System.out.println("Foes cannot be used in attacks.");
                        } else if (card.getSubType().equals("weapon") && usedWeapons.contains(card.getName())) {
                            System.out.println("Cannot use two of the same weapon in an attack.");
                        } else {
                            player.discardHand(player.getHand().indexOf(card));
                            System.out.println(player.getID() + " added " + card.getName() + " to their attack.");

                            // Add the weapon to the usedWeapons set to track it
                            usedWeapons.add(card.getName());

                            // Add the card to the attack
                            attack.add(card);

                            System.out.println("Current attack: " + attack);
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number or 'Quit' to finish.");
                }
            }
        }
        System.out.println(player.getID() + " has finished building their attack.");
        System.out.println("Final attack: " + attack);
        return attack;
    }


    public boolean handleFoeCard(AdventureCard card, Player sponsorPlayer, ArrayList<ArrayList<AdventureCard>> stages, int stageIndex, boolean foeAdded){
        if (foeAdded) {
            System.out.println("A foe has already been added to this stage. Please choose a different card.");
            return false;
        }
        // Add foe to the stage
        sponsorPlayer.discardHand(sponsorPlayer.getHand().indexOf(card));
        stages.get(stageIndex).add(card);
        System.out.println("Added foe: " + card);
        return true;
    }
    private boolean handleWeaponCard(AdventureCard card, Player sponsorPlayer, ArrayList<ArrayList<AdventureCard>> stages, int stageIndex, Set<String> usedWeapons) {
        if (usedWeapons.contains(card.getName())) {
            System.out.println("This weapon has already been used in this stage. Please choose a different card.");
            return false;
        }
        // Add weapon to the stage
        sponsorPlayer.discardHand(sponsorPlayer.getHand().indexOf(card));
        stages.get(stageIndex).add(card);
        usedWeapons.add(card.getName());
        System.out.println("Added weapon: " + card);
        return true;
    }

    public boolean handleQuitInput(int stageIndex, ArrayList<ArrayList<AdventureCard>> stages, boolean foeAdded) {
        if (stages.get(stageIndex).isEmpty()) {
            System.out.println("A stage cannot be empty");
            return false;
        } else if (stageIndex > 0 && !isStageGreater(stages.get(stageIndex - 1),stages.get(stageIndex))) {
            System.out.println("Insufficient value for this stage\n");
            return false;
        } else if(!foeAdded){
            System.out.println("There must be at least one foe per stage\n");
            return false;
        }else {
            System.out.println("Finished building stage " + (stageIndex + 1));
            System.out.println("Final Stage: " + stages.get(stageIndex) + "\n\n");
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
        System.out.println("You must discard " + n + " cards.");
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < n; i++) {
            System.out.println(player.getHand());
            int pos = -1;

            do {
                System.out.println("Enter the position of the card you want to select (1 to " + player.getHand().size() + "):");

                if (scanner.hasNextInt()) {
                    pos = scanner.nextInt() - 1;

                    // Validate if it's within range
                    if (pos < 0 || pos >= player.getHand().size()) {
                        System.out.println("Invalid input. Please enter a valid position.");
                        pos = -1;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();
                }
            } while (pos == -1);

            Card card = player.discardHand(pos);
            adventureDeck.discard(card);
            System.out.println(card + " was successfully discarded");
            System.out.println("Updated Hand: "+ player.getHand());

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
        for (int i = 0; i < 50; i++) {
            System.out.print("\n");
        }
    }

    public Player getSponsoringPlayer(){
        return sponsoringPlayer;
    }
    // for testing purposes
    public Player setSponsoringPlayer(Player sponsoringPlayer){
        return this.sponsoringPlayer = sponsoringPlayer;
    }

}


