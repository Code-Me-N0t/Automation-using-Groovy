from src.modules import *

def findElement(driver, *keys, click=False):
    selector=locator(*keys)
    element = driver.find_element(By.CSS_SELECTOR, selector)
    if click: element.click()
    else: return element

def findElements(driver, *keys):
    selector=locator(*keys)
    element = driver.find_elements(By.CSS_SELECTOR, selector)
    return element

def waitElement(driver, *keys, time=60):
    selector = (By.CSS_SELECTOR, locator(*keys))
    element = WebDriverWait(driver, time).until(EC.visibility_of_element_located(selector))
    return element

def waitClickable(driver, *keys, time=10):
    selector= (By.CSS_SELECTOR, locator(*keys))
    element = WebDriverWait(driver, time).until(EC.element_to_be_clickable(selector))
    element.click()

def waitText(driver, *keys, text):
    selector= (By.CSS_SELECTOR, locator(*keys))
    element = WebDriverWait(driver, 600)
    element.until(EC.text_to_be_present_in_element(selector, text_=text))
    return element

def inputValue(driver, *keys, value):
    selector = (By.CSS_SELECTOR, locator(*keys))
    input_element = driver.find_element(*selector)
    if value:input_element.send_keys(value)
    else:return value

def clickElement(driver, element, x_offset, y_offset):
    element_size = element.size
    
    x_offset = min(max(x_offset, 0), element_size['width'] - 1)
    y_offset = min(max(y_offset, 0), element_size['height'] - 1)

    action = ActionChains(driver)
    action.move_to_element(element)
    action.move_by_offset(x_offset, y_offset)

    action.click().perform()
