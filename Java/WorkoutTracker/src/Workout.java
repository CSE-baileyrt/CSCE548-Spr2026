import java.time.LocalDate;
import java.time.LocalTime;


public class Workout {
	private int id;
	private LocalDate date;
	private LocalTime time;
	private int durationMinutes;
	private String type;
	private int caloriesBurned;
	private int rpe;
	private MuscleGroup muscleGroup;


	public Workout() {}
	
	
	public Workout(int id, LocalDate date, LocalTime time, int durationMinutes, String type,
	int caloriesBurned, int rpe, MuscleGroup muscleGroup) {
		this.setId(id);
		this.setDate(date);
		this.setTime(time);
		this.setDurationMinutes(durationMinutes);
		this.setType(type);
		this.setCaloriesBurned(caloriesBurned);
		this.setRpe(rpe);
		this.setMuscleGroup(muscleGroup);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public LocalTime getTime() {
		return time;
	}


	public void setTime(LocalTime time) {
		this.time = time;
	}


	public int getDurationMinutes() {
		return durationMinutes;
	}


	public void setDurationMinutes(int durationMinutes) {
		this.durationMinutes = durationMinutes;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getCaloriesBurned() {
		return caloriesBurned;
	}


	public void setCaloriesBurned(int caloriesBurned) {
		this.caloriesBurned = caloriesBurned;
	}


	public int getRpe() {
		return rpe;
	}


	public void setRpe(int rpe) {
		this.rpe = rpe;
	}


	public MuscleGroup getMuscleGroup() {
		return muscleGroup;
	}


	public void setMuscleGroup(MuscleGroup muscleGroup) {
		this.muscleGroup = muscleGroup;
	}


// Getters and Setters
}