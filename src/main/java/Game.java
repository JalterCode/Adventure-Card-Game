import java.util.*;

public class Game {

    private Deck adventureDeck = new Deck();

    private Deck eventDeck = new Deck();

    Player P1 = new Player("P1");
    Player P2 = new Player("P2");
    Player P3 = new Player("P3");
    Player P4 = new Player("P4");

    Player[] players = {P1,P2,P3,P4};

    private Set<Player> winners = new HashSet<>();

    private Player sponsoringPlayer = null;

    public int currentPlayerNum = 0;
    boolean finished = false;


    public Game(){
        adventureDeckSetup();
        eventDeckSetup();
    }

    public void play() {
        winners.clear();
        while(!finished){
            playTurn();
            for(Player player: players){
                if(player.getShields()>= 7){
                    winners.add(player);
                }
            }
            if(!winners.isEmpty()){
                System.out.println("Winners: " + winners);
                System.out.println("Now ending game...");
                finished = true;
            }

        }
    }

    public void playTurn(){
        System.out.println("It is now " + players[currentPlayerNum].getID() + "'s turn.");
        System.out.println("Hand: " + players[currentPlayerNum].getHand());
        drawEventCard(players[currentPlayerNum]);
        if(players[currentPlayerNum].getHand().size() > 12){
            trimHand(players[currentPlayerNum]);
        }
        System.out.println(players[currentPlayerNum] + "'s turn has ended.");

        System.out.println("Press <return> to end and pass your turn.");

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // This waits for the player to press <return>

        clearDisplay();

        currentPlayerNum = (currentPlayerNum + 1) % players.length;
    }



    public void adventureDeckSetup(){
        //Foe cards
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
            adventureDeck.addCard(card);
        }
        adventureDeck.shuffle();
    }


    public void eventDeckSetup(){
        QuestCard Q2 = new QuestCard("quest","Q2",2,3);
        QuestCard Q3 = new QuestCard("quest","Q3",3,4);
        QuestCard Q4 = new QuestCard("quest","Q4",4,3);
        QuestCard Q5 = new QuestCard("quest","Q5",5,2);

        EventCard Plague = new EventCard("event","Plague",1);
        EventCard QueenFavor = new EventCard("event","Queen's Favor",2);
        EventCard Prosperity = new EventCard("event","Prosperity",2);

        Card[] cards = { Q2, Q3, Q4, Q5, Plague, QueenFavor, Prosperity };

        for (Card card : cards) {
            eventDeck.addCard(card);
        }
        eventDeck.shuffle();
    }

    public int countOccurrence(Deck deck, Card card){
        int count = 0;

        for(Card searchCard: deck.getCards()){
            if(searchCard.getName().equals(card.getName())){
                count++;
            }
        }
        return count;
    }

    public void drawAdventureCard(Player player){
        if(adventureDeck.deckSize() > 0){
            AdventureCard card = (AdventureCard) adventureDeck.getCards().remove(0);
            player.addCardToHand(card);

        }
    }

    public Card drawEventCard(Player player){
        if(eventDeck.deckSize() == 0){
            eventDeck.reShuffle();
        }

        Card card = eventDeck.getCards().remove(0);

        System.out.println(player.getID() + " Draws the card: " + card);



        if(card instanceof EventCard){
            ((EventCard) card).handleEvent((EventCard) card, player, this);
            eventDeck.discard(card); //event card needs to be discarded after effect is resolved
        }

        if(card instanceof QuestCard){
            sponsorQuest(card);
        }


        return card;
    }
    public void sponsorQuest(Card card){
        Player startingPlayer = players[currentPlayerNum];

        boolean questSponsored = false;

        while(!questSponsored){
            questSponsored = askToSponsorQuest(players[currentPlayerNum]);
            if(questSponsored){
                System.out.println(players[currentPlayerNum].getID() + " has sponsored the quest");
                ((QuestCard) card).setSponsored(true);
                sponsoringPlayer = players[currentPlayerNum];
                break;
            }
            currentPlayerNum = (currentPlayerNum + 1) % players.length;

            if(players[currentPlayerNum].getID().equals(startingPlayer.getID())){
                break;
            }
        }
        if(!(((QuestCard) card).isSponsored())){
            eventDeck.discard(card);
            System.out.println("Nobody sponsored");
            System.out.println("Discarding the quest: " +card.getName());
        }
    }
    public boolean askToSponsorQuest(Player player) {
        Scanner scanner = new Scanner(System.in);
        String input = "";


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
            while (!quit) {
                System.out.println("Now building Stage " + (i + 1));
                System.out.println(sponsorPlayer.getHand());
                System.out.println("Enter the position of the card you want to select (1 to " + sponsorPlayer.getHand().size() + ") or 'Quit' to finish the stage:");
                String input = scanner.next().trim();

                if ("Quit".equalsIgnoreCase(input)) {
                    quit = handleQuitInput(i, stages);

                } else {
                    try {
                        int pos = Integer.parseInt(input) - 1;
                        if (pos >= 0 && pos < sponsorPlayer.getHand().size()) {
                            // Remove card from hand
                            AdventureCard card = sponsorPlayer.discardHand(pos);
                            System.out.println("Added card: " + card);
                            stages.get(i).add(card);
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

        System.out.println("Built all stages.");
        return stages;
    }

    public boolean handleQuitInput(int stageIndex, ArrayList<ArrayList<AdventureCard>> stages) {
        if (stages.get(stageIndex).isEmpty()) {
            System.out.println("A stage cannot be empty");
            return false;
        } else if (stageIndex > 0 && !isStageGreater(stages.get(stageIndex - 1),stages.get(stageIndex))) {
            System.out.println("Insufficient value for this stage\n");
            return false;
        } else {
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

}