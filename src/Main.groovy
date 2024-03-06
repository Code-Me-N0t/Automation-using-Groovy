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
        assert captionText == 'You can search for a maximum of 10 AWB numbers.' : 'TEXT ASSERTION: FAILED'
        println("TEXT ASSERTION: PASSED")
        
        WebElement input = helpers.findElement(driver, [click: false], 'TRACKING', 'INPUT')
        String inputText = input.getText();
        assert inputText == '' : 'EMPTY TEXTBOX ASSERTION: FAILED'
        println("EMPTY TEXTBOX ASSERTION: PASSED")       
    }
}