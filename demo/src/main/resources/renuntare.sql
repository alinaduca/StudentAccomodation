
--Renunta cativa studenti la camine
CREATE OR REPLACE PROCEDURE modificare_camin_repartizat
IS
  TYPE studenti_tab_type IS TABLE OF Studenti1.id%TYPE;
  studenti_tab studenti_tab_type := studenti_tab_type();
  ok INTEGER;
  id_student Studenti1.id%TYPE;
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
      BEGIN
          SELECT camin_repartizat INTO camin_student FROM studenti1 WHERE id = studenti_tab(i);
          SELECT gen, facultate, id INTO gen_student, facultate_student, id_student FROM studenti1 WHERE id = studenti_tab(i);
          DELETE FROM studenti1 WHERE id = id_student;
          SELECT id INTO id_camin_student FROM camine WHERE nume = camin_student;
          IF gen_student LIKE 'fata' THEN
            UPDATE facultate_camine SET locuri_fete = locuri_fete + 1 WHERE nume_facultate = facultate_student AND id_camin = id_camin_student;
            DBMS_OUTPUT.PUT_LINE('Fata cu id-ul ' || studenti_tab(i) || ' a renuntat la camin');
          ELSIF gen_student LIKE 'baiat' THEN
            UPDATE facultate_camine SET locuri_baieti = locuri_baieti + 1 WHERE nume_facultate = facultate_student AND id_camin = id_camin_student;
            DBMS_OUTPUT.PUT_LINE('Baiatul cu id-ul ' || studenti_tab(i) || ' a renuntat la camin');
          END IF;
          EXCEPTION
        WHEN NO_DATA_FOUND THEN
          DBMS_OUTPUT.PUT_LINE('Nu s-a gasit inregistrare pentru studentul cu id-ul ' || studenti_tab(i));
        WHEN TOO_MANY_ROWS THEN
          DBMS_OUTPUT.PUT_LINE('S-au gasit prea multe inregistrari pentru studentul cu id-ul ' || studenti_tab(i));
        WHEN OTHERS THEN
          DBMS_OUTPUT.PUT_LINE('A aparut o eroare pentru studentul cu id-ul ' || studenti_tab(i));
      END;
    END IF;
  END LOOP;
END;
/
SET SERVEROUTPUT ON;
BEGIN
  modificare_camin_repartizat();
END;
/

commit;
/


--verificare
--SELECT * FROM facultate_camine;
--SELECT * FROM studenti1 where camin_repartizat IS NOT NULL;


