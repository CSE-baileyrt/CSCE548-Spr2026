import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDao {

    private static final String URL =
            "jdbc:mysql://localhost:3306/world?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // INSERT
    public void insertCity(City city) {
        String sql = "INSERT INTO City (Name, CountryCode, District, Population) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, city.getName());
            stmt.setString(2, city.getCountryCode());
            stmt.setString(3, city.getDistrict());
            stmt.setInt(4, city.getPopulation());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public void updateCity(City city) {
        String sql = "UPDATE City SET Name=?, CountryCode=?, District=?, Population=? WHERE ID=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, city.getName());
            stmt.setString(2, city.getCountryCode());
            stmt.setString(3, city.getDistrict());
            stmt.setInt(4, city.getPopulation());
            stmt.setInt(5, city.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE ALL
    public void deleteAllCities() {
        String sql = "DELETE FROM City";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // GET BY ID
    public City getCityById(int id) {
        String sql = "SELECT * FROM City WHERE ID=?";
        City city = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                city = new City(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("CountryCode"),
                        rs.getString("District"),
                        rs.getInt("Population")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return city;
    }

    // GET ALL
    public List<City> getAllCities() {
        List<City> cities = new ArrayList<>();
        String sql = "SELECT * FROM City";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                City city = new City(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("CountryCode"),
                        rs.getString("District"),
                        rs.getInt("Population")
	                );
	                cities.add(city);
	            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cities;
    }
}

