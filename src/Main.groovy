// main.groovy
package src
import org.openqa.selenium.support.ui.ExpectedConditions as EC
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import src.Helpers

class Main {
    static boolean repeat = true

    static void trackShipment(WebDriver driver) {
        println("Page title: ${driver.getTitle()}")
        println("\nTrack Shipment")
        Helpers.waitElement(driver, 30, 'NAV', 'MAIN')
        Helpers.waitElement(driver, 30, 'NAV', 'CACHE')
        Helpers.findElement(driver, [click: true], 'NAV', 'CACHE')
        Helpers.findElement(driver, [click: true], 'NAV', 'E SERVICE')
        Helpers.findElement(driver, [click: true], 'NAV', 'TRACKING')
        Helpers.waitElement(driver, 30, 'TRACKING', 'MAIN')
        
        WebElement caption = Helpers.findElement(driver, [click: false], 'TRACKING', 'TEXT')
        Helpers.assertResult("TEXT ASSERTION", 'You can search for a maximum of 10 AWB numbers.', caption.text)
        
        WebElement input = Helpers.findElement(driver, [click: false], 'TRACKING', 'INPUT')
        Helpers.assertResult("EMPTY TEXTBOX ASSERTION", '', input.text)
    }

    static void flightSchedule(WebDriver driver, String origin = null, String destination = null, String flightNumber = null, String date = null) {
        println("\nFlight Schedule")
        Helpers.waitElement(driver, 30, 'NAV', 'MAIN')
        Helpers.findElement(driver, [click: true], 'NAV', 'E SERVICE')
        Helpers.findElement(driver, [click: true], 'NAV', 'FLIGHT')
        
        Helpers.waitElement(driver, 30, 'FLIGHT', 'MAIN')

        Helpers.inputValue(driver, origin, "FLIGHT", "ORIGIN")
        Helpers.inputValue(driver, destination, "FLIGHT", "DESTINATION")
        Helpers.findElement(driver, [click: true], "FLIGHT", "DEPARTURE BUTTON")
        Helpers.findElement(driver, [click: true], "FLIGHT", "DEPARTURE DATE")
        Helpers.findElement(driver, [click: true], "FLIGHT", "SEARCH")
        Helpers.waitElement(driver, "FLIGHT RESULT", "MAIN")

        Actions actions = new Actions(driver)

        def results = Helpers.findElements(driver, "FLIGHT RESULT", "RESULT")
        while (repeat == true) {
            results.eachWithIndex { result, index ->
                actions.moveToElement(result).perform()

                if (result.text.contains(flightNumber) && result.text.contains(date)) {
                    try{
                        assert result.text.contains(flightNumber) && result.text.contains(date)
                        Color.printColor("RESULT ASSERTION: ", Color.GREEN, "PASSED ${Color.GRAY}[${flightNumber} for ${date} is displayed]${Color.RESET}")
                    } catch (AssertionError e) {
                        Color.printColor("RESULT ASSERTION: ", Color.RED, "FAILED")
                        e.printStackTrace()
                    }
                    repeat = false
                }
                if (index == 9) {
                    Helpers.waitClickable(driver, 10, "FLIGHT RESULT", "NEXT BUTTON")
                    results = Helpers.findElements(driver, "FLIGHT RESULT", "RESULT")
                }
            }
        }
    }

    static void ErrorValidation(WebDriver driver, String origin = null, String destination = null){
        
    }
}