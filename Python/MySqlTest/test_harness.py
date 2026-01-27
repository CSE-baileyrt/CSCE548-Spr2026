from db import Database
from city_dao import CityDAO
from country_dao import CountryDAO
from country_language_dao import CountryLanguageDAO


if __name__ == "__main__":
    # port 3306 is default, so no need to set it
    db = Database(host="localhost", user="root", password="password", database="world")

    city_dao = CityDAO(db)
    country_dao = CountryDAO(db)
    lang_dao = CountryLanguageDAO(db)

    print("--- Country CRUD ---")
    country_dao.create_country("ZZZ", "TestLand", "Asia", "TestRegion", 1000.5, 100000, None)
    print(country_dao.get_country_by_code("ZZZ"))
    country_dao.update_country_population("ZZZ", 200000)
    print(country_dao.get_country_by_code("ZZZ"))

    print("--- City CRUD ---")
    city_id = city_dao.create_city("Test City", "ZZZ", "District 1", 50000)
    print(city_dao.get_city_by_id(city_id))
    city_dao.update_city(city_id, "Test City Updated", "District 2", 60000)
    print(city_dao.get_city_by_id(city_id))

    print("--- Language CRUD ---")
    lang_dao.create_language("ZZZ", "Testish", "T", 70.0)
    print(lang_dao.get_languages_by_country("ZZZ"))
    lang_dao.update_language_percentage("ZZZ", "Testish", 85.0)
    print(lang_dao.get_languages_by_country("ZZZ"))

    print("--- Cleanup ---")
    lang_dao.delete_language("ZZZ", "Testish")
    city_dao.delete_city(city_id)
    country_dao.delete_country("ZZZ")

    print("All tests completed successfully")

