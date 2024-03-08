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
        Color.printTitle("Track Shipment")
        Helpers.waitElement(driver, 30, 'NAV', 'MAIN')
        Helpers.waitElement(driver, 30, 'NAV', 'CACHE')
        Helpers.findElement(driver, [click: true], 'NAV', 'CACHE')
        // Point to ‘E-SERVICES
        Helpers.findElement(driver, [click: true], 'NAV', 'E SERVICE')
        // Select ‘Tracking’ 
        Helpers.findElement(driver, [click: true], 'NAV', 'TRACKING')
        Helpers.waitElement(driver, 30, 'TRACKING', 'MAIN')
        
        // Assert text ‘You can search for a maximum of 10 AWB numbers.’
        WebElement caption = Helpers.findElement(driver, [click: false], 'TRACKING', 'TEXT')
        Helpers.assertResult("TEXT ASSERTION", 'You can search for a maximum of 10 AWB numbers.', caption.text)
        // Assert if input is empty or ''
        WebElement input = Helpers.findElement(driver, [click: false], 'TRACKING', 'INPUT')
        Helpers.assertResult("EMPTY TEXTBOX ASSERTION", '', input.text)
    }

    static void flightSchedule(WebDriver driver, String origin = null, String destination = null, String flightNumber = null, String date = null) {
        Color.printTitle("Flight Schedule")
        Helpers.waitElement(driver, 30, 'NAV', 'MAIN')
        // Pointing to ‘E-SERVICES’
        Helpers.findElement(driver, [click: true], 'NAV', 'E SERVICE')
        // Select Flight Schedule
        Helpers.findElement(driver, [click: true], 'NAV', 'FLIGHT')
        Helpers.waitElement(driver, 30, 'FLIGHT', 'MAIN')

        // Enter ‘SIN’ in ORIGIN
        Helpers.inputValue(driver, origin, "FLIGHT", "ORIGIN")
        // Enter ‘SYD’ in DESTINATION
        Helpers.inputValue(driver, destination, "FLIGHT", "DESTINATION")
        // Select DEPARTURE DATE as ’04 MAR 2024'
        Helpers.findElement(driver, [click: true], "FLIGHT", "DEPARTURE BUTTON")
        Helpers.findElement(driver, [click: true], "FLIGHT", "DEPARTURE DATE")
        Helpers.findElement(driver, [click: true], "FLIGHT", "SEARCH")
        Helpers.waitElement(driver, "FLIGHT RESULT", "MAIN")

        Actions actions = new Actions(driver)

        def results = Helpers.findElements(driver, "FLIGHT RESULT", "RESULT")
        while (repeat == true) {
            results.eachWithIndex { result, index ->
                actions.moveToElement(result).perform()

                //  Check that flight number ‘SQ0211’ for ’04 Mar Mon’ is displayed in the search results
                if (result.text.contains(flightNumber) && result.text.contains(date)) {
                    try{
                        assert result.text.contains(flightNumber) && result.text.contains(date)
                        Color.printColor("RESULT ASSERTION: ", Color.GREEN, "PASSED ${Color.GRAY}[${flightNumber} for ${date} is displayed]${Color.RESET}")
                    } catch (AssertionError e) {
                        Color.printColor("RESULT ASSERTION: ", Color.RED, "FAILED")
                        e.printStackTrace()}
                    repeat = false
                }
                if (index == 9) {
                    Helpers.waitClickable(driver, 10, "FLIGHT RESULT", "NEXT BUTTON")
                    results = Helpers.findElements(driver, "FLIGHT RESULT", "RESULT")
                }
            }
        }
    }

    static void errorValidation(WebDriver driver, String origin = null, String destination = null){
        Color.printTitle("Error Message Validation")
        // Click on hyperlink 'Reset / New Search'
        Helpers.waitClickable(driver, 10, "FLIGHT RESULT", "RESET")
        // Enter the same origin and destination
        Helpers.inputValue(driver, origin, "FLIGHT", "ORIGIN")
        Helpers.inputValue(driver, destination, "FLIGHT", "DESTINATION")
        // Click search
        Helpers.findElement(driver, [click: true], "FLIGHT", "SEARCH")
        // Assert that the 'Please fill in missing fields to proceed' error prompt is present
        def errorMessage = Helpers.findElement(driver, [click: false], "FLIGHT RESULT", "MESSAGE")
        Helpers.assertResult("ERROR MESSAGE ASSERTION: ", "Please fill in missing field(s) to proceed.", errorMessage.text)
    }
}