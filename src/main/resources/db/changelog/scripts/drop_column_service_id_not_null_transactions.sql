--liquibase formatted sql
--changeset Qiraht:table transaction drop column service_id NOT NULL
--comment: table transaction drop column service_id NOT NULL

ALTER TABLE transactions
    ALTER COLUMN service_id DROP NOT NULL;