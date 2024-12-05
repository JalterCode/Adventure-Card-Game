package A3;


import io.cucumber.java.jv.Lan;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


@RestController
@CrossOrigin(origins = "http://127.0.0.1:8081")
public class GameController {
    private Game game;
    private BlockingQueue<String> inputQueue = new LinkedBlockingQueue<>();
    private BlockingQueue<String> outputQueue = new LinkedBlockingQueue<>();
    private Thread gameThread;

    @GetMapping("/start")
    public String startGame() {
        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
            try {
                gameThread.join(100);
            } catch (InterruptedException e) {

            }
        }

        outputQueue = new LinkedBlockingQueue<>();
        inputQueue = new LinkedBlockingQueue<>();

        game = new Game();

        game.setIOQueues(inputQueue, outputQueue);
        game.distributeAdventureCards();

        gameThread = new Thread(() -> {
            try {
                Thread.sleep(100); // Small delay to ensure frontend is ready
                game.play();
            } catch (InterruptedException e) {

            }
        });
        gameThread.start();

        return "Game started";
    }

    @PostMapping("/sendInput")
    public String sendInput(@RequestParam(name = "input") String input) {
        try {

            // Clear any old output before processing new input
            outputQueue.clear();
            inputQueue.add(input);
            return "Input received";
        } catch (Exception e) {
            return "Error processing input: " + e.getMessage();
        }
    }

    @GetMapping("/getOutput")
    public String getOutput() {
        try {
            List<String> messages = new ArrayList<>();
            String message;

            message = outputQueue.poll(1, TimeUnit.SECONDS);
            if (message != null) {
                messages.add(message);

                while ((message = outputQueue.poll()) != null) {
                    messages.add(message);
                }

                return String.join("\n", messages);
            }
            return "";
        } catch (InterruptedException e) {
            return "";
        }
    }
    @GetMapping("/getCurrentHands")
    public List<String> getCurrentHands() {
        List<String> hands = new ArrayList<>();
        if (game != null) {
            Player[] players = {game.P1, game.P2, game.P3, game.P4};
            for (Player p : players) {
                hands.add(String.format("%s Hand: %s", p.getID(), p.getHand()));
            }
        }
        return hands;
    }

    @GetMapping("/getGameStatus")
    public List<String> getGameStatus() {
        List<String> status = new ArrayList<>();
        if (game != null) {
            Player[] players = {game.P1, game.P2, game.P3, game.P4};
            for (Player p : players) {
                status.add(String.format("%s - Shields: %d, Cards: %d %s",
                        p.getID(),
                        p.getShields(),
                        p.getHand().size(),
                        (p == players[game.currentPlayerNum] ? "(Current Turn)" : "")
                ));
            }
        }
        return status;
    }

    @GetMapping("/A1_scenario")
    public String testA1Scenario() {

        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
        }

        inputQueue = new LinkedBlockingQueue<>();
        outputQueue = new LinkedBlockingQueue<>();

        game = new Game();

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


        Deck eventDeck = new Deck();
        QuestCard Q4 = new QuestCard("quest", "Q4", 4, 1);
        eventDeck.addCard(Q4);
        game.setEventDeck(eventDeck);

        Deck testDeck = new Deck();

        game.setAdventureDeck(testDeck);
        // remove first 11 cards, to enable the next 9 to be set cards, with everything random after
        for(int i = 0; i>11;i++){
            game.getAdventureDeck().getCards().removeFirst();
        }


        game.getAdventureDeck().getCards().add(0,Lance);
        game.getAdventureDeck().getCards().add(0,F30);
        game.getAdventureDeck().getCards().add(0,Sword);
        game.getAdventureDeck().getCards().add(0,BattleAxe);
        game.getAdventureDeck().getCards().add(0,Lance);
        game.getAdventureDeck().getCards().add(0,Lance);
        game.getAdventureDeck().getCards().add(0,F10);
        game.getAdventureDeck().getCards().add(0,BattleAxe);
        game.getAdventureDeck().getCards().add(0,Sword);
        game.getAdventureDeck().getCards().add(0,F30);


        game.setIOQueues(inputQueue, outputQueue);
        gameThread = new Thread(game::play);
        gameThread.start();


        return "Test initialized";
    }


    @GetMapping("/0_winner_quest")
    public String test0WinnerQuest() {
        // Stop existing game if running
        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
        }

        // Create fresh queues
        inputQueue = new LinkedBlockingQueue<>();
        outputQueue = new LinkedBlockingQueue<>();

        // Create new game and initialize test scenario
        game = new Game();



        // Set up P1's hand
        game.P1.getHand().clear();
        game.P1.addCardToHand(F50);
        game.P1.addCardToHand(F70);
        game.P1.addCardToHand(Dagger);
        game.P1.addCardToHand(Dagger);

        game.P1.addCardToHand(Sword);
        game.P1.addCardToHand(Sword);

        game.P1.addCardToHand(Horse);
        game.P1.addCardToHand(Horse);

        game.P1.addCardToHand(BattleAxe);
        game.P1.addCardToHand(BattleAxe);
        game.P1.addCardToHand(Lance);
        game.P1.addCardToHand(Lance);

        game.P2.getHand().clear();
        game.P2.addCardToHand(F5);
        game.P2.addCardToHand(F5);
        game.P2.addCardToHand(F10);
        game.P2.addCardToHand(F15);
        game.P2.addCardToHand(F15);
        game.P2.addCardToHand(F20);
        game.P2.addCardToHand(F20);
        game.P2.addCardToHand(F25);
        game.P2.addCardToHand(F30);
        game.P2.addCardToHand(F30);
        game.P2.addCardToHand(F40);
        game.P2.addCardToHand(Excalibur);

        game.P3.getHand().clear();
        game.P3.addCardToHand(F5);
        game.P3.addCardToHand(F5);
        game.P3.addCardToHand(F10);
        game.P3.addCardToHand(F15);
        game.P3.addCardToHand(F15);
        game.P3.addCardToHand(F20);
        game.P3.addCardToHand(F20);
        game.P3.addCardToHand(F25);
        game.P3.addCardToHand(F25);
        game.P3.addCardToHand(F30);
        game.P3.addCardToHand(F40);
        game.P3.addCardToHand(Lance);

        game.P4.getHand().clear();
        game.P4.addCardToHand(F5);
        game.P4.addCardToHand(F5);
        game.P4.addCardToHand(F10);
        game.P4.addCardToHand(F15);
        game.P4.addCardToHand(F15);
        game.P4.addCardToHand(F20);
        game.P4.addCardToHand(F20);
        game.P4.addCardToHand(F25);
        game.P4.addCardToHand(F25);
        game.P4.addCardToHand(F30);
        game.P4.addCardToHand(F50);
        game.P4.addCardToHand(Excalibur);

        Deck eventDeck = new Deck();
        QuestCard Q2 = new QuestCard("quest", "Q2", 2, 1);
        eventDeck.addCard(Q2);


        Deck adventureDeck = new Deck();
        adventureDeck.addCard(F5);
        adventureDeck.addCard(F15);
        adventureDeck.addCard(F10);

        adventureDeck.addCard(F5);
        adventureDeck.addCard(F10);
        adventureDeck.addCard(F15);
        adventureDeck.addCard(Dagger);
        adventureDeck.addCard(Dagger);
        adventureDeck.addCard(Dagger);
        adventureDeck.addCard(Dagger);
        adventureDeck.addCard(Horse);
        adventureDeck.addCard(Horse);
        adventureDeck.addCard(Horse);
        adventureDeck.addCard(Horse);
        adventureDeck.addCard(Sword);
        adventureDeck.addCard(Sword);
        adventureDeck.addCard(Sword);

        game.setEventDeck(eventDeck);
        game.setAdventureDeck(adventureDeck);




        game.setIOQueues(inputQueue, outputQueue);
        gameThread = new Thread(game::play);
        gameThread.start();

        return "Test initialized";
    }

    @GetMapping("/2winner_game_2winner_quest")
    public String test2winner_game_2winner_quest() {

        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
        }

        // Create fresh queues
        inputQueue = new LinkedBlockingQueue<>();
        outputQueue = new LinkedBlockingQueue<>();

        // Create new game and initialize test scenario
        game = new Game();


        game.P1.getHand().clear();
        game.P1.addCardToHand(F5);
        game.P1.addCardToHand(F5);
        game.P1.addCardToHand(F10);
        game.P1.addCardToHand(F10);
        game.P1.addCardToHand(F15);
        game.P1.addCardToHand(F15);
        game.P1.addCardToHand(Dagger);
        game.P1.addCardToHand(Horse);
        game.P1.addCardToHand(Horse);
        game.P1.addCardToHand(BattleAxe);
        game.P1.addCardToHand(BattleAxe);
        game.P1.addCardToHand(Lance);

        game.P2.getHand().clear();
        game.P2.addCardToHand(F40);
        game.P2.addCardToHand(F50);
        game.P2.addCardToHand(Horse);
        game.P2.addCardToHand(Horse);
        game.P2.addCardToHand(Sword);
        game.P2.addCardToHand(Sword);
        game.P2.addCardToHand(Sword);
        game.P2.addCardToHand(BattleAxe);
        game.P2.addCardToHand(BattleAxe);
        game.P2.addCardToHand(Lance);
        game.P2.addCardToHand(Lance);
        game.P2.addCardToHand(Excalibur);

        game.P3.getHand().clear();
        game.P3.addCardToHand(F5);
        game.P3.addCardToHand(F5);
        game.P3.addCardToHand(F5);
        game.P3.addCardToHand(F5);
        game.P3.addCardToHand(Dagger);
        game.P3.addCardToHand(Dagger);
        game.P3.addCardToHand(Dagger);
        game.P3.addCardToHand(Horse);
        game.P3.addCardToHand(Horse);
        game.P3.addCardToHand(Horse);
        game.P3.addCardToHand(Horse);
        game.P3.addCardToHand(Horse);

        game.P4.getHand().clear();
        game.P4.addCardToHand(F50);
        game.P4.addCardToHand(F70);
        game.P4.addCardToHand(Horse);
        game.P4.addCardToHand(Horse);
        game.P4.addCardToHand(Sword);
        game.P4.addCardToHand(Sword);
        game.P4.addCardToHand(Sword);
        game.P4.addCardToHand(BattleAxe);
        game.P4.addCardToHand(BattleAxe);
        game.P4.addCardToHand(Lance);
        game.P4.addCardToHand(Lance);
        game.P4.addCardToHand(Excalibur);


        Deck eventDeck = new Deck();
        QuestCard Q4 = new QuestCard("quest", "Q4", 4, 1);
        QuestCard Q3 = new QuestCard("quest", "Q3", 3, 1);
        eventDeck.addCard(Q4);
        eventDeck.addCard(Q3);
        game.setEventDeck(eventDeck);

        Deck adventureDeck = new Deck();
        adventureDeck.addCard(F5);   // P2 draws for stage 1
        adventureDeck.addCard(F40);  // P3 draws for stage 1
        adventureDeck.addCard(F10);  // P4 draws for stage 1
        adventureDeck.addCard(F10);  // P2 draws for stage 2
        adventureDeck.addCard(F30);  // P4 draws for stage 2
        adventureDeck.addCard(F30);  // P2 draws for stage 3
        adventureDeck.addCard(F15);  // P4 draws for stage 3
        adventureDeck.addCard(F15);  // P2 draws for stage 4
        adventureDeck.addCard(F20);  // P4 draws for stage 4

        // Cards P1 draws after the quest
        adventureDeck.addCard(F5);
        adventureDeck.addCard(F10);
        adventureDeck.addCard(F15);
        adventureDeck.addCard(F15);
        adventureDeck.addCard(F20);
        adventureDeck.addCard(F20);
        adventureDeck.addCard(F20);
        adventureDeck.addCard(F20);
        adventureDeck.addCard(F25);
        adventureDeck.addCard(F25);
        adventureDeck.addCard(F30);

        // Cards drawn during the quest
        adventureDeck.addCard(Dagger);  // P2 draws for stage 1
        adventureDeck.addCard(Dagger);  // P4 draws for stage 1
        adventureDeck.addCard(F15);     // P2 draws for stage 2
        adventureDeck.addCard(F15);     // P4 draws for stage 2
        adventureDeck.addCard(F25);     // P2 draws for stage 3
        adventureDeck.addCard(F25);     // P4 draws for stage 3

        // Cards P3 draws after the quest
        adventureDeck.addCard(F20);
        adventureDeck.addCard(F20);
        adventureDeck.addCard(F25);
        adventureDeck.addCard(F30);
        adventureDeck.addCard(Sword);
        adventureDeck.addCard(BattleAxe);
        adventureDeck.addCard(BattleAxe);
        adventureDeck.addCard(Lance);

        game.setAdventureDeck(adventureDeck);

        game.setIOQueues(inputQueue, outputQueue);
        gameThread = new Thread(game::play);
        gameThread.start();
        return "Test initialized";
        
    }


    @GetMapping("/1winner_game_with_events")
    public String test1winner_game_with_events() {

        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
        }

        // Create fresh queues
        inputQueue = new LinkedBlockingQueue<>();
        outputQueue = new LinkedBlockingQueue<>();

        // Create new game and initialize test scenario
        game = new Game();


        // P1's initial hand
        game.P1.getHand().clear();
        game.P1.addCardToHand(F5);
        game.P1.addCardToHand(F5);
        game.P1.addCardToHand(F10);
        game.P1.addCardToHand(F10);
        game.P1.addCardToHand(F15);
        game.P1.addCardToHand(F15);
        game.P1.addCardToHand(F20);
        game.P1.addCardToHand(F20);
        game.P1.addCardToHand(Dagger);
        game.P1.addCardToHand(Dagger);
        game.P1.addCardToHand(Dagger);
        game.P1.addCardToHand(Dagger);

        // P2's initial hand
        game.P2.getHand().clear();
        game.P2.addCardToHand(F25);
        game.P2.addCardToHand(F30);
        game.P2.addCardToHand(Horse);
        game.P2.addCardToHand(Horse);
        game.P2.addCardToHand(Sword);
        game.P2.addCardToHand(Sword);
        game.P2.addCardToHand(Sword);
        game.P2.addCardToHand(BattleAxe);
        game.P2.addCardToHand(BattleAxe);
        game.P2.addCardToHand(Lance);
        game.P2.addCardToHand(Lance);
        game.P2.addCardToHand(Excalibur);

        // P3's initial hand
        game.P3.getHand().clear();
        game.P3.addCardToHand(F25);
        game.P3.addCardToHand(F30);
        game.P3.addCardToHand(Horse);
        game.P3.addCardToHand(Horse);
        game.P3.addCardToHand(Sword);
        game.P3.addCardToHand(Sword);
        game.P3.addCardToHand(Sword);
        game.P3.addCardToHand(BattleAxe);
        game.P3.addCardToHand(BattleAxe);
        game.P3.addCardToHand(Lance);
        game.P3.addCardToHand(Lance);
        game.P3.addCardToHand(Excalibur);

        // P4's initial hand
        game.P4.getHand().clear();
        game.P4.addCardToHand(F25);
        game.P4.addCardToHand(F30);
        game.P4.addCardToHand(F70);
        game.P4.addCardToHand(Horse);
        game.P4.addCardToHand(Horse);
        game.P4.addCardToHand(Sword);
        game.P4.addCardToHand(Sword);
        game.P4.addCardToHand(Sword);
        game.P4.addCardToHand(BattleAxe);
        game.P4.addCardToHand(BattleAxe);
        game.P4.addCardToHand(Lance);
        game.P4.addCardToHand(Lance);

        Deck adventureDeck = new Deck();
        // Cards drawn during the quest
        adventureDeck.addCard(F5);   // P2 draws for stage 1
        adventureDeck.addCard(F10);  // P3 draws for stage 1
        adventureDeck.addCard(F20);  // P4 draws for stage 1
        adventureDeck.addCard(F15);  // P2 draws for stage 2
        adventureDeck.addCard(F5);   // P3 draws for stage 2
        adventureDeck.addCard(F25);  // P4 draws for stage 2
        adventureDeck.addCard(F5);   // P2 draws for stage 3
        adventureDeck.addCard(F10);  // P3 draws for stage 3
        adventureDeck.addCard(F20);  // P4 draws for stage 3
        adventureDeck.addCard(F5);   // P2 draws for stage 4
        adventureDeck.addCard(F10);  // P3 draws for stage 4
        adventureDeck.addCard(F20);  // P4 draws for stage 4

        // Cards P1 draws after the quest
        adventureDeck.addCard(F5);
        adventureDeck.addCard(F5);
        adventureDeck.addCard(F10);
        adventureDeck.addCard(F10);
        adventureDeck.addCard(F15);
        adventureDeck.addCard(F15);
        adventureDeck.addCard(F15);
        adventureDeck.addCard(F15);


        // Prosperity Draws
        adventureDeck.addCard(F25);  // P1 draws
        adventureDeck.addCard(F25);  // P1 draws
        adventureDeck.addCard(Horse); // P2 draws
        adventureDeck.addCard(Sword); // P2 draws
        adventureDeck.addCard(BattleAxe); // P3 draws
        adventureDeck.addCard(F40);  // P3 draws
        adventureDeck.addCard(Dagger);  // P4 draws
        adventureDeck.addCard(Dagger);  // P4 draws

        //Queen's Favor Draws
        adventureDeck.addCard(F30);
        adventureDeck.addCard(F25);

        // Cards drawn during the Second quest
        adventureDeck.addCard(BattleAxe);  // P2 draws for stage 1
        adventureDeck.addCard(Horse);      // P3 draws for stage 1
        adventureDeck.addCard(F50);        // P4 draws for stage 1
        adventureDeck.addCard(Sword);      // P2 draws for stage 2
        adventureDeck.addCard(Sword);      // P3 draws for stage 2
        adventureDeck.addCard(F40);        // P2 draws for stage 3
        adventureDeck.addCard(F50);        // P3 draws for stage 3

        // Cards P1 draws after the quest
        adventureDeck.addCard(Horse);      // P1 draws
        adventureDeck.addCard(Horse);      // P1 draws
        adventureDeck.addCard(Horse);      // P1 draws
        adventureDeck.addCard(Sword);      // P1 draws
        adventureDeck.addCard(Sword);      // P1 draws
        adventureDeck.addCard(Sword);      // P1 draws
        adventureDeck.addCard(Sword);      // P1 draws
        adventureDeck.addCard(F35);        // P1 draws

        Deck eventDeck = new Deck();

        QuestCard Q4 = new QuestCard("quest", "Q4", 4, 1);
        EventCard Plague = new EventCard("event", "Plague", 1);
        EventCard Prosperity = new EventCard("event", "Prosperity", 1);
        EventCard QueenFavor = new EventCard("event", "Queen's Favor", 1);
        QuestCard Q3 = new QuestCard("quest", "Q3", 3, 1);

        eventDeck.addCard(Q4);
        eventDeck.addCard(Plague);
        eventDeck.addCard(Prosperity);
        eventDeck.addCard(QueenFavor);
        eventDeck.addCard(Q3);

        game.setAdventureDeck(adventureDeck);
        game.setEventDeck(eventDeck);

        game.setIOQueues(inputQueue, outputQueue);
        gameThread = new Thread(game::play);
        gameThread.start();

        return "Test Initialized";


    }

    private AdventureCard F5 = new AdventureCard("adventure", "F5", 5, 1, "foe");
    private AdventureCard F10 = new AdventureCard("adventure", "F10", 10, 1, "foe");
    private AdventureCard F15 = new AdventureCard("adventure", "F15", 15, 1, "foe");
    private AdventureCard F20 = new AdventureCard("adventure", "F20", 20, 1, "foe");
    private AdventureCard F25 = new AdventureCard("adventure", "F25", 25, 1, "foe");
    private AdventureCard F30 = new AdventureCard("adventure", "F30", 30, 1, "foe");
    private AdventureCard F35 = new AdventureCard("adventure", "F35", 35, 1, "foe");
    private AdventureCard F40 = new AdventureCard("adventure", "F40", 40, 1, "foe");
    private AdventureCard F50 = new AdventureCard("adventure", "F50", 50, 1, "foe");
    private AdventureCard F70 = new AdventureCard("adventure", "F70", 70, 1, "foe");
    private AdventureCard Dagger = new AdventureCard("adventure", "Dagger", 5, 1, "weapon");
    private AdventureCard Horse = new AdventureCard("adventure", "Horse", 10, 1, "weapon");
    private AdventureCard Sword = new AdventureCard("adventure", "Sword", 10, 1, "weapon");
    private AdventureCard BattleAxe = new AdventureCard("adventure", "Battle Axe", 15, 1, "weapon");
    private AdventureCard Lance = new AdventureCard("adventure", "Lance", 20, 1, "weapon");
    private AdventureCard Excalibur = new AdventureCard("adventure", "Excalibur", 30, 1, "weapon");

}