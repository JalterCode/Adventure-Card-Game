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
        drawEventCard(players[currentPlayerNum]);

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

        eventDeck.discard(card);

        if(card instanceof EventCard){
            ((EventCard) card).handleEvent((EventCard) card, player, this);
        }



        return card;
    }

    public void distributeAdventureCards(){
        for (Player player: players){
            for(int i = 0; i<12;i++){
                drawAdventureCard(player);
                Collections.sort(player.getHand());
            }
        }
    }

    public Deck getAdventureDeck(){
        return adventureDeck;
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
}
