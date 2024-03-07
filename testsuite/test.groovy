// test.groovy
@Grapes([
    @Grab(group='org.seleniumhq.selenium', module='selenium-java', version='3.141.59'),
    // @Grab(group='org.yaml', module='snakeyaml', version='1.29')
])
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.WebDriverWait
import src.Main
import java.util.logging.Level
import java.util.logging.Logger

System.setProperty("webdriver.chrome.silentOutput", "true")
Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF)
System.setProperty("webdriver.chrome.driver", "driver/chromedriver-win64/chromedriver.exe")

WebDriver driver = new ChromeDriver()
WebDriverWait wait = new WebDriverWait(driver, 60)
driver.get("https://www.siacargo.com")
driver.manage().window().maximize();

def data = [
    "origin": "SIN",
    "destination": "SYD",
    "flightNumber": "SQ0211",
    "date": "04 Mar Mon"
]

// TEST CASE
Main.trackShipment(driver)
Main.flightSchedule(driver, data.origin, data.destination, data.flightNumber, data.date)

driver.quit()