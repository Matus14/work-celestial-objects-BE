alter table celestial_object
add column updated_at TIMESTAMP not null DEFAULT now();