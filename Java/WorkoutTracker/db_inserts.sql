use workout_tracker;


INSERT INTO MuscleGroup (name, description, days_per_week, sets, reps, weight) VALUES
('Chest', 'Chest and push muscles', 2, 4, 10, 185),
('Back', 'Back and pull muscles', 2, 4, 8, 200),
('Legs', 'Lower body strength', 2, 5, 8, 225),
('Arms', 'Biceps and triceps', 2, 3, 12, 95);


INSERT INTO Workout (workout_date, workout_time, duration_minutes, type, calories_burned, rpe, muscle_group_id) VALUES
('2026-01-20', '18:00:00', 60, 'Strength Training', 500, 8, 1),
('2026-01-21', '07:30:00', 45, 'Back Day Workout', 420, 7, 2),
('2026-01-22', '17:45:00', 70, 'Leg Day', 650, 9, 3),
('2026-01-23', '19:00:00', 40, 'Arm Workout', 350, 6, 4);