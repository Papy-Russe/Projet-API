BEGIN;

INSERT INTO reservations(id, flight_id, passenger_id) VALUES(NEXTVAL('reservations_sequence_database'), 1, 1);
INSERT INTO reservations(id, flight_id, passenger_id) VALUES(NEXTVAL('reservations_sequence_database'), 1, 2);

COMMIT;