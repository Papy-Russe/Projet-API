BEGIN;

INSERT INTO reservation(id, flight_id, passenger_id) VALUES(NEXTVAL('passenger_sequence_database'), 1, 1);

COMMIT;