CREATE TABLE celestial_object (
id BIGSERIAL PRIMARY KEY,

object_name VARCHAR(128) unique not null,
object_type VARCHAR(128) not null,
object_designation VARCHAR(128) unique,
discovery_year INTEGER,
distance_from_sun_au DOUBLE PRECISION,
object_speed_km_s DOUBLE PRECISION,
object_mass_to_earth DOUBLE PRECISION,
short_description TEXT not null,
image_main_url TEXT not null,
created_at TIMESTAMP DEFAULT now()
);