--CREATE TABLE studenti1 (
--  id INT NOT NULL PRIMARY KEY,
--  nume VARCHAR2(15),
--  prenume VARCHAR2(30),
--  gen VARCHAR2(10),
--  nr_matricol VARCHAR2(20),
--  email VARCHAR2(40),
--  telefon VARCHAR(15),
--  facultate VARCHAR2(50),
--  medie NUMBER(4,2),
--  preferinta1 VARCHAR2(30),
--  preferinta2 VARCHAR2(30),
--  preferinta3 VARCHAR2(30),
--  preferinta4 VARCHAR2(30),
--  preferinta5 VARCHAR2(30),
--  camin_repartizat VARCHAR2(30)
--)

--CREATE TABLE camine (
--  id INT NOT NULL PRIMARY KEY,
--  nume VARCHAR2(15),
--  capacitate_per_camera INTEGER,
--  pret INTEGER,
--  nr_camere_fete INTEGER,
--  nr_camere_baieti INTEGER
--)


--CREATE TABLE facultate_camine (
--  id INT NOT NULL PRIMARY KEY,
--  nume_facultate VARCHAR2(50),
--  id_camin INTEGER,
--  locuri_fete INTEGER,
--  locuri_baieti INTEGER
--)


INSERT INTO camine (id, nume, capacitate_per_camera, pret, nr_camere_fete, nr_camere_baieti) VALUES (1, 'Akademos', 2, 650, 30, 20);
INSERT INTO camine (id, nume, capacitate_per_camera, pret, nr_camere_fete, nr_camere_baieti) VALUES (2, 'Gaudeamus', 2, 600, 35, 25);
INSERT INTO camine (id, nume, capacitate_per_camera, pret, nr_camere_fete, nr_camere_baieti) VALUES (3, 'C1', 3, 125, 30, 20);
INSERT INTO camine (id, nume, capacitate_per_camera, pret, nr_camere_fete, nr_camere_baieti) VALUES (4, 'C2', 3, 125, 30, 20);
INSERT INTO camine (id, nume, capacitate_per_camera, pret, nr_camere_fete, nr_camere_baieti) VALUES (5, 'C3', 3, 125, 30, 20);
INSERT INTO camine (id, nume, capacitate_per_camera, pret, nr_camere_fete, nr_camere_baieti) VALUES (6, 'C4', 3, 125, 30, 20);
INSERT INTO camine (id, nume, capacitate_per_camera, pret, nr_camere_fete, nr_camere_baieti) VALUES (7, 'C5', 3, 125, 30, 20);
INSERT INTO camine (id, nume, capacitate_per_camera, pret, nr_camere_fete, nr_camere_baieti) VALUES (8, 'C6', 3, 125, 30, 20);
INSERT INTO camine (id, nume, capacitate_per_camera, pret, nr_camere_fete, nr_camere_baieti) VALUES (9, 'C7', 3, 125, 30, 20);
INSERT INTO camine (id, nume, capacitate_per_camera, pret, nr_camere_fete, nr_camere_baieti) VALUES (10, 'C8', 3, 125, 30, 20);
INSERT INTO camine (id, nume, capacitate_per_camera, pret, nr_camere_fete, nr_camere_baieti) VALUES (11, 'C9', 3, 125, 30, 20);
INSERT INTO camine (id, nume, capacitate_per_camera, pret, nr_camere_fete, nr_camere_baieti) VALUES (12, 'C10', 3, 125, 30, 20);
INSERT INTO camine (id, nume, capacitate_per_camera, pret, nr_camere_fete, nr_camere_baieti) VALUES (13, 'C11', 3, 125, 30, 20);




set serveroutput on;
DECLARE
  nume_student VARCHAR2(15);
  prenume_student VARCHAR2(30);
  gen_student VARCHAR2(10);
  nr_matricol_student VARCHAR2(20);
  email_student VARCHAR2(40);
  telefon_student VARCHAR2(15);
  facultate_student VARCHAR2(50);
  medie_student NUMBER(4,2);
  preferinta1_student VARCHAR2(30);
  preferinta2_student VARCHAR2(30);
  preferinta3_student VARCHAR2(30);
  preferinta4_student VARCHAR2(30);
  preferinta5_student VARCHAR2(30);
BEGIN
  FOR i IN 1..10000 LOOP
    nume_student := 'Nume' || i;
    prenume_student := 'Prenume' || i;
    IF MOD(i, 2) = 0 THEN
      gen_student := 'baiat';
    ELSE
      gen_student := 'fata';
    END IF;
    nr_matricol_student := '1234567' || TO_CHAR(i, 'FM000');
    email_student := 'student' || i || '@example.com';
    telefon_student := '07' || TO_CHAR(100000 + i, 'FM000000');
    medie_student := TRUNC(DBMS_RANDOM.VALUE(5, 10) * 100) / 100;
    facultate_student := CASE MOD(ROUND(DBMS_RANDOM.VALUE(1, 15)), 15)
      WHEN 0 THEN 'Facultatea de Biologie'
      WHEN 1 THEN 'Facultatea de Chimie'
      WHEN 2 THEN 'Facultatea de Drept'
      WHEN 3 THEN 'Facultatea de Economie si Administrarea Afacerilor'
      WHEN 4 THEN 'Facultatea de Educatie Fizica si Sport'
      WHEN 5 THEN 'Facultatea de Filosofie si Stiinte Social-Politice'
      WHEN 6 THEN 'Facultatea de Fizica'
      WHEN 7 THEN 'Facultatea de Geografie si Geologie'
      WHEN 8 THEN 'Facultatea de Informatica'
      WHEN 9 THEN 'Facultatea de Istorie'
      WHEN 10 THEN 'Facultatea de Litere'
      WHEN 11 THEN 'Facultatea de Matematica'
      WHEN 12 THEN 'Facultatea de Psihologie si Stiinte ale Educatiei'
      WHEN 13 THEN 'Facultatea de Teologie Ortodoxa'
      WHEN 14 THEN 'Facultatea de Teologie Romano-Catolica'
    END;
    preferinta2_student := CASE MOD(i + 1, 13)
      WHEN 0 THEN 'Akademos'
      WHEN 1 THEN 'Gaudeamus'
      WHEN 2 THEN 'C1'
      WHEN 3 THEN 'C2'
      WHEN 4 THEN 'C3'
      WHEN 5 THEN 'C4'
      WHEN 6 THEN 'C5'
      WHEN 7 THEN 'C6'
      WHEN 8 THEN 'C7'
      WHEN 9 THEN 'C8'
      WHEN 10 THEN 'C9'
      WHEN 11 THEN 'C10'
      WHEN 12 THEN 'C11'
    END;
    preferinta1_student := CASE MOD(i, 13)
      WHEN 0 THEN 'Akademos'
      WHEN 1 THEN 'Gaudeamus'
      WHEN 2 THEN 'C1'
      WHEN 3 THEN 'C2'
      WHEN 4 THEN 'C3'
      WHEN 5 THEN 'C4'
      WHEN 6 THEN 'C5'
      WHEN 7 THEN 'C6'
      WHEN 8 THEN 'C7'
      WHEN 9 THEN 'C8'
      WHEN 10 THEN 'C9'
      WHEN 11 THEN 'C10'
      WHEN 12 THEN 'C11'
    END;
    preferinta2_student := CASE MOD(i + 1, 13)
      WHEN 0 THEN 'Akademos'
      WHEN 1 THEN 'Gaudeamus'
      WHEN 2 THEN 'C1'
      WHEN 3 THEN 'C2'
      WHEN 4 THEN 'C3'
      WHEN 5 THEN 'C4'
      WHEN 6 THEN 'C5'
      WHEN 7 THEN 'C6'
      WHEN 8 THEN 'C7'
      WHEN 9 THEN 'C8'
      WHEN 10 THEN 'C9'
      WHEN 11 THEN 'C10'
      WHEN 12 THEN 'C11'
    END;
    preferinta3_student := CASE MOD(i + 2, 13)
      WHEN 0 THEN 'Akademos'
      WHEN 1 THEN 'Gaudeamus'
      WHEN 2 THEN 'C1'
      WHEN 3 THEN 'C2'
      WHEN 4 THEN 'C3'
      WHEN 5 THEN 'C4'
      WHEN 6 THEN 'C5'
      WHEN 7 THEN 'C6'
      WHEN 8 THEN 'C7'
      WHEN 9 THEN 'C8'
      WHEN 10 THEN 'C9'
      WHEN 11 THEN 'C10'
      WHEN 12 THEN 'C11'
    END;
    preferinta4_student := CASE MOD(i + 3, 13)
      WHEN 0 THEN 'Akademos'
      WHEN 1 THEN 'Gaudeamus'
      WHEN 2 THEN 'C1'
      WHEN 3 THEN 'C2'
      WHEN 4 THEN 'C3'
      WHEN 5 THEN 'C4'
      WHEN 6 THEN 'C5'
      WHEN 7 THEN 'C6'
      WHEN 8 THEN 'C7'
      WHEN 9 THEN 'C8'
      WHEN 10 THEN 'C9'
      WHEN 11 THEN 'C10'
      WHEN 12 THEN 'C11'
    END;
    preferinta5_student := CASE MOD(i + 4, 13)
        WHEN 0 THEN 'Akadeos'
        WHEN 1 THEN 'Gaudeamus'
        WHEN 2 THEN 'C1'
        WHEN 3 THEN 'C2'
        WHEN 4 THEN 'C3'
        WHEN 5 THEN 'C4'
        WHEN 6 THEN 'C5'
        WHEN 7 THEN 'C6'
        WHEN 8 THEN 'C7'
        WHEN 9 THEN 'C8'
        WHEN 10 THEN 'C9'
        WHEN 11 THEN 'C10'
        WHEN 12 THEN 'C11'
    END;
    INSERT INTO studenti1 (id, nume, prenume, gen, nr_matricol, email, telefon, facultate, medie, preferinta1, preferinta2, preferinta3, preferinta4, preferinta5) VALUES (i, nume_student, prenume_student, gen_student, nr_matricol_student, email_student, telefon_student, facultate_student, medie_student, preferinta1_student, preferinta2_student, preferinta3_student, preferinta4_student, preferinta5_student);
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('Inserted rows.');
END;


set serveroutput on;
DECLARE
  nume_facultate1 VARCHAR2(40);
  id_camin1 INTEGER;
  locuri_baieti1 INTEGER;
  locuri_fete1 INTEGER;
  id1 INTEGER;
BEGIN
  id1 := 1;
  FOR i IN 1..13 LOOP
    INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Biologie', i, ROUND(DBMS_RANDOM.VALUE(0, 5)), ROUND(DBMS_RANDOM.VALUE(0, 5)));
    id1 := id1+1;
  END LOOP;
  FOR i IN 1..13 LOOP
    INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Chimie', i, ROUND(DBMS_RANDOM.VALUE(0, 5)), ROUND(DBMS_RANDOM.VALUE(0, 5)));
    id1 := id1+1;
  END LOOP;
  FOR i IN 1..13 LOOP
    INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Drept', i, ROUND(DBMS_RANDOM.VALUE(0, 5)), ROUND(DBMS_RANDOM.VALUE(0, 5)));
    id1 := id1+1;
  END LOOP;
  FOR i IN 1..13 LOOP
    INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Economie si Administrarea Afacerilor', i, ROUND(DBMS_RANDOM.VALUE(0, 5)), ROUND(DBMS_RANDOM.VALUE(0, 5)));
    id1 := id1+1;
  END LOOP;
  FOR i IN 1..13 LOOP
    INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Educatie Fizica si Sport', i, ROUND(DBMS_RANDOM.VALUE(0, 5)), ROUND(DBMS_RANDOM.VALUE(0, 5)));
    id1 := id1+1;
  END LOOP;
  FOR i IN 1..13 LOOP
    INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Filosofie si Stiinte Social-Politice', i, ROUND(DBMS_RANDOM.VALUE(0, 5)), ROUND(DBMS_RANDOM.VALUE(0, 5)));
    id1 := id1+1;
  END LOOP;
  FOR i IN 1..13 LOOP
    INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Fizica', i, ROUND(DBMS_RANDOM.VALUE(0, 5)), ROUND(DBMS_RANDOM.VALUE(0, 5)));
    id1 := id1+1;
  END LOOP;
  FOR i IN 1..13 LOOP
    INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Geografie si Geologie', i, ROUND(DBMS_RANDOM.VALUE(0, 5)), ROUND(DBMS_RANDOM.VALUE(0, 5)));
    id1 := id1+1;
  END LOOP;
  FOR i IN 1..13 LOOP
    INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Informatica', i, ROUND(DBMS_RANDOM.VALUE(0, 5)), ROUND(DBMS_RANDOM.VALUE(0, 5)));
    id1 := id1+1;
  END LOOP;
  FOR i IN 1..13 LOOP
    INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Istorie', i, ROUND(DBMS_RANDOM.VALUE(0, 5)), ROUND(DBMS_RANDOM.VALUE(0, 5)));
    id1 := id1+1;
  END LOOP;
  FOR i IN 1..13 LOOP
    INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Litere', i, ROUND(DBMS_RANDOM.VALUE(0, 5)), ROUND(DBMS_RANDOM.VALUE(0, 5)));
    id1 := id1+1;
  END LOOP;
  FOR i IN 1..13 LOOP
    INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Matematica', i, ROUND(DBMS_RANDOM.VALUE(0, 5)), ROUND(DBMS_RANDOM.VALUE(0, 5)));
    id1 := id1+1;
  END LOOP;
  FOR i IN 1..13 LOOP
    INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Psihologie si Stiinte ale Educatiei', i, ROUND(DBMS_RANDOM.VALUE(0, 5)), ROUND(DBMS_RANDOM.VALUE(0, 5)));
    id1 := id1+1;
  END LOOP;
  FOR i IN 1..13 LOOP
    INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Teologie Ortodoxa', i, ROUND(DBMS_RANDOM.VALUE(0, 5)), ROUND(DBMS_RANDOM.VALUE(0, 5)));
    id1 := id1+1;
  END LOOP;
  FOR i IN 1..13 LOOP
    INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Teologie Romano-Catolica', i, ROUND(DBMS_RANDOM.VALUE(0, 5)), ROUND(DBMS_RANDOM.VALUE(0, 5)));
    id1 := id1+1;
  END LOOP;
  DBMS_OUTPUT.PUT_LINE('Inserted rows.');
END;




--drop table studenti1;
--select * from studenti1;
--select * from camine; 
--select * from facultate_camine;
--delete from studenti1;
--delete from facultate_camine;
--drop table facultate_camine;
--select * from studenti1;
--select * from facultate_camine;
--SELECT * FROM studenti1 WHERE facultate = 'Facultatea de Matematica' ORDER BY medie DESC;
--SELECT DISTINCT nume_facultate FROM facultate_camine;
--SELECT * FROM studenti1 WHERE facultate = 'Facultatea de Informatica' ORDER BY medie DESC;