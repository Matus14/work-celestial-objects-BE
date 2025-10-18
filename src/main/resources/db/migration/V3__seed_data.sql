INSERT INTO celestial_object (
    object_name,
    object_type,
    object_designation,
    discovery_year,
    distance_from_sun_au,
    object_speed_km_s,
    object_mass_to_earth,
    short_description,
    image_main_url,
    created_at,
    updated_at
)
VALUES
('Mercury', 'Planet', 'MERC01', -1000, 0.39, 47.87, 0.055, 'Smallest planet in the Solar System.',
'https://upload.wikimedia.org/wikipedia/commons/4/4a/Mercury_in_true_color.jpg', now(), now()),

('Venus', 'Planet', 'VENU02', -500, 0.72, 35.02, 0.815, 'Extremely hot atmosphere.',
'https://upload.wikimedia.org/wikipedia/commons/e/e5/Venus-real_color.jpg', now(), now()),

('Earth', 'Planet', 'EART03', 0, 1.00, 29.78, 1.00, 'Our home planet.',
'https://upload.wikimedia.org/wikipedia/commons/9/97/The_Earth_seen_from_Apollo_17.jpg', now(), now()),

('Mars', 'Planet', 'MARS04', 1659, 1.52, 24.07, 0.107, 'The Red Planet.',
'https://upload.wikimedia.org/wikipedia/commons/0/02/OSIRIS_Mars_true_color.jpg', now(), now()),

('Jupiter', 'Planet', 'JUPI05', 1610, 5.20, 13.07, 317.8, 'Gas giant with Great Red Spot.',
'https://upload.wikimedia.org/wikipedia/commons/e/e2/Jupiter.jpg', now(), now()),

('Saturn', 'Planet', 'SATU06', 1610, 9.58, 9.69, 95.16, 'Planet famous for its rings.',
'https://upload.wikimedia.org/wikipedia/commons/c/c7/Saturn_during_Equinox.jpg', now(), now()),

('Halley''s Comet', 'Comet', 'HALC07', 1758, 35.08, 54.6, 0.0, 'Short-period comet visible every 75 years.',
'https://upload.wikimedia.org/wikipedia/commons/9/9d/Halley%27s_Comet_-_1986.jpg', now(), now());