BEGIN TRANSACTION;
DROP TABLE IF EXISTS "znamenitosti";
CREATE TABLE IF NOT EXISTS "znamenitosti" (
	"id"	INTEGER,
	"naziv"	TEXT,
	"slika"	TEXT,
	"grad_id"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("grad_id") REFERENCES "grad"("id")
);
DROP TABLE IF EXISTS "grad";
CREATE TABLE IF NOT EXISTS "grad" (
	"id"	INTEGER,
	"naziv"	TEXT,
	"broj_stanovnika"	INTEGER,
	"drzava"	INTEGER,
	"postanski_broj"	INTEGER,
	FOREIGN KEY("drzava") REFERENCES "drzava",
	PRIMARY KEY("id")
);
DROP TABLE IF EXISTS "drzava";
CREATE TABLE IF NOT EXISTS "drzava" (
	"id"	INTEGER,
	"naziv"	TEXT,
	"glavni_grad"	INTEGER,
	PRIMARY KEY("id")
);
INSERT INTO "znamenitosti" VALUES (1,'Sarajevo','Sarajevo.jpg',6);
INSERT INTO "znamenitosti" VALUES (2,'Austrija','Berlin.jpg',3);
INSERT INTO "grad" VALUES (1,'Pariz',2206488,1,NULL);
INSERT INTO "grad" VALUES (2,'London',8825000,2,NULL);
INSERT INTO "grad" VALUES (3,'Beč',1899055,3,NULL);
INSERT INTO "grad" VALUES (4,'Manchester',545500,2,NULL);
INSERT INTO "grad" VALUES (5,'Graz',280200,3,NULL);
INSERT INTO "grad" VALUES (6,'Sarajevo',350000,3,NULL);
INSERT INTO "drzava" VALUES (1,'Francuska',1);
INSERT INTO "drzava" VALUES (2,'Velika Britanija',2);
INSERT INTO "drzava" VALUES (3,'Austrija',3);
INSERT INTO "drzava" VALUES (4,'Neka',2);
COMMIT;
