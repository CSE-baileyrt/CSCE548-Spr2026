import java.sql.*;
import java.sql.Date;
import java.util.*;


public class WorkoutDao {

	
	public void insert(Workout w) throws Exception {
		String sql = "INSERT INTO Workout (workout_date, workout_time, duration_minutes, type, calories_burned, rpe, muscle_group_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setDate(1, Date.valueOf(w.getDate()));
			ps.setTime(2, Time.valueOf(w.getTime()));
			ps.setInt(3, w.getDurationMinutes());
			ps.setString(4, w.getType());
			ps.setInt(5, w.getCaloriesBurned());
			ps.setInt(6, w.getRpe());
			ps.setInt(7, w.getMuscleGroup().getId());
			ps.executeUpdate();
		}
	}
	
	
	public List<Workout> getAll() throws Exception {
		List<Workout> list = new ArrayList<>();
		String sql = "SELECT w.*, m.* FROM Workout w LEFT JOIN MuscleGroup m ON w.muscle_group_id = m.muscle_group_id";
		try (Connection conn = DBUtil.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				MuscleGroup mg = new MuscleGroup(
					rs.getInt("muscle_group_id"), rs.getString("name"), rs.getString("description"),
					rs.getInt("days_per_week"), rs.getInt("sets"), rs.getInt("reps"), rs.getDouble("weight")
				);
				
				
				Workout w = new Workout(
					rs.getInt("workout_id"),
					rs.getDate("workout_date").toLocalDate(),
					rs.getTime("workout_time").toLocalTime(),
					rs.getInt("duration_minutes"),
					rs.getString("type"),
					rs.getInt("calories_burned"),
					rs.getInt("rpe"),
					mg
				);
				list.add(w);
			}
		}
		return list;
	}
	
	
	public void update(Workout w) throws Exception {
		String sql = "UPDATE Workout SET workout_date=?, workout_time=?, duration_minutes=?, type=?, calories_burned=?, rpe=?, muscle_group_id=? WHERE workout_id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setDate(1, Date.valueOf(w.getDate()));
			ps.setTime(2, Time.valueOf(w.getTime()));
			ps.setInt(3, w.getDurationMinutes());
			ps.setString(4, w.getType());
			ps.setInt(5, w.getCaloriesBurned());
			ps.setInt(6, w.getRpe());
			ps.setInt(7, w.getMuscleGroup().getId());
			ps.setInt(8, w.getId());
			ps.executeUpdate();
		}
	}
	
	
	public void delete(int id) throws Exception {
		String sql = "DELETE FROM Workout WHERE workout_id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.executeUpdate();
		}
	}
}