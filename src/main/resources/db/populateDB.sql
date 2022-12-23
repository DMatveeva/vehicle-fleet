DELETE
FROM vehicles;
DELETE
FROM vehicle_models;
DELETE
FROM drivers;
DELETE
FROM enterprises;
DELETE
FROM managers;
DELETE
FROM users;
DELETE
FROM enterprises_managers;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO vehicle_models (brand, name, vehicle_type, num_seats, engine_type, load_capacity)
VALUES ('OPEL', 'Astra', 'CAR', 5, 'PETROL', 500),
       ('FORD', 'Focus 1', 'CAR', 5, 'PETROL', 400);

INSERT INTO enterprises (name, city)
VALUES ('It''s not the car but the driver', 'Los Angeles'),
       ('Drive anywhere', 'Las Vegas'),
       ('Best drivers ever', 'New York');

INSERT INTO vehicles (vin, model_id, cost_usd, color, mileage, production_year, enterprise_id)
VALUES ('4Y1SL65848Z411439', 100000, 10000.00, 'dark blue', 60000, 2008, 100002),
       ('8F0KL65848Z490765', 100001, 5000.00, 'white', 120000, 2003, 100003),
       ('90KKL65848Z490000', 100001, 9000.00, 'black', 1200, 2009, 100004);

INSERT INTO drivers (first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active)
VALUES ('Dominic', 'Toretto', 1000.00, 20, 100002, 100005, False),
       ('Letty', 'Ortiz', 2000.00, 10, 100003, 100006, False),
       ('Mia', 'Toretto', 3000.00, 5, 100004, 100007, False),
       ('Brian', 'O''Conner', 4000.00, 5, 100004, 100007, False);


INSERT INTO managers (login, password, first_name, second_name)
VALUES ('amy.lee@gmail.com', 'amy', 'Amy', 'Lee'),
       ('user.user@gmail.com', 'pass', 'User', 'User'),
       ('john.smith@gmail.com', 'j', 'John', 'Smith');

/*INSERT INTO users (login, password, first_name, second_name)
VALUES ('user.user@gmail.com', 'pass', 'User', 'User'),
       ('user1.user1@gmail.com', 'pass', 'User1', 'User1');*/

INSERT INTO enterprises_managers (enterprise_id, manager_id)
VALUES (100002, 100012),
       (100003, 100012),
       (100003, 100013),
       (100004, 100013);


INSERT INTO drivers (first_name, second_name, salary_usd, experience, enterprise_id, vehicle_id, is_active)
VALUES
       ('D1', 'D1', 1000.00, 20, 100002, null, False),
       ('D2', 'D2', 1000.00, 20, 100002, null, False),
       ('D3', 'D3', 1000.00, 20, 100002, null, False),
       ('D4', 'D4', 1000.00, 20, 100002, null, False)
       ;