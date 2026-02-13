CREATE TABLE IF NOT EXISTS workout_session (
    id BIGSERIAL PRIMARY KEY,
    exercise_type VARCHAR(255) NOT NULL,
    duration_in_minutes INTEGER NOT NULL,
    intensity INTEGER NOT NULL,
    date DATE NOT NULL
);