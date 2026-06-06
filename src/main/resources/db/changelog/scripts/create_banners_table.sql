--liquibase formatted sql
--changeset Qiraht:create_banners_table
--comment: create banners table

CREATE TABLE IF NOT EXISTS banners(
    id uuid NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    image TEXT NOT NULL,
    description TEXT,
    status VARCHAR(255) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ(3) NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ(3) NOT NULL DEFAULT now()
);