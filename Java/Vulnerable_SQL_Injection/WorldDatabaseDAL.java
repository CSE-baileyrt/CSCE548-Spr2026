import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * WorldDatabaseDAL - Data Access Layer for the MySQL World sample database.
 *
 * ⚠️  EDUCATIONAL PURPOSES ONLY ⚠️
 * This class is INTENTIONALLY VULNERABLE to SQL Injection.
 * It demonstrates common mistakes developers make when building DAL classes.
 *
 * Setup:
 *   1. Install the MySQL World sample database:
 *      https://dev.mysql.com/doc/index-other.html  →  world.sql.gz
 *      mysql -u root -p < world.sql
 *
 *   2. Add the MySQL JDBC driver to your classpath:
 *      https://dev.mysql.com/downloads/connector/j/
 *      javac -cp .:mysql-connector-j-9.x.x.jar WorldDatabaseDAL.java
 *
 * Database credentials – change as needed:
 */
public class WorldDatabaseDAL {

    // -----------------------------------------------------------------------
    // Connection constants – adjust to match your MySQL installation
    // -----------------------------------------------------------------------
    private static final String DB_URL  = "jdbc:mysql://localhost:3306/world";
    private static final String DB_USER = "username";
    private static final String DB_PASS = "password";

    // -----------------------------------------------------------------------
    // getConnection() – opens a new connection each call (fine for a demo)
    // -----------------------------------------------------------------------
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // =======================================================================
    //  VULNERABILITY 1 — String concatenation in a SELECT … WHERE clause
    //
    //  The countryCode parameter is spliced directly into the SQL string.
    //  An attacker can close the string literal and append any SQL they like.
    //
    //  Safe fix: use a PreparedStatement with a '?' placeholder (see comments).
    // =======================================================================
    public List<String[]> getCitiesByCountry(String countryCode) {
        List<String[]> cities = new ArrayList<>();

        // 🔴 VULNERABLE: user input concatenated directly into the query
        String sql = "SELECT Name, District, Population " +
                     "FROM city " +
                     "WHERE CountryCode = '" + countryCode + "'";

        // ✅ SAFE alternative (commented out):
        // String sql = "SELECT Name, District, Population FROM city WHERE CountryCode = ?";
        // try (Connection conn = getConnection();
        //      PreparedStatement ps = conn.prepareStatement(sql)) {
        //     ps.setString(1, countryCode);
        //     ResultSet rs = ps.executeQuery();
        //     ...
        // }

        System.out.println("[DAL] Executing SQL: " + sql);   // logged so the class shows the injected query

        try (Connection conn = getConnection();
             Statement  stmt = conn.createStatement();
             ResultSet  rs   = stmt.executeQuery(sql)) {

            while (rs.next()) {
                cities.add(new String[]{
                    rs.getString("Name"),
                    rs.getString("District"),
                    String.valueOf(rs.getInt("Population"))
                });
            }
        } catch (SQLException e) {
            System.err.println("[DAL] SQL Error: " + e.getMessage());
        }
        return cities;
    }

    // =======================================================================
    //  VULNERABILITY 2 — String concatenation in a SELECT … LIKE clause
    //
    //  The searchTerm is inserted into a LIKE pattern without any escaping.
    //  Attackers can escape the LIKE pattern and inject a UNION SELECT to
    //  pull data from an entirely different table (e.g., countrylanguage).
    //
    //  Safe fix: use PreparedStatement; special LIKE chars (%, _) must also
    //  be escaped with conn.prepareStatement + setString().
    // =======================================================================
    public List<String[]> searchCountries(String searchTerm) {
        List<String[]> results = new ArrayList<>();

        // 🔴 VULNERABLE
        String sql = "SELECT Code, Name, Continent, Population " +
                     "FROM country " +
                     "WHERE Name LIKE '%" + searchTerm + "%'";

        System.out.println("[DAL] Executing SQL: " + sql);

        try (Connection conn = getConnection();
             Statement  stmt = conn.createStatement();
             ResultSet  rs   = stmt.executeQuery(sql)) {

            while (rs.next()) {
                results.add(new String[]{
                    rs.getString("Code"),
                    rs.getString("Name"),
                    rs.getString("Continent"),
                    String.valueOf(rs.getLong("Population"))
                });
            }
        } catch (SQLException e) {
            System.err.println("[DAL] SQL Error: " + e.getMessage());
        }
        return results;
    }

    // =======================================================================
    //  VULNERABILITY 3 — Numeric input treated as a string without validation
    //
    //  The population threshold arrives as a String. If the caller passes a
    //  non-numeric value the application crashes with a SQL error – but worse,
    //  an attacker can inject SQL that bypasses the WHERE clause entirely,
    //  returning every row in the table (authentication-bypass pattern).
    //
    //  Safe fix: parse to long/int first, or use PreparedStatement.setLong().
    // =======================================================================
    public List<String[]> getCountriesAbovePopulation(String minPopulation) {
        List<String[]> results = new ArrayList<>();

        // 🔴 VULNERABLE: numeric column, but value comes from user as a raw string
        String sql = "SELECT Code, Name, Population, GNP " +
                     "FROM country " +
                     "WHERE Population > " + minPopulation;

        System.out.println("[DAL] Executing SQL: " + sql);

        try (Connection conn = getConnection();
             Statement  stmt = conn.createStatement();
             ResultSet  rs   = stmt.executeQuery(sql)) {

            while (rs.next()) {
                results.add(new String[]{
                    rs.getString("Code"),
                    rs.getString("Name"),
                    String.valueOf(rs.getLong("Population")),
                    String.valueOf(rs.getDouble("GNP"))
                });
            }
        } catch (SQLException e) {
            System.err.println("[DAL] SQL Error: " + e.getMessage());
        }
        return results;
    }
}