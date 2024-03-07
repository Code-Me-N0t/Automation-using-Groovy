// WebDriverSetup.groovy
package src
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import java.util.logging.Level
import java.util.logging.Logger

class WebDriverSetup {
    static WebDriver setupWebDriver() {
        System.setProperty("webdriver.chrome.silentOutput", "true")
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF)
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver-win64/chromedriver.exe")
        WebDriver driver = new ChromeDriver()
        driver.manage().window().maximize()
        driver.get("https://www.siacargo.com")
        return driver
    }
}