--liquibase formatted sql
--changeset Qiraht:create_user_wallet_table
--comment: create user wallets table

CREATE TABLE IF NOT EXISTS user_wallets(
    id uuid NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id uuid NOT NULL REFERENCES users(id),
    balance DECIMAL(15,2) NOT NULL DEFAULT 0,
    status VARCHAR(255) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ(3) NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ(3) NOT NULL DEFAULT now()
);