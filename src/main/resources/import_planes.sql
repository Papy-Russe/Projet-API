BEGIN;

INSERT INTO planes(id, operator, model, immatriculation, capacity) VALUES(NEXTVAL('planes_sequence_database'), 'AirbusIndustrie', 'AIRBUS A380', 'F-ABCD', 10);
INSERT INTO planes(id, operator, model, immatriculation, capacity) VALUES(NEXTVAL('planes_sequence_database'), 'Boeing', 'BOEING CV2', 'F-AZER', 15);

COMMIT;
