@Grapes([
    @Grab(group='org.seleniumhq.selenium', module='selenium-java', version='3.141.59')
])
import org.openqa.selenium.WebDriver
import src.WebDriverSetup
import src.Main
WebDriver driver = WebDriverSetup.setupWebDriver()

// DATA GIVEN
def data = [
    "origin": "SIN",
    "destination": "SYD",
    "flightNumber": "SQ0211",
    "date": "04 Mar Mon"
]

try{ // TEST CASE
    Main.trackShipment(driver)
    Main.flightSchedule(driver, data.origin, data.destination, data.flightNumber, data.date)
    Main.errorValidation(driver, data.origin, data.destination)


} catch (Exception e){
    println("Exception error: ${e.message}")
    e.printStackTrace()
} finally{
    driver.quit()
}