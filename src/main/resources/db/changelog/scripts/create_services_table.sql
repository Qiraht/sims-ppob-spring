--liquibase formatted sql
--changeset Qiraht:create_services_table
--comment: create services table

CREATE TABLE IF NOT EXISTS services(
    id uuid NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    icon TEXT,
    tariff DECIMAL(15,2) NOT NULL DEFAULT 0,
    status VARCHAR(255) NOT NULL DEFAULT 'ACTIVE',
    is_deleted BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMPTZ(3) NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ(3) NOT NULL DEFAULT NOW()
);