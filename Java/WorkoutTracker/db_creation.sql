CREATE DATABASE IF NOT EXISTS workout_tracker;
USE workout_tracker;

CREATE TABLE MuscleGroup (
    muscle_group_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    days_per_week INT,
    sets INT,
    reps INT,
    weight DECIMAL(6,2)
);

CREATE TABLE Workout (
    workout_id INT AUTO_INCREMENT PRIMARY KEY,
    workout_date DATE NOT NULL,
    workout_time TIME NOT NULL,
    duration_minutes INT,
    type VARCHAR(255),
    calories_burned INT,
    rpe INT,
    muscle_group_id INT,
    CONSTRAINT fk_muscle_group
        FOREIGN KEY (muscle_group_id)
        REFERENCES MuscleGroup(muscle_group_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);
