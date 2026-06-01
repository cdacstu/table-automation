import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public class TableTest {
    public static void main(String[] args) {
        // Automatically configures the correct Chrome driver binary
        WebDriverManager.chromedriver().setup();
        
        // Headless options required for executing on an unattended environment like Jenkins
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            // i) Open the Target URL
            System.out.println("Navigating to Web Tables page...");
            driver.get("https://demoqa.com/webtables");

            // ii) Click "Add" button and fill out registration form
            System.out.println("Clicking Add button and populating user info...");
            driver.findElement(By.id("addNewRecordButton")).click();
            
            driver.findElement(By.id("firstName")).sendKeys("John");
            driver.findElement(By.id("lastName")).sendKeys("Doe");
            driver.findElement(By.id("userEmail")).sendKeys("john.doe@example.com");
            driver.findElement(By.id("age")).sendKeys("30");
            driver.findElement(By.id("salary")).sendKeys("55000");
            driver.findElement(By.id("department")).sendKeys("QA");
            
            // Submit form
            driver.findElement(By.id("submit")).click();
            System.out.println("Form submitted successfully.");

            // iii) Verify the new row is in the table by checking page source text
            boolean isUserAdded = driver.getPageSource().contains("john.doe@example.com");
            
            if (isUserAdded) {
                System.out.println("----------------------------------------------");
                System.out.println("SUCCESS: Verified new record exists in the web table!");
                System.out.println("----------------------------------------------");
            } else {
                System.out.println("----------------------------------------------");
                System.out.println("FAIL: User record was not found in the table.");
                System.out.println("----------------------------------------------");
                System.exit(1); // Terminates with error status to fail the Jenkins build
            }

        } catch (Exception e) {
            System.out.println("An execution error occurred: " + e.getMessage());
            System.exit(1);
        } finally {
            // Clean up and close driver instance
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
