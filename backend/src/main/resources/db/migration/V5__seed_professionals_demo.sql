-- Données de démo pour le matching Sprint 1 (zone Paris, même jeu que les tests API).
INSERT INTO professionals (display_name, service_type, available, location, rating, response_rate)
VALUES
    (
        'Artisan Dupont',
        'Plombier',
        true,
        ST_SetSRID(ST_MakePoint(2.3522, 48.8566), 4326)::geography,
        4.8,
        0.95
    ),
    (
        'Artisan Martin',
        'Plombier - fuite chauffe-eau',
        true,
        ST_SetSRID(ST_MakePoint(2.3600, 48.8600), 4326)::geography,
        4.2,
        0.85
    );
