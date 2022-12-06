DROP TABLE IF EXISTS vehicles;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE vehicles
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    vin            VARCHAR                           NOT NULL,
    costUsd         DECIMAL                           NOT NULL,
    mileage       INTEGER            NOT NULL,
    productionYear          INTEGER                NOT NULL,
);
CREATE UNIQUE INDEX vehicle_unique_vin_idx ON vehicles (vin);