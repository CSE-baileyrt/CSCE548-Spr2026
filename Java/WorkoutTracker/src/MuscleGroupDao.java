import java.sql.*;
import java.util.*;


public class MuscleGroupDao {


	public void insert(MuscleGroup mg) throws Exception {
		String sql = "INSERT INTO MuscleGroup (name, description, days_per_week, sets, reps, weight) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, mg.getName());
			ps.setString(2, mg.getDescription());
			ps.setInt(3, mg.getDaysPerWeek());
			ps.setInt(4, mg.getSets());
			ps.setInt(5, mg.getReps());
			ps.setDouble(6, mg.getWeight());
			ps.executeUpdate();
		}
	}
	
	
	public List<MuscleGroup> getAll() throws Exception {
		List<MuscleGroup> list = new ArrayList<>();
		String sql = "SELECT * FROM MuscleGroup";
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				list.add(new MuscleGroup(
				rs.getInt("muscle_group_id"),
				rs.getString("name"),
				rs.getString("description"),
				rs.getInt("days_per_week"),
				rs.getInt("sets"),
				rs.getInt("reps"),
				rs.getDouble("weight")
				));
			}
		}
		return list;
	}
	
	
	public void update(MuscleGroup mg) throws Exception {
		String sql = "UPDATE MuscleGroup SET name=?, description=?, days_per_week=?, sets=?, reps=?, weight=? WHERE muscle_group_id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, mg.getName());
			ps.setString(2, mg.getDescription());
			ps.setInt(3, mg.getDaysPerWeek());
			ps.setInt(4, mg.getSets());
			ps.setInt(5, mg.getReps());
			ps.setDouble(6, mg.getWeight());
			ps.setInt(7, mg.getId());
			ps.executeUpdate();
		}
	}
	
	
	public void delete(int id) throws Exception {
		String sql = "DELETE FROM MuscleGroup WHERE muscle_group_id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.executeUpdate();
		}
	}
}