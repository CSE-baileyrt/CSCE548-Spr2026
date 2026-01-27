from db import Database

class CityDAO:
    def __init__(self, db: Database):
        self.db = db

    def create_city(self, name, country_code, district, population):
        conn = self.db.get_connection()
        cur = conn.cursor()
        cur.execute(
            "INSERT INTO city (Name, CountryCode, District, Population) VALUES (%s,%s,%s,%s)",
            (name, country_code, district, population)
        )
        conn.commit()
        city_id = cur.lastrowid
        cur.close()
        conn.close()
        return city_id

    def get_city_by_id(self, city_id):
        conn = self.db.get_connection()
        cur = conn.cursor(dictionary=True)
        cur.execute("SELECT * FROM city WHERE ID=%s", (city_id,))
        result = cur.fetchone()
        cur.close()
        conn.close()
        return result

    def get_all_cities(self):
        conn = self.db.get_connection()
        cur = conn.cursor(dictionary=True)
        cur.execute("SELECT * FROM city")
        results = cur.fetchall()
        cur.close()
        conn.close()
        return results

    def update_city(self, city_id, name, district, population):
        conn = self.db.get_connection()
        cur = conn.cursor()
        cur.execute(
            "UPDATE city SET Name=%s, District=%s, Population=%s WHERE ID=%s",
            (name, district, population, city_id)
        )
        conn.commit()
        cur.close()
        conn.close()

    def delete_city(self, city_id):
        conn = self.db.get_connection()
        cur = conn.cursor()
        cur.execute("DELETE FROM city WHERE ID=%s", (city_id,))
        conn.commit()
        cur.close()
        conn.close()