package A3;

import A3.AdventureCard;
import A3.Deck;
import A3.EventCard;
import A3.Game;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootApplication
public class  Main {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Main.class, args);
    }
}
