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

public class SeleniumTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {

        //write down chrome version in read me / video
        String chromedriverPath = Paths.get("src", "test", "resources", "chromedriver.exe")
                .toAbsolutePath()
                .toString();
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

    private void sendInput(String input) {
        WebElement inputField = driver.findElement(By.id("userInput"));
        inputField.sendKeys(input);
        driver.findElement(By.id("send-button")).click();
    }

    @Test
    public void test_0_winner_quest() throws InterruptedException {
        // Start the game

        driver.findElement(By.id("test-0-winner")).click();
        Thread.sleep(1000);

        // Wait for initial game state

        waitForText("It is now P1's turn");
        waitForText("Hand: [F50, F70, Dagger, Dagger, Sword, Sword, Horse, Horse, Battle Axe, Battle Axe, Lance, Lance]");

        // P1 draws quest and sponsors it
        waitForText("Would you like to sponsor this quest, P1? (Y/N)");

        sendInput("Y");

        waitForText("Now building Stage 1");
        sendInput("1");
        sendInput("2");
        sendInput("3");
        sendInput("4");
        sendInput("5");
        sendInput("6");
        sendInput("quit");

        waitForText("Final Stage: [F50, Dagger, Sword, Horse, Battle Axe, Lance]");



        waitForText("Now building Stage 2");
        sendInput("1");
        sendInput("1");
        sendInput("1");
        sendInput("1");
        sendInput("1");
        sendInput("1");
        sendInput("quit");


        waitForText("Final Stage: [F70, Dagger, Sword, Horse, Battle Axe, Lance]");

        String statusText = driver.findElement(By.id("p1-status")).getText();
        int playerCardCount = Integer.parseInt(statusText.split("Cards: ")[1].split(" ")[0]);


        //Ensure cards are 0
        assertEquals(0, playerCardCount, "P1 should have 0 cards after building stages");


    }
}