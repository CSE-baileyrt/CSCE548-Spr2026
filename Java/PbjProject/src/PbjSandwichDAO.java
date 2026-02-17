import java.sql.*;
import java.util.*;

public class PbjSandwichDAO {

    private BreadDAO breadDAO = new BreadDAO();
    private PeanutButterDAO pbDAO = new PeanutButterDAO();
    private JellyDAO jellyDAO = new JellyDAO();

    public PbjSandwich create(PbjSandwich s) throws Exception {
        String sql = """
            INSERT INTO Pbj_Sandwich
            (Customer, Bread1_ID, Pb_ID, Jelly_ID, Bread2_ID, TotalCost)
            VALUES (?,?,?,?,?,?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, s.getCustomer());
            ps.setInt(2, s.getBread1().getId());
            ps.setInt(3, s.getPb().getId());
            ps.setInt(4, s.getJelly().getId());
            ps.setInt(5, s.getBread2().getId());
            ps.setDouble(6, s.getTotalCost());

            ps.executeUpdate();
            
            //get the ID from the insert
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                s.setId(keys.getInt(1));
            }
            return s;
        }
    }

    public PbjSandwich readById(int id) throws Exception {
        String sql = "SELECT * FROM Pbj_Sandwich WHERE ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new PbjSandwich(
                        rs.getInt("ID"),
                        rs.getString("Customer"),
                        breadDAO.readById(rs.getInt("Bread1_ID")),
                        pbDAO.readById(rs.getInt("Pb_ID")),
                        jellyDAO.readById(rs.getInt("Jelly_ID")),
                        breadDAO.readById(rs.getInt("Bread2_ID")),
                        rs.getDouble("TotalCost")
                );
            }
        }
        return null;
    }

    public List<PbjSandwich> readAll() throws Exception {
        List<PbjSandwich> list = new ArrayList<>();
        String sql = "SELECT * FROM Pbj_Sandwich";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement()) {

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                list.add(readById(rs.getInt("ID")));
            }
        }
        return list;
    }

    public void update(PbjSandwich s) throws Exception {
        String sql = """
            UPDATE Pbj_Sandwich
            SET Customer=?, Bread1_ID=?, Pb_ID=?, Jelly_ID=?, Bread2_ID=?, TotalCost=?
            WHERE ID=?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getCustomer());
            ps.setInt(2, s.getBread1().getId());
            ps.setInt(3, s.getPb().getId());
            ps.setInt(4, s.getJelly().getId());
            ps.setInt(5, s.getBread2().getId());
            ps.setDouble(6, s.getTotalCost());
            ps.setInt(7, s.getId());

            ps.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM Pbj_Sandwich WHERE ID=?")) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}

