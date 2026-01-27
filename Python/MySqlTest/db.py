import mysql.connector

class Database:
    # these will be overwritten by the test harness code
    def __init__(self, host="localhost", user="root", password="password", database="world"):
        self.config = {
        "host": host,
        "user": user,
        "password": password,
        "database": database
        }

    def get_connection(self):
        return mysql.connector.connect(**self.config)