BEGIN;

INSERT INTO passenger(id, surname, firstname, emailaddress) VALUES(NEXTVAL('passenger_sequence_database'), 'DUPONT', 'Jean', 'jean.dupont@etu.unilasalle.fr');
INSERT INTO passenger(id, surname, firstname, emailaddress) VALUES(NEXTVAL('passenger_sequence_database'), 'VALLIER', 'Eddu', 'eddy.vallier@ext.unilasalle.fr');

COMMIT;