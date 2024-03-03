
from src.modules import *
from src.main import *

data = [
    {
        "origin": "SIN",
        "destination": "SYD",
        "flightNumber": "SQ0211",
        "date": "04 Mar Mon"
    },
]

def test_RUN(driver):
    trackShipment(driver)
    for value in data: flightSchedule(driver, **value)
    ErrorMessageValidation(driver, origin=value["origin"], destination=value["destination"])

# terminal: pip install -r requirements.txt
# terminal: pytest -v -s