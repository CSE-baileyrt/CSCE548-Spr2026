import java.sql.*;
import java.util.*;

public class PeanutButterDAO {

    public PeanutButter create(PeanutButter pb) throws Exception {
        String sql = "INSERT INTO Peanut_Butter (Brand, IsCrunchy, Price) VALUES (?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, pb.getBrand());
            ps.setBoolean(2, pb.isCrunchy());
            ps.setDouble(3, pb.getPrice());
            ps.executeUpdate();
            
            //get the ID from the insert
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                pb.setId(keys.getInt(1));
            }
            return pb;
        }
    }

    public PeanutButter readById(int id) throws Exception {
        String sql = "SELECT * FROM Peanut_Butter WHERE ID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new PeanutButter(
                        rs.getInt("ID"),
                        rs.getString("Brand"),
                        rs.getBoolean("IsCrunchy"),
                        rs.getDouble("Price"));
            }
        }
        return null;
    }

    public List<PeanutButter> readAll() throws Exception {
        List<PeanutButter> list = new ArrayList<>();
        String sql = "SELECT * FROM Peanut_Butter";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement()) {

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(new PeanutButter(
                        rs.getInt("ID"),
                        rs.getString("Brand"),
                        rs.getBoolean("IsCrunchy"),
                        rs.getDouble("Price")));
            }
        }
        return list;
    }

    public void update(PeanutButter pb) throws Exception {
        String sql = "UPDATE Peanut_Butter SET Brand=?, IsCrunchy=?, Price=? WHERE ID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pb.getBrand());
            ps.setBoolean(2, pb.isCrunchy());
            ps.setDouble(3, pb.getPrice());
            ps.setInt(4, pb.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM Peanut_Butter WHERE ID=?")) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
