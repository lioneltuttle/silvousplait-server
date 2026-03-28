CREATE TABLE IF NOT EXISTS billing_interactions (
    id BIGSERIAL PRIMARY KEY,
    artisan_id BIGINT NOT NULL,
    demand_id BIGINT NOT NULL,
    amount_cents INTEGER NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

