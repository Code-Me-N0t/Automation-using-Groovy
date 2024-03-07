//helpers.groovy
package src
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions as EC
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.By
import org.yaml.snakeyaml.Yaml
import org.openqa.selenium.WebElement
import src.Color

class helpers {
    static String locator(String... keys) {
        def yaml = new Yaml()
        def get_locator
        new File("resources/locators.yaml").withReader { reader ->
            get_locator = yaml.load(reader)
        }    
        keys.each { key ->
            get_locator = get_locator[key]
        }
        return get_locator
    }
 
    static WebElement findElement(WebDriver driver, Map options = [:], String... keys) {
        def selector = locator(*keys)
        def element = driver.findElement(By.cssSelector(selector))
        if (options.click) {
            element.click()
        } else {
            return element
        }
    }

    static List<WebElement> findElements(WebDriver driver, String... keys) {
        def selector = locator(*keys)
        def elements = driver.findElements(By.cssSelector(selector))
        return elements
    }
    
    static void waitElement(WebDriver driver, int time = 60, String... keys) {
        def selector = By.cssSelector(locator(*keys))
        new WebDriverWait(driver, time).until(EC.visibilityOfElementLocated(selector))
    }

    static void waitClickable(WebDriver driver, int time = 10, String... keys) {
        def selector = locator(*keys)
        def element = new WebDriverWait(driver, time).until(
            EC.elementToBeClickable(driver.findElement(By.cssSelector(selector)))
        )
        Actions actions = new Actions(driver)
        actions.moveToElement(element).perform()
        element.click()
    }

    static void inputValue(WebDriver driver, String value = null, String... keys) {
        def selector = locator(*keys)
        def element = driver.findElement(By.cssSelector(selector))
        if (value != null && !value.isEmpty()) {
            element.sendKeys(value)
        }
    }

    static void assertResult(String caption, String expectedResult, String actualResult) {
        try {
            assert actualResult == expectedResult
            Color.printColor("${caption}: ", Color.GREEN, "PASSED")
        } catch (AssertionError e) {
            Color.printColor("${caption}: ", Color.RED, "FAILED")
        }
    }
}
