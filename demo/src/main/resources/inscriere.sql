drop table studenti1;
/
drop table facultate_camine;
/
DROP TABLE camine;
/

CREATE TABLE studenti1 (
  id INT NOT NULL PRIMARY KEY,
  nume VARCHAR2(15),
  prenume VARCHAR2(30),
  gen VARCHAR2(10),
  nr_matricol VARCHAR2(20),
  email VARCHAR2(40),
  telefon VARCHAR(15),
  facultate VARCHAR2(50),
  medie NUMBER(4,2),
  preferinta1 VARCHAR2(30),
  preferinta2 VARCHAR2(30),
  preferinta3 VARCHAR2(30),
  preferinta4 VARCHAR2(30),
  preferinta5 VARCHAR2(30),
  camin_repartizat VARCHAR2(30)
)
/

CREATE OR REPLACE TYPE adresa AS OBJECT
(strada varchar2(30),
 nr varchar2(10)
);
/

CREATE TABLE camine (
  id INT NOT NULL PRIMARY KEY,
  nume VARCHAR2(20),
  capacitate_per_camera INTEGER,
  pret INTEGER, 
  adresa_camin adresa
)
/

CREATE TABLE facultate_camine (
  id INT NOT NULL PRIMARY KEY,
  nume_facultate VARCHAR2(50),
  id_camin INTEGER,
  locuri_fete INTEGER,
  locuri_baieti INTEGER,
  FOREIGN KEY (id_camin) REFERENCES camine(id)
)
/

CREATE OR REPLACE TRIGGER check_nr_matricol
BEFORE INSERT ON studenti1
FOR EACH ROW
DECLARE
  nr_matricol_count INTEGER;
BEGIN
  SELECT COUNT(*) INTO nr_matricol_count
  FROM studenti1
  WHERE nr_matricol = :NEW.nr_matricol;

  IF nr_matricol_count > 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'Numarul matricol exista deja in baza de date.');
  END IF;
END;
/

CREATE OR REPLACE TRIGGER delete_camine_trigger
BEFORE DELETE ON camine
FOR EACH ROW
BEGIN
  DELETE FROM facultate_camine WHERE id_camin = :OLD.id;
END;
/


--inseram camine
INSERT INTO camine (id, nume, capacitate_per_camera, pret, adresa_camin) VALUES (1, 'Akademos', 2, 650, adresa('Str. P?curari', 'nr. 6'));
INSERT INTO camine (id, nume, capacitate_per_camera, pret, adresa_camin) VALUES (2, 'Gaudeamus', 2, 572, adresa('Str. Codrescu', 'Nr.1'));
INSERT INTO camine (id, nume, capacitate_per_camera, pret, adresa_camin) VALUES (3, 'Buna Vestire', 2, 572, adresa('Str. Iordachi Lozonschi', 'nr. 9'));
INSERT INTO camine (id, nume, capacitate_per_camera, pret, adresa_camin) VALUES (4, 'C1', 3, 325, adresa('Str. Stoicescu', 'nr.1'));
INSERT INTO camine (id, nume, capacitate_per_camera, pret, adresa_camin) VALUES (5, 'C2', 3, 325, adresa('Str. Stoicescu', 'nr.2'));
INSERT INTO camine (id, nume, capacitate_per_camera, pret, adresa_camin) VALUES (6, 'C3', 3, 325, adresa('Str. Stoicescu', 'nr.3'));
INSERT INTO camine (id, nume, capacitate_per_camera, pret, adresa_camin) VALUES (7, 'C4', 4, 150, adresa('Str. Stoicescu', 'nr.4'));
INSERT INTO camine (id, nume, capacitate_per_camera, pret, adresa_camin) VALUES (8, 'C5', 3, 325, adresa('Str. Titu Maiorescu', 'nr.6'));
INSERT INTO camine (id, nume, capacitate_per_camera, pret, adresa_camin) VALUES (9, 'C6', 4, 150, adresa('Str. Titu Maiorescu', 'nr.7'));
INSERT INTO camine (id, nume, capacitate_per_camera, pret, adresa_camin) VALUES (10, 'C7', 4, 150, adresa('Str. Titu Maiorescu', 'nr.8'));
INSERT INTO camine (id, nume, capacitate_per_camera, pret, adresa_camin) VALUES (11, 'C8', 4, 150, adresa('Str. Titu Maiorescu', 'nr.9'));
INSERT INTO camine (id, nume, capacitate_per_camera, pret, adresa_camin) VALUES (12, 'C9', 3, 221, adresa('Str.Codrescu', 'nr.10'));
INSERT INTO camine (id, nume, capacitate_per_camera, pret, adresa_camin) VALUES (13, 'C10', 4, 150, adresa('Str.Codrescu', 'nr.10'));
INSERT INTO camine (id, nume, capacitate_per_camera, pret, adresa_camin) VALUES (14, 'C11', 3, 221, adresa('Str.Codrescu', 'nr.10'));
INSERT INTO camine (id, nume, capacitate_per_camera, pret, adresa_camin) VALUES (15, 'C12', 4, 150, adresa('Str.Codrescu', 'nr.10'));
/

--SELECT id, nume, capacitate_per_camera, pret, c.adresa_camin.strada, c.adresa_camin.nr FROM camine c;

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
  FOR i IN 1..15 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
        INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Biologie', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
        id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..15 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Chimie', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..15 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Drept', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..15 LOOP
  ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Economie si Administrarea Afacerilor', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..15 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Educatie Fizica si Sport', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..15 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Filosofie si Stiinte Social-Politice', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..15 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Fizica', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..15 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Geografie si Geologie', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..15 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Informatica', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..15 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Istorie', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..15 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Litere', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..15 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Matematica', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..15 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Psihologie si Stiinte ale Educatiei', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..15 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Teologie Ortodoxa', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  FOR i IN 1..15 LOOP
    ok := ROUND(DBMS_RANDOM.VALUE(0, 1));
    IF ok=1 THEN
      INSERT INTO facultate_camine (id, nume_facultate, id_camin, locuri_fete, locuri_baieti)VALUES (id1, 'Facultatea de Teologie Romano-Catolica', i, ROUND(DBMS_RANDOM.VALUE(1, 10)), ROUND(DBMS_RANDOM.VALUE(1, 10)));
      id1 := id1+1;
    END IF;
  END LOOP;
  DBMS_OUTPUT.PUT_LINE('Inserted rows.');
  EXCEPTION
  WHEN DUP_VAL_ON_INDEX THEN
    DBMS_OUTPUT.PUT_LINE('Error: Duplicate value!');
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/



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
  FOR i IN 1..2000 LOOP
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
    nr_matricol_student := 'RO1234567' || TO_CHAR(i, 'FM00000');
    email_student := 'student' || i || '@example.com';
    telefon_student := '07' || TO_CHAR(i, 'FM00000000');
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
    OPEN c(facultate_student);
  nr := 1;
    LOOP
      BEGIN
        v_camine_pe_facultate.extend;
        FETCH c INTO v_camine_pe_facultate(nr);
        EXIT WHEN c%NOTFOUND;
        nr := nr + 1;
        EXCEPTION WHEN NO_DATA_FOUND THEN
          EXIT; -- Exit the loop if no more data is found
      END;
  END LOOP;
  CLOSE c;
  nr := nr - 1;
  IF nr > 0 THEN 
    preferinta_aleasa := ROUND(DBMS_RANDOM.VALUE(1, nr));
    preferinta1_student := v_camine_pe_facultate(preferinta_aleasa);
    for j in preferinta_aleasa..nr-1 loop
      v_camine_pe_facultate(j) := v_camine_pe_facultate(j+1);
    end loop;
    v_camine_pe_facultate.delete(nr);
    nr := nr - 1;
  END IF;
  IF nr > 0 THEN 
    preferinta_aleasa := ROUND(DBMS_RANDOM.VALUE(1, nr));
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
/

CREATE OR REPLACE PROCEDURE export_student_data_facultate(nume_facultate IN VARCHAR2) AS
  v_fisier UTL_FILE.FILE_TYPE;
  v_id studenti1.id%TYPE;
  v_nume studenti1.nume%TYPE;
  v_prenume studenti1.prenume%TYPE;
  v_gen studenti1.gen%TYPE;
  v_nr_matricol studenti1.nr_matricol%TYPE;
  v_email studenti1.email%TYPE;
  v_telefon studenti1.telefon%TYPE;
  v_facultate studenti1.facultate%TYPE;
  v_medie studenti1.medie%TYPE;
  v_preferinta1 studenti1.preferinta1%TYPE;
  v_preferinta2 studenti1.preferinta2%TYPE;
  v_preferinta3 studenti1.preferinta3%TYPE;
  v_preferinta4 studenti1.preferinta4%TYPE;
  v_preferinta5 studenti1.preferinta5%TYPE;
  v_camin_repartizat studenti1.camin_repartizat%TYPE;
BEGIN
  v_fisier := UTL_FILE.FOPEN('MYDIR', TRIM(nume_facultate) || '.csv', 'W');

  -- Scrierea antetului în fi?ierul CSV
  UTL_FILE.PUTF(v_fisier, 'ID,Nume,Prenume,Gen,Nr Matricol,Email,Telefon,Facultate,Medie,Camin Repartizat');
  UTL_FILE.NEW_LINE(v_fisier);

  -- Iterarea prin înregistr?rile tabelului studenti1 ?i scrierea lor în fi?ierul CSV
  FOR rec IN (SELECT * FROM studenti1 WHERE camin_repartizat IS NOT NULL AND facultate LIKE nume_facultate ORDER BY medie DESC) LOOP
    v_id := rec.id;
    v_nume := rec.nume;
    v_prenume := rec.prenume;
    v_gen := rec.gen;
    v_nr_matricol := rec.nr_matricol;
    v_email := rec.email;
    v_telefon := rec.telefon;
    v_facultate := rec.facultate;
    v_medie := rec.medie;
    v_preferinta1 := rec.preferinta1;
    v_preferinta2 := rec.preferinta2;
    v_preferinta3 := rec.preferinta3;
    v_preferinta4 := rec.preferinta4;
    v_preferinta5 := rec.preferinta5;
    v_camin_repartizat := rec.camin_repartizat;

    UTL_FILE.PUTF(v_fisier, v_id || ',' || v_nume || ',' || v_prenume || ',' || v_gen || ',' || v_nr_matricol || ',' ||
                          v_email || ',' || ('+4' || v_telefon) || ',' || v_facultate || ',' || v_medie || ','  ||
                          v_camin_repartizat);
    UTL_FILE.NEW_LINE(v_fisier);
  END LOOP;

  UTL_FILE.FCLOSE(v_fisier);
END;
/

CREATE OR REPLACE PROCEDURE export_student_data_camin(nume_camin IN VARCHAR2) AS
  v_fisier UTL_FILE.FILE_TYPE;
  v_id studenti1.id%TYPE;
  v_nume studenti1.nume%TYPE;
  v_prenume studenti1.prenume%TYPE;
  v_gen studenti1.gen%TYPE;
  v_nr_matricol studenti1.nr_matricol%TYPE;
  v_email studenti1.email%TYPE;
  v_telefon studenti1.telefon%TYPE;
  v_facultate studenti1.facultate%TYPE;
  v_medie studenti1.medie%TYPE;
  v_preferinta1 studenti1.preferinta1%TYPE;
  v_preferinta2 studenti1.preferinta2%TYPE;
  v_preferinta3 studenti1.preferinta3%TYPE;
  v_preferinta4 studenti1.preferinta4%TYPE;
  v_preferinta5 studenti1.preferinta5%TYPE;
  v_camin_repartizat studenti1.camin_repartizat%TYPE;
BEGIN
  v_fisier := UTL_FILE.FOPEN('MYDIR', TRIM(nume_camin) || '.csv', 'W');

  -- Scrierea antetului în fi?ierul CSV
  UTL_FILE.PUTF(v_fisier, 'ID,Nume,Prenume,Gen,Nr Matricol,Email,Telefon,Facultate,Medie,Camin Repartizat');
  UTL_FILE.NEW_LINE(v_fisier);

  -- Iterarea prin înregistr?rile tabelului studenti1 ?i scrierea lor în fi?ierul CSV
  FOR rec IN (SELECT * FROM studenti1 WHERE camin_repartizat IS NOT NULL AND camin_repartizat LIKE nume_camin ORDER BY medie DESC) LOOP
    v_id := rec.id;
    v_nume := rec.nume;
    v_prenume := rec.prenume;
    v_gen := rec.gen;
    v_nr_matricol := rec.nr_matricol;
    v_email := rec.email;
    v_telefon := rec.telefon;
    v_facultate := rec.facultate;
    v_medie := rec.medie;
    v_preferinta1 := rec.preferinta1;
    v_preferinta2 := rec.preferinta2;
    v_preferinta3 := rec.preferinta3;
    v_preferinta4 := rec.preferinta4;
    v_preferinta5 := rec.preferinta5;
    v_camin_repartizat := rec.camin_repartizat;

    UTL_FILE.PUTF(v_fisier, v_id || ',' || v_nume || ',' || v_prenume || ',' || v_gen || ',' || v_nr_matricol || ',' ||
                          v_email || ',' || ('+4' || v_telefon) || ',' || v_facultate || ',' || v_medie || ','  ||
                          v_camin_repartizat);
    UTL_FILE.NEW_LINE(v_fisier);
  END LOOP;

  UTL_FILE.FCLOSE(v_fisier);
END;
/



COMMIT;
/

--verificare
--SELECT * FROM studenti1 ;
--SELECT * FROM facultate_camine;
