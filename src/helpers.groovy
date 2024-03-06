//helpers.groovy
package src
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By
import org.yaml.snakeyaml.Yaml
import org.openqa.selenium.WebElement

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
    static WebElement findElements(WebDriver driver, String... keys) {
        def selector = locator(*keys)
        def element = driver.findElements(By.cssSelector(selector))
        return element
    }
    
    static void waitElement(WebDriver driver, int time = 60, String... keys) {
        def selector = By.cssSelector(locator(*keys))
        new WebDriverWait(driver, time).until(ExpectedConditions.visibilityOfElementLocated(selector))
    }

    static void waitClickable(WebDriver driver, int time = 10, String... keys) {
        def selector = By.cssSelector(locator(*keys))
        new WebDriverWait(driver, time).until(ExpectedConditions.elementToBeClickable(selector))
    }
}
