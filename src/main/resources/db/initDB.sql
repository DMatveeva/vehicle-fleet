DROP TABLE IF EXISTS vehicles;
DROP TABLE IF EXISTS vehicle_models;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;


CREATE TABLE vehicle_models
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    brand       VARCHAR NOT NULL,
    name       VARCHAR NOT NULL,
    vehicle_type       VARCHAR NOT NULL,
    num_seats     INTEGER   NOT NULL,
    engine_type       VARCHAR NOT NULL,
    load_capacity    INT       NOT NULL
);

CREATE TABLE vehicles
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    model_id        INTEGER   NOT NULL,
    vin            VARCHAR                           NOT NULL,
    color           VARCHAR                           NOT NULL,
    cost_usd         DECIMAL                           NOT NULL,
    mileage       INTEGER            NOT NULL,
    production_year          INTEGER                NOT NULL,
    FOREIGN KEY (model_id) REFERENCES vehicle_models (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX vehicle_unique_vin_idx ON vehicles (vin);

CREATE UNIQUE INDEX vehicles_unique_model_idx ON vehicles (model_id);
