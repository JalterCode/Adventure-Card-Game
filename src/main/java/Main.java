import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class  Main {
    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        game.distributeAdventureCards();
        game.play();
    }
}
