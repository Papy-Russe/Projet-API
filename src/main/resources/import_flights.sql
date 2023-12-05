BEGIN;

INSERT INTO flights(id, number, origin, destination, departure_date, departure_time, arrival_date, arrival_time, plane_id) VALUES(NEXTVAL('flights_sequence_database'), 'ABC-1234', 'france', 'italy', '2023-11-30','14:45','2023-11-30','08:05',1);--Insertion d'1 vol dans la base de données de vols
--maintenant il faut juste que je trouve comment on accède à la base de données mais
--ça doit se faire à partir de l'url

COMMIT;