BEGIN;

INSERT INTO planes(id, operator, model, immatriculation, capacity) VALUES(NEXTVAL('planes_sequence'), 'AirbusIndustrie', 'AIRBUS A380', 'F-ABCD', 10);
INSERT INTO planes(id, operator, model, immatriculation, capacity) VALUES(NEXTVAL('planes_sequence'), 'Boeing', 'BOEING CV2', 'F-AZER', 15);

COMMIT;
