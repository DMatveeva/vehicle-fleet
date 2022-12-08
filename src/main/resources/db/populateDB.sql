DELETE FROM vehicles;
DELETE FROM vehicle_models;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO vehicle_models (brand, name, vehicle_type, num_seats, engine_type, load_capacity)
VALUES ('OPEL', 'Astra', 'CAR', 5, 'PETROL', 500),
       ('FORD', 'Focus 1', 'CAR', 5, 'PETROL', 400);

INSERT INTO vehicles (vin, model_id, cost_usd, color, mileage, production_year)
VALUES ('4Y1SL65848Z411439', 100000, 10000.00, 'dark blue', 60000, 2008),
       ('8F0KL65848Z490765', 100001, 5000.00, 'white', 120000, 2003);
