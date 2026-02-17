import java.sql.*;
import java.util.*;

public class JellyDAO {
	
    public Jelly create(Jelly j) throws Exception {
        String sql = "INSERT INTO Jelly (Brand, Flavor, Price) VALUES (?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, j.getBrand());
            ps.setString(2, j.getFlavor());
            ps.setDouble(3, j.getPrice());
            ps.executeUpdate();
            
            //get the ID from the insert
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                j.setId(keys.getInt(1));
            }
            return j;
        }
    }

    public Jelly readById(int id) throws Exception {
        String sql = "SELECT * FROM Jelly WHERE ID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Jelly(
                        rs.getInt("ID"),
                        rs.getString("Brand"),
                        rs.getString("Flavor"),
                        rs.getDouble("Price"));
            }
        }
        return null;
    }

    public List<Jelly> readAll() throws Exception {
        List<Jelly> list = new ArrayList<>();
        String sql = "SELECT * FROM Jelly";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement()) {

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(new Jelly(
                        rs.getInt("ID"),
                        rs.getString("Brand"),
                        rs.getString("Flavor"),
                        rs.getDouble("Price")));
            }
        }
        return list;
    }

    public void update(Jelly j) throws Exception {
        String sql = "UPDATE Jelly SET Brand=?, Flavor=?, Price=? WHERE ID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, j.getBrand());
            ps.setString(2, j.getFlavor());
            ps.setDouble(3, j.getPrice());
            ps.setInt(4, j.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM Jelly WHERE ID=?")) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}

