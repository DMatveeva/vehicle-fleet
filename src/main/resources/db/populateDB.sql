DELETE FROM vehicles;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO vehicles (vin, cost_usd, color, mileage, production_year)
VALUES ('4Y1SL65848Z411439', 10000.00, 'dark blue', 60000, 2008),
       ('8F0KL65848Z490765', 5000.00, 'white', 120000, 2003);
