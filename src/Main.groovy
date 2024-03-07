// main.groovy
package src
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import src.helpers

class Main {
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
        try{
            assert captionText == 'You can search for a maximum of 20 AWB numbers.'
            println("TEXT ASSERTION: PASSED")
        } catch (AssertionError e){
            println("TEXT ASSERTION: FAILED")
        }
        
        WebElement input = helpers.findElement(driver, [click: false], 'TRACKING', 'INPUT')
        String inputText = input.getText();
        assert inputText == '' : 'EMPTY TEXTBOX ASSERTION: FAILED'
        println("EMPTY TEXTBOX ASSERTION: PASSED")       
    }

    static void flightSchedule(WebDriver driver, String origin = null, String destination = null) {
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

        def results = helpers.findElements(driver, "FLIGHT RESULT", "RESULT")
        results.each { result ->
            println(result.text)
        }
    }
}