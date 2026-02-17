import java.sql.*;
import java.util.*;

public class BreadDAO {

    public Bread create(Bread bread) throws Exception {
        String sql = "INSERT INTO Bread (Brand, WheatLevel, Price) VALUES (?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, bread.getBrand());
            ps.setInt(2, bread.getWheatLevel());
            ps.setDouble(3, bread.getPrice());
            ps.executeUpdate();
            
            //get the ID from the insert
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                bread.setId(keys.getInt(1));
            }
            return bread;
        }
    }

    public void update(Bread bread) throws Exception {
        String sql = "UPDATE Bread SET Brand=?, WheatLevel=?, Price=? WHERE ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bread.getBrand());
            ps.setInt(2, bread.getWheatLevel());
            ps.setDouble(3, bread.getPrice());
            ps.setInt(4, bread.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM Bread WHERE ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Bread readById(int id) throws Exception {
        String sql = "SELECT * FROM Bread WHERE ID=?";
        Bread bread = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                bread = new Bread(
                        rs.getInt("ID"),
                        rs.getString("Brand"),
                        rs.getInt("WheatLevel"),
                        rs.getDouble("Price"));
            }
        }
        return bread;
    }

    public List<Bread> readAll() throws Exception {
        String sql = "SELECT * FROM Bread";
        List<Bread> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                list.add(new Bread(
                        rs.getInt("ID"),
                        rs.getString("Brand"),
                        rs.getInt("WheatLevel"),
                        rs.getDouble("Price")));
            }
        }
        return list;
    }
}
