CREATE TABLE celestial_object (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    object_type VARCHAR(40) NOT NULL,
    designation VARCHAR(120),
    short_description TEXT,
    discovered_year INT,
    updated_at TIMESTAMPTZ DEFAULT NOW()
);