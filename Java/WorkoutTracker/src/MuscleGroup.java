public class MuscleGroup {
	private int id;
	private String name;
	private String description;
	private int daysPerWeek;
	private int sets;
	private int reps;
	private double weight;
	
	
	public MuscleGroup() {}
	
	
	public MuscleGroup(int id, String name, String description, int daysPerWeek, int sets, int reps, double weight) {
		this.setId(id);
		this.setName(name);
		this.setDescription(description);
		this.setDaysPerWeek(daysPerWeek);
		this.setSets(sets);
		this.setReps(reps);
		this.setWeight(weight);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getDaysPerWeek() {
		return daysPerWeek;
	}


	public void setDaysPerWeek(int daysPerWeek) {
		this.daysPerWeek = daysPerWeek;
	}


	public int getSets() {
		return sets;
	}


	public void setSets(int sets) {
		this.sets = sets;
	}


	public int getReps() {
		return reps;
	}


	public void setReps(int reps) {
		this.reps = reps;
	}


	public double getWeight() {
		return weight;
	}


	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	
	// Getters and Setters
}