package A3;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


@RestController
@CrossOrigin(origins = "http://127.0.0.1:8082/")
public class GameController {

    private Game game;


    //first this, then front end
    //function from previous assignment, draw card, make a get and maybe post functionality
    //after that, setup JS stuff and in the script you can make a function for draw card
    //implement the buttons, if you draw a card do you want to sponsor it, after you draw it, you can make that drawcard disable or hide it
    @GetMapping("/start")
    public String startGame(){
        game = new Game();
        game.distributeAdventureCards();
        return "Game has started";
    }

    @GetMapping("/draw")
    public String draw() {
        if (game == null) {
            return "No game found. Please start a new game.";
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(ps);

        Card cardToDraw = game.getEventDeck().getCards().getFirst();
        game.drawEventCard(game.players[game.currentPlayerNum]);

        System.out.flush();
        System.setOut(oldOut);

        return baos.toString();
    }

}
