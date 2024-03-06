// TestCase.groovy
@Grapes([
    @Grab(group='org.seleniumhq.selenium', module='selenium-java', version='3.141.59'),
    @Grab(group='org.yaml', module='snakeyaml', version='1.29')
])
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.WebDriver
import main

System.setProperty("webdriver.chrome.driver", "driver/chromedriver-win64/chromedriver.exe")
WebDriver driver = new ChromeDriver()
WebDriverWait wait = new WebDriverWait(driver, 60)
driver.get("https://www.siacargo.com")
driver.manage().window().maximize();

// Execute the trackShipment method from Main
main.trackShipment(driver)

// Quit the driver
driver.quit()
