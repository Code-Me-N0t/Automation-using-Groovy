from selenium.common.exceptions import ElementClickInterceptedException, TimeoutException, NoSuchElementException, StaleElementReferenceException
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.chrome.options import Options
import requests, random, pytest, yaml, re, os, os.path
from googleapiclient.discovery import build
from selenium.webdriver.common.by import By
from selenium import webdriver
from colorama import Fore, init
from time import sleep
from selenium.webdriver.common.keys import Keys
from datetime import date
from selenium.webdriver.common.action_chains import ActionChains

init(autoreset=True)

def locator(*keys):
    with open("resources/locator.yaml", "r") as loc:
        get_locator = yaml.load(loc, Loader=yaml.FullLoader)
        for key in keys:
            get_locator = get_locator[key]
        
        return get_locator
    
def execJS(driver, function=None):
    with open(f'resources/script.js','r') as js:
        getScript = js.read()
        script = getScript + f'return {function}'
        run = driver.execute_script(script)
        return run