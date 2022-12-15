DROP TABLE IF EXISTS vehicles CASCADE;
DROP TABLE IF EXISTS drivers CASCADE;
DROP TABLE IF EXISTS enterprises CASCADE;
DROP TABLE IF EXISTS vehicle_models CASCADE;


DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;


CREATE TABLE enterprises
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name            VARCHAR                           NOT NULL,
    city           VARCHAR                           NOT NULL
);
CREATE UNIQUE INDEX enterprises_unique_name_idx ON enterprises (name);


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
    enterprise_id       INTEGER            NOT NULL,
    FOREIGN KEY (model_id) REFERENCES vehicle_models (id) ON DELETE CASCADE,
    FOREIGN KEY (enterprise_id) REFERENCES enterprises (id) ON DELETE CASCADE
);

CREATE TABLE drivers
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    first_name            VARCHAR                           NOT NULL,
    second_name           VARCHAR                           NOT NULL,
    salary_usd         DECIMAL                           NOT NULL,
    experience       INTEGER            NOT NULL,
    enterprise_id       INTEGER            NOT NULL,
    vehicle_id       INTEGER            NOT NULL,
    is_active          BOOLEAN          NOT NULL    DEFAULT FALSE,
    FOREIGN KEY (enterprise_id) REFERENCES enterprises (id) ON DELETE CASCADE,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles (id) ON DELETE CASCADE
);


CREATE UNIQUE INDEX vehicle_unique_vin_idx ON vehicles (vin);


