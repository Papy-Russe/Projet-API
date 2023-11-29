BEGIN;

INSERT INTO flights(id, number, origin, destination, departure_date, departure_time, arrival_date, arrival_time, plane_id) VALUES(NEXTVAL('flights_sequence'), 'ABC-1234', 'FRANCE', 'ITALY', '29 November 2023','14:45','30 November 2023','08:05',10);--Insertion d'1 vol dans la base de données de vols
--maintenant il faut juste que je trouve comment on accède à la base de données mais
--ça doit se faire à partir de l'url

COMMIT;