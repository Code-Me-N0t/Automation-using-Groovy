This Groovy automation script is designed for testing web applications using Selenium. Below is a brief overview of the script files and their functionalities:

test.groovy

• This file serves as the test script where test cases are executed.
• It imports necessary libraries, sets up the WebDriver, defines test data, and executes test cases by calling functions from the src.Main class.
• Test data is provided in a Map format containing details such as origin, destination, flight number, and date.
• The script catches exceptions, prints error messages, and ensures that the WebDriver is properly closed after test execution.

main.groovy

• This file contains the Main class, which defines functions for various test scenarios.
• Functions such as trackShipment(driver), flightSchedule(driver, origin, destination, flightNumber, date), and errorValidation(driver, origin, destination) are defined to perform actions related to tracking shipments, checking flight schedules, and validating error messages.
• These functions utilize helper methods defined in the src.Helpers class to interact with the web application, wait for elements, input values, and validate results.
• The Main class also includes a boolean variable repeat to control the loop while searching for flight results.

Usage

1. Ensure that the necessary dependencies are resolved by using Grape annotations (as shown in the @Grapes annotation in test.groovy).
2. Execute the test script test.groovy to run the test cases.

bash: groovy test.groovy

Note: This readme provides a high-level overview of the Groovy automation script. For detailed implementation and usage instructions, refer to the comments within the script files (test.groovy and main.groovy) and the corresponding modules (src/WebDriverSetup.groovy and src/Helpers.groovy).