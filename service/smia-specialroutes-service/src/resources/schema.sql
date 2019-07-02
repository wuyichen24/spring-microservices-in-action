DROP TABLE IF EXISTS abtesting;

CREATE TABLE abtesting (
  service_name      VARCHAR(100)  NOT NULL  PRIMARY KEY,
  active            VARCHAR(1)    NOT NULL,
  endpoint          VARCHAR(100)  NOT NULL,
  weight            INTEGER);


INSERT INTO abtesting (service_name, active,  endpoint, weight) VALUES ('organizationservice', 'Y','http://orgservice-new:8087',5);
INSERT INTO abtesting (service_name, active,  endpoint, weight) VALUES ('licensingservice', 'Y','http://licservice-new:8087',5);
