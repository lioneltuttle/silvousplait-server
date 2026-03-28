CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE IF NOT EXISTS professionals (
    id BIGSERIAL PRIMARY KEY,
    display_name VARCHAR(200) NOT NULL,
    service_type VARCHAR(140) NOT NULL,
    available BOOLEAN NOT NULL DEFAULT false,
    location GEOGRAPHY(POINT, 4326) NOT NULL,
    rating DOUBLE PRECISION NOT NULL DEFAULT 4.5,
    response_rate DOUBLE PRECISION NOT NULL DEFAULT 0.9
);

CREATE INDEX IF NOT EXISTS idx_professionals_location ON professionals USING GIST (location);
CREATE INDEX IF NOT EXISTS idx_professionals_service ON professionals (service_type);
