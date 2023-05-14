# StudentAccomodation
Evidența cazării în cămine (Stable Roommate Problem)

- inserarea studentului care aplică în baza de date; (Alina)
- renuntare + inscriere sesiunea 2; (Bianca)
- descarcare lista in format csv (in functie de camin sau de facultate) + PDF!!!;
- buton home/back;

/home/alina/Desktop/StudentAccomodation ; /usr/bin/env /usr/lib/jvm/jdk-19/bin/java @/tmp/cp_2ebg03bjmy4w856abn960c6oz.argfile -m com.example.demo/com.example.server.Main

/home/alina/Desktop/StudentAccomodation ; /usr/bin/env /usr/lib/jvm/jdk-19/bin/java @/tmp/cp_2ebg03bjmy4w856abn960c6oz.argfile -m com.example.demo/com.example.client.ClientApplication

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




