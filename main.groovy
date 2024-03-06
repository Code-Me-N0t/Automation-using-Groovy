// TestCaseRunner.groovy
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import helpers

class main {
    static void trackShipment(WebDriver driver) {
        println("Page title: ${driver.getTitle()}")
        helpers.waitElement(driver, 30, 'NAV', 'MAIN')
        helpers.waitElement(driver, 30, 'NAV', 'CACHE')
        helpers.findElement(driver, [click: true], 'NAV', 'CACHE')
        helpers.findElement(driver, [click: true], 'NAV', 'E SERVICE')
        helpers.findElement(driver, [click: true], 'NAV', 'TRACKING')
        helpers.waitElement(driver, 30, 'TRACKING', 'MAIN')
        WebElement element = helpers.findElement(driver, [click: false], 'TRACKING', 'TEXT')
        String elementText = element.getText();
        println("Element text: "+elementText)

    }
}
