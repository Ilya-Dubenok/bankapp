CREATE SCHEMA IF NOT EXISTS app;

ALTER DATABASE bank_app SET search_path TO app, public;

CREATE TABLE IF NOT EXISTS app.weekend
                          (
                              date date NOT NULL,
                              is_day_off boolean NOT NULL,
                              PRIMARY KEY (date)
                          );

CREATE TABLE IF NOT EXISTS app.currency_types
(
    id bigint NOT NULL UNIQUE,
    name text NOT NULL,
    PRIMARY KEY (id, name)
);


CREATE TABLE IF NOT EXISTS app.currency_rates
(
    id bigint NOT NULL,
    name text NOT NULL,
    date date NOT NULL,
    rate numeric NOT NULL,
    PRIMARY KEY (id, date),
    CONSTRAINT currency_rates_id_currency_types_id FOREIGN KEY (id)
        REFERENCES app.currency_types (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);