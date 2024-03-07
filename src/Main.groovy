// main.groovy
package src
import org.openqa.selenium.support.ui.ExpectedConditions as EC
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import src.helpers
import src.Color

class Main {
    static boolean repeat = true

    static void trackShipment(WebDriver driver) {
        println("Page title: ${driver.getTitle()}")
        println("\nTracking Shipment")
        helpers.waitElement(driver, 30, 'NAV', 'MAIN')
        helpers.waitElement(driver, 30, 'NAV', 'CACHE')
        helpers.findElement(driver, [click: true], 'NAV', 'CACHE')
        helpers.findElement(driver, [click: true], 'NAV', 'E SERVICE')
        helpers.findElement(driver, [click: true], 'NAV', 'TRACKING')
        helpers.waitElement(driver, 30, 'TRACKING', 'MAIN')
        
        WebElement caption = helpers.findElement(driver, [click: false], 'TRACKING', 'TEXT')
        String captionText = caption.getText();
        helpers.assertResult("TEXT ASSERTION", 'You can search for a maximum of 10 AWB numbers.', caption.text)
        
        WebElement input = helpers.findElement(driver, [click: false], 'TRACKING', 'INPUT')
        String inputText = input.getText();
        helpers.assertResult("EMPTY TEXTBOX ASSERTION", '', input.text)
        
    }

    static void flightSchedule(WebDriver driver, String origin = null, String destination = null, String flightNumber = null, String date = null) {
        println("\nFlight Schedule")
        helpers.waitElement(driver, 30, 'NAV', 'MAIN')
        helpers.findElement(driver, [click: true], 'NAV', 'E SERVICE')
        helpers.findElement(driver, [click: true], 'NAV', 'FLIGHT')
        helpers.waitElement(driver, 30, 'FLIGHT', 'MAIN')

        helpers.inputValue(driver, origin, "FLIGHT", "ORIGIN")
        helpers.inputValue(driver, destination, "FLIGHT", "DESTINATION")
        helpers.findElement(driver, [click: true], "FLIGHT", "DEPARTURE BUTTON")
        helpers.findElement(driver, [click: true], "FLIGHT", "DEPARTURE DATE")
        helpers.findElement(driver, [click: true], "FLIGHT", "SEARCH")
        helpers.waitElement(driver, "FLIGHT RESULT", "MAIN")

        Actions actions = new Actions(driver)

        def results = helpers.findElements(driver, "FLIGHT RESULT", "RESULT")
        while (repeat == true) {
            results.eachWithIndex { result, index ->
                actions.moveToElement(result).perform()

                if (result.text.contains(flightNumber) && result.text.contains(date)) {
                    try{
                        assert result.text.contains(flightNumber) && result.text.contains(date)
                        Color.printColor("RESULT ASSERTION: ", Color.GREEN, "PASSED ${Color.GRAY}[${flightNumber} for ${date} is displayed]${Color.RESET}")
                    } catch (AssertionError e) {
                        Color.printColor("RESULT ASSERTION: ", Color.RED, "FAILED")
                    }
                    repeat = false
                }
                if (index == 9) {
                    helpers.waitClickable(driver, 10, "FLIGHT RESULT", "NEXT BUTTON")
                    results = helpers.findElements(driver, "FLIGHT RESULT", "RESULT")
                }
            }
        }
    }
}