from db import Database

class CountryLanguageDAO:
    def __init__(self, db: Database):
        self.db = db

    def create_language(self, country_code, language, is_official, percentage):
        conn = self.db.get_connection()
        cur = conn.cursor()
        cur.execute("""
            INSERT INTO countrylanguage (CountryCode, Language, IsOfficial, Percentage)
            VALUES (%s,%s,%s,%s)
            """, (country_code, language, is_official, percentage)
        )
        conn.commit()
        cur.close()
        conn.close()

    def get_languages_by_country(self, country_code):
        conn = self.db.get_connection()
        cur = conn.cursor(dictionary=True)
        cur.execute("SELECT * FROM countrylanguage WHERE CountryCode=%s", (country_code,))
        results = cur.fetchall()
        cur.close()
        conn.close()
        return results

    def get_all_languages(self):
        conn = self.db.get_connection()
        cur = conn.cursor(dictionary=True)
        cur.execute("SELECT * FROM countrylanguage")
        results = cur.fetchall()
        cur.close()
        conn.close()
        return results

    def update_language_percentage(self, country_code, language, percentage):
        conn = self.db.get_connection()
        cur = conn.cursor()
        cur.execute(
            "UPDATE countrylanguage SET Percentage=%s WHERE CountryCode=%s AND Language=%s",
            (percentage, country_code, language)
        )
        conn.commit()
        cur.close()
        conn.close()


    def delete_language(self, country_code, language):
        conn = self.db.get_connection()
        cur = conn.cursor()
        cur.execute("DELETE FROM countrylanguage WHERE CountryCode=%s AND Language=%s", (country_code, language))
        conn.commit()
        cur.close()
        conn.close()
