DROP TABLE IF EXISTS vehicles;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE vehicles
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    vin            VARCHAR                           NOT NULL,
    color           VARCHAR                           NOT NULL,
    cost_usd         DECIMAL                           NOT NULL,
    mileage       INTEGER            NOT NULL,
    production_year          INTEGER                NOT NULL
);
CREATE UNIQUE INDEX vehicle_unique_vin_idx ON vehicles (vin);