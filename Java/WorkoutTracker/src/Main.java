import java.time.*;
import java.util.*;


public class Main {
	public static void main(String[] args) {
		try {
			MuscleGroupDao muscleGroupDAO = new MuscleGroupDao();
			WorkoutDao workoutDAO = new WorkoutDao();
			
			
			System.out.println("=== INSERT TESTS ===");
			MuscleGroup shoulders = new MuscleGroup(0, "Shoulders", "Deltoid training", 2, 4, 10, 135);
			muscleGroupDAO.insert(shoulders);
			System.out.println("Inserted MuscleGroup: Shoulders");
			
			
			List<MuscleGroup> muscleGroups = muscleGroupDAO.getAll();
			MuscleGroup mgRef = muscleGroups.get(muscleGroups.size() - 1);
			
			
			Workout workout = new Workout(0,
				LocalDate.now(),
				LocalTime.now().withNano(0),
				50,
				"Shoulder Strength Session",
				480,
				8,
				mgRef);
			workoutDAO.insert(workout);
			System.out.println("Inserted Workout");
			
			
			System.out.println("=== READ TESTS ===");
			for (MuscleGroup mg : muscleGroupDAO.getAll()) {
				System.out.println(mg.getId() + " - " + mg.getName());
			}
			
			
			for (Workout w : workoutDAO.getAll()) {
				System.out.println(w.getId() + " - " + w.getType() + " (" + w.getMuscleGroup().getName() + ")");
			}
			
			
			System.out.println("=== UPDATE TESTS ===");
			mgRef.setWeight(155);
			mgRef.setSets(5);
			muscleGroupDAO.update(mgRef);
			System.out.println("Updated MuscleGroup: " + mgRef.getName());
			
			
			List<Workout> workouts = workoutDAO.getAll();
			Workout wRef = workouts.get(workouts.size() - 1);
			wRef.setCaloriesBurned(550);
			wRef.setDurationMinutes(60);
			workoutDAO.update(wRef);
			System.out.println("Updated Workout ID: " + wRef.getId());
			
			
			System.out.println("=== DELETE TESTS ===");
			workoutDAO.delete(wRef.getId());
			System.out.println("Deleted Workout ID: " + wRef.getId());
			
			
			muscleGroupDAO.delete(mgRef.getId());
			System.out.println("Deleted MuscleGroup ID: " + mgRef.getId());
			
			
			System.out.println("=== FINAL DB STATE ===");
			System.out.println("MuscleGroups: " + muscleGroupDAO.getAll().size());
			System.out.println("Workouts: " + workoutDAO.getAll().size());
			
			
			System.out.println("DATA LAYER TEST COMPLETED SUCCESSFULLY");
			
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}