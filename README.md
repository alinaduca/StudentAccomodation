# StudentAccomodation
Evidența cazării în cămine (Stable Roommate Problem)

- inserarea studentului care aplică în baza de date; (Alina)
- renuntare + inscriere sesiunea 2; (Bianca)
- descarcare lista in format csv (in functie de camin sau de facultate) + PDF!!!;

Admin

-> Repartizare studenti (apeleaza RepartizareStudentiInCamin());

-> Repartizare turul 2 (apeleaza Repartizare2StudentiInCamin());

-> Salveaza liste (apeleaza .....)



Student

-> inscriere (apeleaza InsereazaStudentInBD - de implementat);

-> verificare camin (select camin_repartizat from studednti1 - de implementat);

         -> introducere nr matricol (verificare cu select)
         
                  -> a intrat la camin
                  
                        -> ii afisam caminul (cu un select)
                        
                          -> confirma loc - Te-ai cazat cu succes
                          
                          -> renunta la camin - delete student
                          
                  -> nu a intrat la camin
                  
                           -> reinscrie in turul 2 - reinscris_la_camin devine 1




