package A3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;  // Change to JUnit 5 Test
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {

        //write down Chrome version in read me / video
        String chromedriverPath = Paths.get("src", "test", "resources", "chromedriver.exe").toAbsolutePath().toString();
        System.setProperty("webdriver.chrome.driver", chromedriverPath);

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("http://127.0.0.1:8081");
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void waitForText(String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("game-output"), text));
    }

    private void sendInput(String input) throws InterruptedException {
        WebElement inputField = driver.findElement(By.id("userInput"));
        inputField.sendKeys(input);
        driver.findElement(By.id("send-button")).click();
        Thread.sleep(700);
    }



    @Test
    public void test_A1_Scenario() throws InterruptedException {

        driver.findElement(By.id("test-A1-scenario")).click();
        Thread.sleep(1000);

        // Wait for initial game state

        waitForText("It is now P1's turn");
        waitForText("Hand: [F5, F5, F15, F15, Dagger, Sword, Sword, Horse, Horse, Battle Axe, Battle Axe, Lance]");

        // P1 draws quest and sponsors it
        waitForText("Would you like to sponsor this quest, P1? (Y/N)");
        Thread.sleep(1000);

        sendInput("N");

        Thread.sleep(1000);

        waitForText("Would you like to sponsor this quest, P2? (Y/N)");
        Thread.sleep(1000);
        sendInput("Y");

        //Stage 1
        Thread.sleep(1000);

        sendInput("1"); //F5
        Thread.sleep(1000);
        sendInput("7"); //Horse
        Thread.sleep(1000);
        sendInput("Quit");

        //Stage 2
        Thread.sleep(500);

        sendInput("2"); //F15
        Thread.sleep(1000);
        sendInput("5"); //Sword
        Thread.sleep(1000);
        sendInput("Quit");

        //Stage 3
        Thread.sleep(500);

        sendInput("2"); //F15
        Thread.sleep(1000);
        sendInput("3"); //Dagger
        Thread.sleep(1000);
        sendInput("4"); //Battle Axe
        Thread.sleep(1000);
        sendInput("Quit");

        //Stage 4
        Thread.sleep(500);

        sendInput("2"); //F40
        Thread.sleep(1000);
        sendInput("3"); //Battle-Axe
        Thread.sleep(1000);
        sendInput("Quit");

        //Participation

        //P1
        Thread.sleep(500);

        sendInput("Y");
        sendInput("1"); //Discard F5

        //P3
        Thread.sleep(500);

        sendInput("Y");
        sendInput("1"); //Discard F5

        //P4
        Thread.sleep(500);

        sendInput("Y");
        Thread.sleep(1000);
        sendInput("1"); //Discard F5

        //Build Attacks, stage 1
        //P1
        Thread.sleep(500);

        sendInput("5"); //Dagger
        sendInput("5"); //Sword
        sendInput("Quit");

        //P3
        Thread.sleep(500);

        sendInput("5"); //Sword
        sendInput("4"); //Dagger
        sendInput("Quit");

        //P4
        Thread.sleep(500);

        sendInput("4"); //Dagger
        sendInput("6"); //Horse
        sendInput("Quit");



        //All participate, stage 2
        sendInput("Y");
        sendInput("Y");
        sendInput("Y");
        Thread.sleep(500);

        //P1 Attack Stage 2
        sendInput("7"); //Horse
        sendInput("6"); //Sword
        Thread.sleep(500);
        //Ensure correct hand after setting up attack for P1
        waitForText("[F5, F10, F15, F15, F30, Horse, Battle Axe, Battle Axe, Lance]");
        sendInput("Quit");

        //P3
        sendInput("9"); //Axe
        sendInput("4"); //Sword
        sendInput("Quit");
        Thread.sleep(1000);

        //P4
        sendInput("6"); //Horse
        sendInput("6"); //Axe
        sendInput("Quit");
        Thread.sleep(1000);

        //Stage 2 resolution, assert P1 hand and shield count
        verifyPlayerHand("p1","[F5, F10, F15, F15, F30, Horse, Battle Axe, Battle Axe, Lance]");
        assertEquals(0,getP1Shields());

        //All accept to participate stage 3
        sendInput("Y");
        sendInput("Y");
        Thread.sleep(1000);

        //P3
        sendInput("10"); //Lance
        sendInput("6"); //Horse
        sendInput("4"); //Sword
        sendInput("Quit");
        Thread.sleep(1000);

        //P4
        sendInput("7"); //Axe
        Thread.sleep(500);
        sendInput("5"); //Sword
        Thread.sleep(500);
        sendInput("7"); //Lance
        Thread.sleep(500);
        sendInput("Quit");
        Thread.sleep(500);

        //Participate stage 4
        sendInput("Y");
        sendInput("Y");
        Thread.sleep(1000);

        //P3 Attack Stage 4
        sendInput("7"); //Axe
        Thread.sleep(500);
        sendInput("6"); //Horse
        Thread.sleep(500);
        sendInput("6"); //Lance
        sendInput("Quit");
        Thread.sleep(500);

        //P4 Attack Stage 4
        sendInput("4"); //Dagger
        Thread.sleep(500);
        sendInput("4"); //Sword
        Thread.sleep(500);
        sendInput("4"); //Lance
        Thread.sleep(500);
        sendInput("5"); //Excalibur
        Thread.sleep(500);
        sendInput("Quit");
        Thread.sleep(500);


        //Assert Shields for P3 and P4
        Thread.sleep(1000);
        assertEquals(0, getP3Shields());
        assertEquals(4, getP4Shields());

        //Assert Hand Size and Hands for P3 and P4
        Thread.sleep(500);
        assertEquals(5, getP3CardCount());
        assertEquals(4, getP4CardCount());

        Thread.sleep(1000);
        //Verify P2 has 16 cards
        assertEquals(16,getP2CardCount());



        //P1 Discard 4 cards
        sendInput("1");
        sendInput("1");
        sendInput("1");
        sendInput("1");

        driver.findElement(By.id("show-hands-button")).click();

        verifyPlayerHand("p1", "[F5, F10, F15, F15, F30, Horse, Battle Axe, Battle Axe, Lance]"); //Ensure it has not changed
        verifyPlayerHand("p3", "[F5, F5, F15, F30, Sword]");
        verifyPlayerHand("p4","[F15, F15, F40, Lance]");


        Thread.sleep(8000);

    }

    @Test
    public void test_2winner_game_2winner_quest() throws InterruptedException {

        driver.findElement(By.id("test-2winner_game_2winner_quest")).click();
        Thread.sleep(1000);
        waitForText("Hand: [F5, F5, F10, F10, F15, F15, Dagger, Horse, Horse, Battle Axe, Battle Axe, Lance]");

        waitForText("P1 Draws the card: Q4");

        sendInput("Y"); //Accept Quest
        Thread.sleep(800);

        //Stage 1
        sendInput("1"); //F5
        Thread.sleep(500);
        sendInput("quit");
        Thread.sleep(800);

        //Stage 2
        sendInput("1"); //F5
        Thread.sleep(500);
        sendInput("5"); //Dagger
        Thread.sleep(500);
        sendInput("quit");
        Thread.sleep(800);

        //Stage 3
        sendInput("1"); //F10
        Thread.sleep(500);
        sendInput("4"); //Horse
        Thread.sleep(500);
        sendInput("quit");
        Thread.sleep(800);

        //Stage 4
        sendInput("1"); //F10
        Thread.sleep(500);
        sendInput("5"); //Axe
        sendInput("quit");
        Thread.sleep(800);

        //Accept / Discard
        sendInput("y"); //P2
        sendInput("1"); //Discard F5
        Thread.sleep(800);

        sendInput("y"); //P3
        sendInput("1"); //Discard F5
        Thread.sleep(800);

        sendInput("y"); //P4
        sendInput("1"); //Discard F10
        Thread.sleep(800);

        //Build Stage 1 Attacks
        sendInput("6"); //Horse
        sendInput("quit");
        Thread.sleep(800);

        sendInput("quit"); //P3 AFK
        Thread.sleep(800);

        sendInput("6"); //Horse
        sendInput("quit");
        Thread.sleep(800);

        //Assert Card Counts
        assertEquals(11,getP2CardCount());
        assertEquals(12, getP3CardCount());
        assertEquals(11,getP4CardCount());

        //Stage 2
        sendInput("y"); //P2
        sendInput("y"); //P4
        Thread.sleep(800);

        sendInput("4"); //Sword
        sendInput("quit");
        Thread.sleep(800);

        sendInput("4"); //Sword
        sendInput("quit");
        Thread.sleep(800);

        //Card Counts
        assertEquals(11,getP2CardCount());
        assertEquals(11,getP4CardCount());

        //Stage 3
        sendInput("y"); //P2
        sendInput("y"); //P4
        Thread.sleep(800);

        //Wait for P2 Hand
        waitForText("[F10, F30, F40, F50, Sword, Sword, Horse, Battle Axe, Battle Axe, Lance, Lance, Excalibur]");
        sendInput("7"); //Horse
        sendInput("5"); //Sword
        sendInput("quit");
        Thread.sleep(800);

        waitForText("[F15, F30, F50, F70, Sword, Sword, Horse, Battle Axe, Battle Axe, Lance, Lance, Excalibur]");
        sendInput("7"); //Horse
        sendInput("5"); //Sword
        sendInput("quit");
        Thread.sleep(800);

        //Assert Card Counts
        assertEquals(10,getP2CardCount());
        assertEquals(10,getP4CardCount());


        //Stage 4
        sendInput("y"); //P2
        waitForText("P2 has chosen to participate");
        sendInput("y"); //P4
        waitForText("P4 has chosen to participate");
        Thread.sleep(800);


        sendInput("6"); //Sword
        sendInput("6"); //Axe
        sendInput("quit");
        Thread.sleep(800);

        sendInput("6"); //Sword
        sendInput("6"); //Axe
        sendInput("quit");
        Thread.sleep(800);

        //Assert Card Counts
        assertEquals(9,getP2CardCount());
        assertEquals(9,getP4CardCount());


        Thread.sleep(1000);
        //Quest Finished
        waitForText("P2 is awarded with 4 shields");
        waitForText("P4 is awarded with 4 shields");

        Thread.sleep(1000);
        //Assert P2 Shields
        assertEquals(4,getP2Shields());
        //Assert P4 Shields
        assertEquals(4,getP4Shields());
        //Assert P3 Shields, ensure they did not win any


        //P1 Discard Cards
        waitForText("You must discard 4 cards.");
        sendInput("1");
        Thread.sleep(500);
        sendInput("1");
        Thread.sleep(500);
        sendInput("1");
        Thread.sleep(500);
        sendInput("1");
        Thread.sleep(800);

        sendInput(""); //Continue to Next Turn

        waitForText("Would you like to sponsor this quest, P2? (Y/N)");

        sendInput("N"); //P2 Decline
        Thread.sleep(800);

        sendInput("y"); //P3 Accept
        Thread.sleep(800);

        //Build Stages
        sendInput("1"); //F5
        sendInput("quit");
        Thread.sleep(800);

        //Stage 2
        sendInput("1"); //F5
        sendInput("3"); //Dagger
        sendInput("quit");
        Thread.sleep(800);

        //Stage 3
        sendInput("1");
        sendInput("4");
        sendInput("quit");
        Thread.sleep(800);


        //Participation
        sendInput("N"); //P1 Decline
        sendInput("Y"); //P2 Participate
        sendInput("Y"); //P4 Participate
        Thread.sleep(800);

        //Stage 1
        sendInput("6"); //Dagger P2
        sendInput("quit");
        Thread.sleep(800);

        sendInput("6"); //Dagger P4
        sendInput("quit");
        Thread.sleep(800);

        //Participate
        sendInput("y");
        sendInput("y");
        Thread.sleep(800);

        //Stage 2
        sendInput("7"); //axe p2
        sendInput("quit");
        Thread.sleep(800);

        sendInput("7"); //axe p4
        sendInput("quit");
        Thread.sleep(800);

        //Accept Quest

        sendInput("y");
        sendInput("y");
        Thread.sleep(800);

        //Stage 3
        sendInput("10"); //Excalibur
        sendInput("quit");
        Thread.sleep(800);

        sendInput("10"); //Excalibur
        sendInput("quit");
        Thread.sleep(800);

        waitForText("P2 is awarded with 3 shields");
        waitForText("P4 is awarded with 3 shields");


        //Assert that they won the game
        Thread.sleep(1000);
        assertEquals(7,getP2Shields());
        assertEquals(7, getP4Shields());
        assertEquals(0, getP1Shields());
        assertEquals(0, getP3Shields());

        //P3 Discards
        sendInput("1"); //Discard F20
        Thread.sleep(500);
        sendInput("2"); //Discard F25
        Thread.sleep(500);
        sendInput("2"); //Discard F30
        Thread.sleep(500);
        sendInput(""); //Pass Turn
        Thread.sleep(800);



        //Assert Expected Card counts
        assertEquals(12, getP1CardCount());
        assertEquals(9, getP2CardCount());
        assertEquals(12, getP3CardCount());
        assertEquals(9, getP4CardCount());


        //Assert each player's hand
        Thread.sleep(2000);
        driver.findElement(By.id("show-hands-button")).click();


        Thread.sleep(8000);

        verifyPlayerHand("p1","[F15, F15, F20, F20, F20, F20, F25, F25, F30, Horse, Battle Axe, Lance]");
        verifyPlayerHand("p2","[F10, F15, F15, F25, F30, F40, F50, Lance, Lance]");
        verifyPlayerHand("p3","[F20, F40, Dagger, Dagger, Sword, Horse, Horse, Horse, Horse, Battle Axe, Battle Axe, Lance]");
        verifyPlayerHand("p4","[F15, F15, F20, F25, F30, F50, F70, Lance, Lance]");

    }

    @Test
    public void test_1winner_game_with_events() throws InterruptedException {

        driver.findElement(By.id("test-1winner_game_with_events")).click();

        waitForText("Hand: [F5, F5, F10, F10, F15, F15, F20, F20, Dagger, Dagger, Dagger, Dagger]"); //Wait for P1 Hand
        waitForText("Quest card drawn: Q4");
        waitForText("Would you like to sponsor this quest, P1? (Y/N)");

        //P1 Sponsors
        sendInput("y");
        Thread.sleep(500);

        //Build Stages
        sendInput("1"); //F5
        sendInput("quit");
        Thread.sleep(500);

        sendInput("2"); //10
        sendInput("quit");
        Thread.sleep(500);

        sendInput("3"); //15
        sendInput("quit");
        Thread.sleep(500);

        sendInput("4"); //20
        sendInput("quit");
        Thread.sleep(500);

        //All participate and Discard
        sendInput("y");
        sendInput("1");
        Thread.sleep(500);

        sendInput("y");
        sendInput("1");
        Thread.sleep(500);

        sendInput("y");
        sendInput("1");
        Thread.sleep(500);


        //Stage 1, same attack, sword
        sendInput("3");
        sendInput("quit");
        Thread.sleep(500);

        sendInput("3");
        sendInput("quit");
        Thread.sleep(500);

        sendInput("4");
        sendInput("quit");
        Thread.sleep(500);

        //All participate
        sendInput("y");
        sendInput("y");
        sendInput("y");

        //All build same attack, horse
        sendInput("6");
        sendInput("quit");
        Thread.sleep(500);

        sendInput("6");
        sendInput("quit");
        Thread.sleep(500);

        sendInput("7");
        sendInput("quit");
        Thread.sleep(500);

        //All participate
        sendInput("y");
        sendInput("y");
        sendInput("y");


        //All build same attack, axe
        waitForText("P2 please setup an attack.");
        sendInput("8");
        sendInput("quit");
        Thread.sleep(500);

        waitForText("P3 please setup an attack.");
        sendInput("8");
        sendInput("quit");
        Thread.sleep(500);

        waitForText("P4 please setup an attack.");
        sendInput("9");
        sendInput("quit");
        Thread.sleep(500);

        //Stage 4

        //All participate
        sendInput("y");
        sendInput("y");
        sendInput("y");

        //All attack with Lance
        waitForText("P2 please setup an attack");
        sendInput("10");
        sendInput("quit");
        Thread.sleep(500);

        waitForText("P3 please setup an attack");
        sendInput("10");
        sendInput("quit");
        Thread.sleep(500);

        waitForText("P4 please setup an attack");
        sendInput("11");
        sendInput("quit");
        Thread.sleep(500);

        //Assert Card Count
        waitForText("P4 is awarded with 4 shields");
        Thread.sleep(1000);

        //Test Card Count and Shield

        //Assert that each player has 11 cards
        assertEquals(11,getP2CardCount());
        assertEquals(11,getP3CardCount());
        assertEquals(11,getP4CardCount());


        //Assert that each player was awarded with 4 shields and has 4 shields at this point
        assertEquals(4,getP2Shields());
        assertEquals(4,getP3Shields());
        assertEquals(4,getP4Shields());

        //P1 Discards
        sendInput("1");
        sendInput("1");
        sendInput("2");
        sendInput("2");

        Thread.sleep(2000);

        //Assert that P1 has 12 cards
        assertEquals(12, getP1CardCount());

        waitForText("Quest ended.");
        sendInput("");

        //P2 Draws Plague
        waitForText("P2 Draws the card: Plague");
        Thread.sleep(1000);
        assertEquals(2,getP2Shields());
        //Pass Turn
        sendInput("");

        //P3 Draws Prosperity
        waitForText("P3 Draws the card: Prosperity");

        waitForText("P1: ");
        sendInput("1"); //Discard F5
        sendInput("1"); //Discard F10
        Thread.sleep(1000);

        assertEquals(12,getP1CardCount());

        //P2 Draw
        waitForText("P2: ");
        sendInput("1"); //Discard F5
        Thread.sleep(1000);
        assertEquals(12,getP2CardCount());

        //P3 Draw
        waitForText("P3: ");
        sendInput("1"); //Discard F5
        Thread.sleep(1000);
        assertEquals(12,getP3CardCount());

        //P4 Draw
        waitForText("P4: ");
        sendInput("1"); //Discard F20
        Thread.sleep(1000);
        assertEquals(12,getP4CardCount());

        //Pass Turn
        waitForText("P3's turn has ended.");
        sendInput("");

        //P4 Draws Queen's Favor
        waitForText("P4 Draws the card: Queen's Favor");
        sendInput("2");
        sendInput("4");

        waitForText("P4's turn has ended.");
        sendInput("");

        Thread.sleep(1000);

        waitForText("P1 Draws the card: Q3");
        sendInput("y");

        //P1 Builds Quest

        //Stage 1
        sendInput("1"); //F15
        sendInput("quit");
        Thread.sleep(500);

        //Stage 2
        sendInput("1"); //F15
        sendInput("7"); //Dagger
        sendInput("quit");
        Thread.sleep(500);

        //Stage 3
        sendInput("4"); //F20
        sendInput("8"); //Dagger
        sendInput("quit");
        Thread.sleep(500);


        waitForText("Built all stages. Now starting quest: Q3");

        //Everyone Participates and Discards
        waitForText("P2, would you like to participate in the current quest? (Y/N)");
        sendInput("y");
        sendInput("1"); //Discard F5

        waitForText("P3, would you like to participate in the current quest? (Y/N)");
        sendInput("y");
        sendInput("1");
        Thread.sleep(500);

        waitForText("P4, would you like to participate in the current quest? (Y/N)");
        sendInput("y");
        sendInput("1");
        Thread.sleep(500);


        //Attacks for Stage 1
        waitForText("P2 please setup an attack.");
        sendInput("10"); //P2 Attacks with Battle axe
        sendInput("quit");
        Thread.sleep(500);

        waitForText("P3 please setup an attack.");
        sendInput("10"); //P3 Attacks with Battle axe
        sendInput("quit");
        Thread.sleep(500);

        waitForText("P4 please setup an attack.");
        sendInput("10"); //P3 Attacks with Horse
        sendInput("quit");
        Thread.sleep(500);

        //Participate
        sendInput("y");
        sendInput("y");


        //Attacks for Stage 2
        waitForText("P2 please setup an attack.");
        sendInput("10"); //P2 Attacks with Battle axe
        sendInput("9"); //P2 attacks with horse
        sendInput("quit");
        Thread.sleep(500);

        waitForText("P3 please setup an attack.");
        sendInput("10"); //P3 Attacks with Battle axe
        sendInput("5"); //P3 attacks with Sword
        sendInput("quit");
        Thread.sleep(500);

        //Participate stage 3
        sendInput("y");
        sendInput("y");
        Thread.sleep(500);

        //Build attacks stage 3
        sendInput("10");
        sendInput("5");
        sendInput("quit");
        Thread.sleep(500);

        //P3
        sendInput("11"); //excal
        sendInput("quit");
        Thread.sleep(500);


        waitForText("You must discard 3 cards.");
        sendInput("1");
        sendInput("1");
        sendInput("1");

        waitForText("Quest ended");
        sendInput("");

        Thread.sleep(2000);
        //Assert Shields

        //P3 has 7 shields (winner)
        assertEquals(7, getP3Shields());

        //Assert the rest
        assertEquals(5, getP2Shields());
        assertEquals(0, getP1Shields()); //Never Participated
        assertEquals(4, getP4Shields()); //won first quest

        //Assert Expected Card counts
        assertEquals(12, getP1CardCount());
        assertEquals(9, getP2CardCount());
        assertEquals(10, getP3CardCount());
        assertEquals(11, getP4CardCount());

        Thread.sleep(2000);
        driver.findElement(By.id("show-hands-button")).click();

        verifyPlayerHand("p1","[F25, F25, F35, Dagger, Dagger, Sword, Sword, Sword, Sword, Horse, Horse, Horse]");
        verifyPlayerHand("p2","[F15, F25, F30, F40, Sword, Sword, Sword, Horse, Excalibur]");
        verifyPlayerHand("p3","[F10, F25, F30, F40, F50, Sword, Sword, Horse, Horse, Lance]");
        verifyPlayerHand("p4","[F25, F25, F30, F50, F70, Dagger, Dagger, Sword, Sword, Battle Axe, Lance]");

        Thread.sleep(8000);

    }

    @Test
    public void test_0_winner_quest() throws InterruptedException {
        // Start the game

        driver.findElement(By.id("test-0-winner")).click();
        Thread.sleep(1000);

        waitForText("It is now P1's turn");
        waitForText("Hand: [F50, F70, Dagger, Dagger, Sword, Sword, Horse, Horse, Battle Axe, Battle Axe, Lance, Lance]");

        // P1 draws quest and sponsors it
        waitForText("Would you like to sponsor this quest, P1? (Y/N)");

        sendInput("Y");

        Thread.sleep(1000);
        waitForText("Now building Stage 1");
        sendInput("1");
        Thread.sleep(1000);

        sendInput("2");
        Thread.sleep(1000);

        sendInput("3");
        Thread.sleep(1000);

        sendInput("4");
        Thread.sleep(1000);

        sendInput("5");
        Thread.sleep(1000);

        sendInput("6");
        Thread.sleep(1000);

        sendInput("quit");
        Thread.sleep(1000);

        waitForText("Final Stage: [F50, Dagger, Sword, Horse, Battle Axe, Lance]");



        Thread.sleep(2000);
        waitForText("Now building Stage 2");
        sendInput("1");
        Thread.sleep(1000);

        sendInput("1");
        Thread.sleep(1000);

        sendInput("1");
        Thread.sleep(1000);

        sendInput("1");
        Thread.sleep(1000);

        sendInput("1");
        Thread.sleep(1000);

        sendInput("1");
        Thread.sleep(1000);

        sendInput("quit");
        Thread.sleep(1000);


        waitForText("Final Stage: [F70, Dagger, Sword, Horse, Battle Axe, Lance]");


        //Ensure cards are 0
        assertEquals(0, getP1CardCount(), "P1 should have 0 cards after building stages");
        Thread.sleep(1000);

        //P2 Participate
        sendInput("Y");
        Thread.sleep(1000);
        sendInput("1"); //discard F5
        Thread.sleep(1000);

        //P3
        sendInput("Y");
        Thread.sleep(1000);
        sendInput("4"); //discard f15
        Thread.sleep(1000);

        //P4
        sendInput("Y");
        Thread.sleep(1000);
        sendInput("3"); //discard f10
        Thread.sleep(1000);

        //Attacks
        sendInput("12"); //Excalibur
        Thread.sleep(1000);
        sendInput("quit");
        Thread.sleep(1000);

        //P4 and P3 do not attack
        sendInput("quit");
        Thread.sleep(1000);
        sendInput("quit");
        Thread.sleep(1000);

        Thread.sleep(2000);

        //Ensure P1 drew 14
        assertEquals(14, getP1CardCount(), "P1 should have 0 cards after building stages");
        Thread.sleep(2000);

        //P1 discard F5, F10
        sendInput("1");
        Thread.sleep(1000);
        sendInput("1");
        Thread.sleep(2000);

        //p1 discarded 2 to get back to 12, ensure this happened
        assertEquals(12, getP1CardCount());
        waitForText("P1's turn has ended.\n");

        Thread.sleep(3000);
        driver.findElement(By.id("show-hands-button")).click();
        Thread.sleep(8000);

        verifyPlayerHand("p1","[F15, Dagger, Dagger, Dagger, Dagger, Sword, Sword, Sword, Horse, Horse, Horse, Horse]" );
        verifyPlayerHand("p2","[F5, F5, F10, F15, F15, F20, F20, F25, F30, F30, F40]");
        verifyPlayerHand("p3","[F5, F5, F10, F15, F15, F20, F20, F25, F25, F30, F40, Lance]");
        verifyPlayerHand("p4","[F5, F5, F10, F15, F15, F20, F20, F25, F25, F30, F50, Excalibur]");

    }

    private int getP1Shields() {
        return extractShieldCount(driver.findElement(By.id("p1-status")).getText());
    }

    private int getP2Shields(){
        return extractShieldCount(driver.findElement(By.id("p2-status")).getText());
    }

    private int getP3Shields(){
        return extractShieldCount(driver.findElement(By.id("p3-status")).getText());
    }

    private int getP4Shields(){
        return extractShieldCount(driver.findElement(By.id("p4-status")).getText());
    }

    private int getP1CardCount(){
        return extractCardCount(driver.findElement(By.id("p1-status")).getText());
    }

    private int getP2CardCount(){
        return extractCardCount(driver.findElement(By.id("p2-status")).getText());
    }

    private int getP3CardCount(){
        return extractCardCount(driver.findElement(By.id("p3-status")).getText());
    }

    private int getP4CardCount(){
        return extractCardCount(driver.findElement(By.id("p4-status")).getText());
    }


    private void verifyPlayerHand(String playerId, String expectedHand) {
        driver.findElement(By.id("show-hands-button")).click();

        String actualHand = driver.findElement(By.id(playerId + "-hand")).getText();
        assertTrue(actualHand.contains(expectedHand), String.format("Expected hand '%s' not found in '%s'", expectedHand, actualHand));
    }



    private int extractShieldCount(String statusText) {
            String shieldsPart = statusText.split("Shields: ")[1];
            return Integer.parseInt(shieldsPart.split(",")[0]);
    }

    private int extractCardCount(String statusText) {
        String cardsPart = statusText.split("Cards: ")[1];
        return Integer.parseInt(cardsPart.split("\\s")[0]);
    }
}