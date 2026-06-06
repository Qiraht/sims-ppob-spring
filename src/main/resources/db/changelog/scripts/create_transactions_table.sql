--liquibase formatted sql
--changeset Qiraht:create_transactions_table
--comment: create transactions table

CREATE TABLE IF NOT EXISTS transactions(
    id uuid NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    invoice_number VARCHAR(255) NOT NULL,
    user_id uuid NOT NULL REFERENCES users(id),
    service_id uuid NOT NULL REFERENCES services(id),
    transaction_type VARCHAR(255) NOT NULL,
    description TEXT,
    total_amount DECIMAL(15,2) NOT NULL,
    created_at TIMESTAMPTZ(3) NOT NULL DEFAULT now()
);