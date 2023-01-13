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
DELETE
FROM tracks;
DELETE
FROM vehicle_coordinates;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO vehicle_models (brand, name, vehicle_type, num_seats, engine_type, load_capacity)
VALUES ('OPEL', 'Astra', 'CAR', 5, 'PETROL', 500),
       ('FORD', 'Focus 1', 'CAR', 5, 'PETROL', 400);

INSERT INTO enterprises (name, city, time_zone)
VALUES ('It''s not the car but the driver', 'Los Angeles', 'America/Los_Angeles'),
       ('Drive anywhere', 'Las Vegas', 'America/Chicago'),
       ('Best drivers ever', 'Paris', 'Europe/Paris');

INSERT INTO vehicles (vin, model_id, cost_usd, color, mileage, production_year, enterprise_id, purchase_date)
VALUES ('4Y1SL65848Z411439', 100000, 10000.00, 'dark blue', 60000, 2008, 100002,'2020-01-01 20:00:00'),
       ('8F0KL65848Z490765', 100001, 5000.00, 'white', 120000, 2003, 100003, '2011-01-01 09:00:00'),
       ('90KKL65848Z490000', 100001, 9000.00, 'black', 1200, 2009, 100004, '2008-01-01 10:00:00');

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

INSERT INTO tracks(vehicle_id, started, finished)
values (100005, '2008-01-01 18:00', '2008-01-01 22:00'),
       (100006, '2009-01-01 17:00', '2009-01-01 23:00');


INSERT INTO vehicle_coordinates(id, track_id, vehicle_id, lat, lon, position, visited)
values (100021, 100019, 100005, 55.7538337, 37.6211812, st_setsrid(st_makepoint(37.6211812, 55.7538337), 4326),  '2008-01-01 17:00');
INSERT INTO vehicle_coordinates(id, track_id, vehicle_id, lat, lon, position, visited)
values (100022, 100019, 100005, 56.7538337, 38.6211812, st_setsrid(st_makepoint(38.6211812, 56.7538337), 4326),  '2008-01-01 19:00');
INSERT INTO vehicle_coordinates(id, track_id, vehicle_id, lat, lon, position, visited)
values (100023, 100019, 100005, 57.7538337, 39.6211812, st_setsrid(st_makepoint(39.6211812, 57.7538337), 4326),  '2008-01-01 21:00');
INSERT INTO vehicle_coordinates(id, track_id, vehicle_id, lat, lon, position, visited)
values (100024, 100019, 100005, 58.7538337, 40.6211812, st_setsrid(st_makepoint(40.6211812, 58.7538337), 4326),  '2008-01-01 23:00');
