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


//first this, then front end
//function from previous assignment, draw card, make a get and maybe post functionality
//after that, setup JS stuff and in the script you can make a function for draw card
//implement the buttons, if you draw a card do you want to sponsor it, after you draw it, you can make that drawcard disable or hide it

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8081")
public class GameController {
    private Game game;
    private BlockingQueue<String> inputQueue = new LinkedBlockingQueue<>();
    private BlockingQueue<String> outputQueue = new LinkedBlockingQueue<>();
    private Thread gameThread;

    @GetMapping("/start")
    public String startGame() {
        // Stop existing game and wait for it to finish
        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
            try {
                gameThread.join(100); // Wait up to 100ms for thread to finish
            } catch (InterruptedException e) {
                // Ignore interruption
            }
        }

        // Drain any existing messages
        outputQueue = new LinkedBlockingQueue<>();
        inputQueue = new LinkedBlockingQueue<>();

        // Create new game instance
        game = new Game();

        // Initialize game after queues are set
        game.setIOQueues(inputQueue, outputQueue);
        game.distributeAdventureCards();

        // Start new game thread
        gameThread = new Thread(() -> {
            try {
                Thread.sleep(100); // Small delay to ensure frontend is ready
                game.play();
            } catch (InterruptedException e) {
                // Thread was interrupted, game is stopping
            }
        });
        gameThread.start();

        return "Game started";
    }

    @PostMapping("/sendInput")
    public String sendInput(@RequestParam(name = "input") String input) {
        try {
            // Clear any stale output before processing new input
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

            // Wait up to 1 second for first message
            message = outputQueue.poll(1, TimeUnit.SECONDS);
            if (message != null) {
                messages.add(message);

                // Collect any additional messages without waiting
                while ((message = outputQueue.poll()) != null) {
                    messages.add(message);
                }

                return String.join("\n", messages);
            }
            return "";  // Return empty string if no messages
        } catch (InterruptedException e) {
            return "";
        }
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
        adventureDeck.addCard(new AdventureCard("adventure", "F5", 5, 1, "foe"));
        adventureDeck.addCard(new AdventureCard("adventure", "F15", 15, 1, "foe"));
        adventureDeck.addCard(new AdventureCard("adventure", "F10", 10, 1, "foe"));

        game.setEventDeck(eventDeck);
        game.setAdventureDeck(adventureDeck);




        game.setIOQueues(inputQueue, outputQueue);
        gameThread = new Thread(game::play);
        gameThread.start();

        return "Test initialized";
    }

    private AdventureCard F5 = new AdventureCard("adventure", "F5", 5, 1, "foe");
    private AdventureCard F10 = new AdventureCard("adventure", "F10", 10, 1, "foe");
    private AdventureCard F15 = new AdventureCard("adventure", "F15", 15, 1, "foe");
    private AdventureCard F20 = new AdventureCard("adventure", "F20", 20, 1, "foe");
    private AdventureCard F25 = new AdventureCard("adventure", "F25", 25, 1, "foe");
    private AdventureCard F30 = new AdventureCard("adventure", "F30", 30, 1, "foe");
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