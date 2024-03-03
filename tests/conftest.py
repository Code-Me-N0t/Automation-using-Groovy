from src.modules import *

@pytest.fixture(scope="session")
def driver():
    gameUrl = 'https://www.siacargo.com'
    option = Options()
    option.add_argument("--incognito")
    option.add_argument(f"--app={gameUrl}")
    driver = webdriver.Chrome(options=option)
    driver.maximize_window()
    yield driver
    driver.quit()

