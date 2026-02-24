

CREATE TABLE IF NOT EXISTS studyplan (
    id BIGSERIAL PRIMARY KEY,
    task VARCHAR(255) NOT NULL,
    priority INTEGER NOT NULL CHECK, (priority >= 0 AND priority <= 100)
    coursename VARCHAR(255) NOT NULL,
    startdate DATE NOT NULL,
    deadline DATE NOT NULL

);