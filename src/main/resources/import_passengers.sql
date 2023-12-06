BEGIN;

INSERT INTO passengers(id, surname, firstname, emailaddress) VALUES(NEXTVAL('passengers_sequence_database'), 'DUPONT', 'Jean', 'jean.dupont@etu.unilasalle.fr');
INSERT INTO passengers(id, surname, firstname, emailaddress) VALUES(NEXTVAL('passengers_sequence_database'), 'VALLIER', 'Eddy', 'eddy.vallier@ext.unilasalle.fr');

COMMIT;