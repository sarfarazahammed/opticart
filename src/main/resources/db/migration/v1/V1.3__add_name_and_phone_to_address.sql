ALTER TABLE address
    ADD COLUMN name                 VARCHAR(255) NOT NULL,
    ADD COLUMN country_calling_code VARCHAR(8)   NOT NULL,
    ADD COLUMN phone_number         VARCHAR(16)  NOT NULL;
