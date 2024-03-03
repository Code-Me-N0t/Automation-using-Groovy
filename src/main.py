from src.modules import *
from src.helpers import *

def trackShipment(driver):
    print(f"{Fore.CYAN}Track Shipment")
    waitElement(driver, "NAV", "MAIN")
    waitElement(driver, "NAV", "CACHE")
    waitClickable(driver, "NAV", "CACHE")
    # Point to ‘E-SERVICES
    findElement(driver, "NAV", "E SERVICE", click=True)
    # Select ‘Tracking’ 
    findElement(driver, "NAV", "TRACKING", click=True)
    waitElement(driver, "TRACKING", "MAIN")

    # Verify text ‘You can search for a maximum of 10 AWB numbers.’
    caption = findElement(driver, "TRACKING", "TEXT")
    assert caption.text == 'You can search for a maximum of 10 AWB numbers.', f'TEXT ASSERTION:{Fore.RED} FAILED {Fore.LIGHTBLACK_EX}[{caption.text}]'
    print(f"TEXT ASSERTION:{Fore.GREEN} PASSED {Fore.LIGHTBLACK_EX}[{caption.text}]")

    # Verify if input is empty or ''
    input = findElement(driver, "TRACKING", "INPUT")
    assert input.text == '', f"EMPTY TEXTBOX ASSERTION:{Fore.RED} FAILED"
    print(f"EMPTY TEXTBOX ASSERTION:{Fore.GREEN} PASSED")


def flightSchedule(driver, origin, destination, flightNumber, date):
    print(f"{Fore.CYAN}\nFlight Schedule")
    waitElement(driver, "NAV", "MAIN")
    # Pointing to ‘E-SERVICES’
    findElement(driver, "NAV", "E SERVICE", click=True)
    # Select Flight Schedule
    findElement(driver, "NAV", "FLIGHT", click=True)
    waitElement(driver, "FLIGHT", "MAIN")

    # Enter ‘SIN’ in ORIGIN
    inputValue(driver, "FLIGHT", "ORIGIN", value=origin)
    # Enter ‘SYD’ in DESTINATION
    inputValue(driver, "FLIGHT", "DESTINATION", value=destination)
    # Select DEPARTURE DATE as ’04 MAR 2024'
    findElement(driver, "FLIGHT", "DEPARTURE BUTTON", click=True)
    findElement(driver, "FLIGHT", "DEPARTURE DATE", click=True)
    findElement(driver, "FLIGHT", "SEARCH", click=True)
    waitElement(driver, "FLIGHT RESULT", "MAIN")

    results = findElements(driver, "FLIGHT RESULT", "RESULT")
    repeat = True
    while repeat:
        for i in range(len(results)):
            result = results[i]
            driver.execute_script("arguments[0].scrollIntoView();", result)
            if flightNumber in result.text and date in result.text:
                # Check that flight number ‘SQ0211’ for ’04 Mar Mon’ is displayed in the search results
                assert flightNumber in result.text and date in result.text, f'FLIGHT NUMBER ASSERTION:{Fore.RED} FAILED'
                actions = ActionChains(driver)
                actions.move_to_element(result).perform()
                print(f'FLIGHT NUMBER ASSERTION:{Fore.GREEN} PASSED {Fore.LIGHTBLACK_EX}[{flightNumber} for {date} is displayed]')
                repeat=False
            if i == 9:
                sleep(3)
                findElement(driver, "FLIGHT RESULT", "NEXT BUTTON", click=True)
            results = findElements(driver, "FLIGHT RESULT", "RESULT")


def ErrorMessageValidation(driver, origin, destination):
    print(f"{Fore.CYAN}\nError Message Validation")
    waitElement(driver, "NAV", "MAIN")
    waitElement(driver, "FLIGHT", "MAIN")
    try:
        findElement(driver, "FLIGHT RESULT", "RESET", click=True)
    except ElementClickInterceptedException:
        flight_schedule = findElement(driver, "FLIGHT RESULT", "RESET")
        clickElement(driver, flight_schedule, 1358, 406)

    inputMain = waitElement(driver, "NAV", "MAIN")
    actions = ActionChains(driver)
    actions.move_to_element(inputMain).perform()

    driver.refresh()
    waitElement(driver, "FLIGHT", "ORIGIN")
    inputValue(driver, "FLIGHT", "ORIGIN", value=origin)
    inputValue(driver, "FLIGHT", "DESTINATION", value=destination)

    # Verify error prompt displays and equal to 'Please fill in missing field(s) to proceed.'    
    findElement(driver, "FLIGHT", "SEARCH", click=True)
    errorMessage = waitText(driver, "FLIGHT RESULT", "MESSAGE", text="Please fill in missing field(s) to proceed")
    errorMessageText = findElement(driver, "FLIGHT RESULT", "MESSAGE")

    assert errorMessage, f"ERROR MESSAGE ASSERTION: {Fore.RED}FAILED {Fore.LIGHTBLACK_EX} [{errorMessageText.text}]"  
    print(f"ERROR MESSAGE ASSERTION: {Fore.GREEN}PASSED {Fore.LIGHTBLACK_EX} [{errorMessageText.text}]\n")