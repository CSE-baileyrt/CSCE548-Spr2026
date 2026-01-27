from db import Database

class CountryDAO:
    def __init__(self, db: Database):
        self.db = db


    def create_country(self, code, name, continent, region, surface_area, population, capital):
        conn = self.db.get_connection()
        cur = conn.cursor()
        cur.execute("""
            INSERT INTO country (Code, Name, Continent, Region, SurfaceArea, Population, Capital)
            VALUES (%s,%s,%s,%s,%s,%s,%s)
            """, (code, name, continent, region, surface_area, population, capital)
        )
        conn.commit()
        cur.close()
        conn.close()

    def get_country_by_code(self, code):
        conn = self.db.get_connection()
        cur = conn.cursor(dictionary=True)
        cur.execute("SELECT * FROM country WHERE Code=%s", (code,))
        result = cur.fetchone()
        cur.close()
        conn.close()
        return result

    def get_all_countries(self):
        conn = self.db.get_connection()
        cur = conn.cursor(dictionary=True)
        cur.execute("SELECT * FROM country")
        results = cur.fetchall()
        cur.close()
        conn.close()
        return results

    def update_country_population(self, code, population):
        conn = self.db.get_connection()
        cur = conn.cursor()
        cur.execute("UPDATE country SET Population=%s WHERE Code=%s", (population, code))
        conn.commit()
        cur.close()
        conn.close()

    def delete_country(self, code):
        conn = self.db.get_connection()
        cur = conn.cursor()
        cur.execute("DELETE FROM country WHERE Code=%s", (code,))
        conn.commit()
        cur.close()
        conn.close()
