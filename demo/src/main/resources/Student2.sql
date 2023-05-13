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
--  camin_repartizat VARCHAR2(30),
--  reinscris_la_camin INTEGER
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


--inseram camine
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



--inseram camine pentru fiecare facultate
set serveroutput on;
DECLARE
  nume_facultate1 VARCHAR2(40);
  id_camin1 INTEGER;
  locuri_baieti1 INTEGER;
  locuri_fete1 INTEGER;
  id1 INTEGER;
  ok INTEGER;
BEGIN
  id1 := 1;
  FOR i IN 1..13 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
        INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Biologie', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
        id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..13 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Chimie', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..13 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Drept', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..13 LOOP
  ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Economie si Administrarea Afacerilor', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..13 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Educatie Fizica si Sport', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..13 LOOP
    INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Filosofie si Stiinte Social-Politice', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
    id1 := id1+1;
  END LOOP;
  FOR i IN 1..13 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Fizica', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..13 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Geografie si Geologie', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..13 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Informatica', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..13 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Istorie', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..13 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Litere', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..13 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Matematica', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..13 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Psihologie si Stiinte ale Educatiei', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..13 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Teologie Ortodoxa', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..13 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Teologie Romano-Catolica', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  DBMS_OUTPUT.PUT_LINE('Inserted rows.');
END;





--inseram studenti
set serveroutput on;
DECLARE
  TYPE camine_pe_facultate IS TABLE OF VARCHAR2(15);
  v_camine_pe_facultate camine_pe_facultate := camine_pe_facultate();
  CURSOR c (v_facultate_nume facultate_camine.nume_facultate%TYPE) IS SELECT nume FROM camine c JOIN facultate_camine fc on c.id=fc.id_camin where nume_facultate=v_facultate_nume;
  nr INTEGER;
  nume_student VARCHAR2(15);
  prenume_student VARCHAR2(30);
  gen_student VARCHAR2(10);
  nr_matricol_student VARCHAR2(20);
  email_student VARCHAR2(40);
  telefon_student VARCHAR2(15);
  facultate_student VARCHAR2(50);
  medie_student NUMBER(4,2);
  preferinta1_student VARCHAR2(15);
  preferinta2_student VARCHAR2(15);
  preferinta3_student VARCHAR2(15);
  preferinta4_student VARCHAR2(15);
  preferinta5_student VARCHAR2(15);
  preferinta_aleasa INTEGER;
BEGIN
  FOR i IN 1..10000 LOOP
    preferinta1_student := null;
    preferinta2_student := null;
    preferinta3_student := null;
    preferinta4_student := null;
    preferinta5_student := null;
    nume_student := 'Nume' || i;
    prenume_student := 'Prenume' || i;
    IF MOD(i, 2) = 0 THEN
      gen_student := 'baiat';
    ELSE
      gen_student := 'fata';
    END IF;
    nr_matricol_student := '1234567' || TO_CHAR(i, 'FM00000');
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
    DBMS_OUTPUT.PUT_LINE(facultate_student);
    OPEN c(facultate_student);
  nr := 1;
    LOOP
        v_camine_pe_facultate.extend;
        FETCH c INTO v_camine_pe_facultate(nr);
        EXIT WHEN c%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(v_camine_pe_facultate(nr));
        nr := nr + 1;
  END LOOP;
  CLOSE c;
  nr := nr - 1;
  DBMS_OUTPUT.PUT_LINE(nr);
  IF nr > 0 THEN 
    preferinta_aleasa := ROUND(DBMS_RANDOM.VALUE(1, nr));
    --DBMS_OUTPUT.PUT_LINE(preferinta_aleasa);
    preferinta1_student := v_camine_pe_facultate(preferinta_aleasa);
    for j in preferinta_aleasa..nr-1 loop
      v_camine_pe_facultate(j) := v_camine_pe_facultate(j+1);
    end loop;
    v_camine_pe_facultate.delete(nr);
    nr := nr - 1;
  END IF;
  DBMS_OUTPUT.PUT_LINE(nr);
  IF nr > 0 THEN 
    preferinta_aleasa := ROUND(DBMS_RANDOM.VALUE(1, nr));
    --DBMS_OUTPUT.PUT_LINE(preferinta_aleasa);
    preferinta2_student := v_camine_pe_facultate(preferinta_aleasa);
    v_camine_pe_facultate.DELETE(preferinta_aleasa);
    for j in preferinta_aleasa..nr-1 loop
      v_camine_pe_facultate(j) := v_camine_pe_facultate(j+1);
    end loop;
    v_camine_pe_facultate.delete(nr);
    nr := nr - 1;
  END IF;
  IF nr > 0 THEN 
    preferinta_aleasa := ROUND(DBMS_RANDOM.VALUE(1, nr));
    preferinta3_student := v_camine_pe_facultate(preferinta_aleasa);
    v_camine_pe_facultate.DELETE(preferinta_aleasa);
    for j in preferinta_aleasa..nr-1 loop
      v_camine_pe_facultate(j) := v_camine_pe_facultate(j+1);
    end loop;
    v_camine_pe_facultate.delete(nr);
    nr := nr - 1;
  END IF;
  IF nr > 0 THEN 
    preferinta_aleasa := ROUND(DBMS_RANDOM.VALUE(1, nr));
    preferinta4_student := v_camine_pe_facultate(preferinta_aleasa);
    v_camine_pe_facultate.DELETE(preferinta_aleasa);
    for j in preferinta_aleasa..nr-1 loop
      v_camine_pe_facultate(j) := v_camine_pe_facultate(j+1);
    end loop;
    v_camine_pe_facultate.delete(nr);
    nr := nr - 1;
  END IF;
  IF nr > 0 THEN 
    preferinta_aleasa := ROUND(DBMS_RANDOM.VALUE(1, nr));
    preferinta5_student := v_camine_pe_facultate(preferinta_aleasa);
    v_camine_pe_facultate.DELETE(preferinta_aleasa);
    for j in preferinta_aleasa..nr-1 loop
      v_camine_pe_facultate(j) := v_camine_pe_facultate(j+1);
    end loop;
    v_camine_pe_facultate.delete(nr);
    nr := nr - 1;
  END IF;
    INSERT INTO studenti1 (id, nume, prenume, gen, nr_matricol, email, telefon, facultate, medie, preferinta1, preferinta2, preferinta3, preferinta4, preferinta5) VALUES (i, nume_student, prenume_student, gen_student, nr_matricol_student, email_student, telefon_student, facultate_student, medie_student, preferinta1_student, preferinta2_student, preferinta3_student, preferinta4_student, preferinta5_student);
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('Inserted rows.');
END;


--Renunta cativa studenti la camine
CREATE OR REPLACE PROCEDURE modificare_camin_repartizat
IS
  TYPE studenti_tab_type IS TABLE OF Studenti1.id%TYPE;
  studenti_tab studenti_tab_type := studenti_tab_type();
  ok INTEGER;
  gen_student studenti1.gen%type;
  facultate_student studenti1.facultate%type;
  camin_student studenti1.camin_repartizat%type;
  id_camin_student camine.id%type;
BEGIN
  -- Selectam studentii care au fost repartizati la un camin
  SELECT id BULK COLLECT INTO studenti_tab FROM studenti1 WHERE camin_repartizat IS NOT NULL;

  -- Pentru fiecare student, gener?m un numar random între 0 ?i 1
  FOR i IN 1..studenti_tab.COUNT LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    -- Daca ok este 1, actualizam camin_repartizat cu null pentru studentul respectiv
    IF ok = 1 THEN
      SELECT camin_repartizat INTO camin_student FROM studenti1 WHERE id = studenti_tab(i);
      UPDATE studenti1 SET camin_repartizat = null WHERE id = studenti_tab(i);
      SELECT gen, facultate INTO gen_student, facultate_student FROM studenti1 WHERE id = studenti_tab(i);
      SELECT id INTO id_camin_student FROM camine WHERE nume = camin_student;
      IF gen_student LIKE 'fata' THEN
        UPDATE facultate_camine SET locuri_fete = locuri_fete + 1 WHERE nume_facultate = facultate_student AND id_camin = id_camin_student;
        DBMS_OUTPUT.PUT_LINE('Fata cu id-ul ' || studenti_tab(i) || ' a renuntat la camin');
      ELSIF gen_student LIKE 'baiat' THEN
        UPDATE facultate_camine SET locuri_baieti = locuri_baieti + 1 WHERE nume_facultate = facultate_student AND id_camin = id_camin_student;
        DBMS_OUTPUT.PUT_LINE('Baiatul cu id-ul ' || studenti_tab(i) || ' a renuntat la camin');
      END IF;
    END IF;
  END LOOP;
END;
/
SET SERVEROUTPUT ON;
BEGIN
  modificare_camin_repartizat();
END;

--verificare
SELECT * FROM facultate_camine;
SELECT * FROM studenti1 where camin_repartizat IS NOT NULL;


--reinscrieri la camine








commit;
--select * from facultate_camine where nume_facultate='Facultatea de Chimie';
--select * from studenti1 where camin_repartizat is not null;
delete from facultate_camine;
delete from studenti1;
