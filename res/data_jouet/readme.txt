CAS1 : TRADUCTION DE L'ANGLAIS SUR LE FRANCAIS

- Modele de langage 2gram sur le francais: data_jouet/modele_jouet_2g_fr.txt

- Calcul de perplexité sur les phrases du corpus d'apprentissage:
 * log base e et exp pour la perplexite, lissage de Laplace avec alpha=0,01
2.4 <s> la maison fraîche est cool </s> 
2.5 <s> ils sont sympa </s> 
2.5 <s> tu es sympa </s> 
2.5 <s> vous êtes sympa </s> 
2.7 <s> la maison est fraîche </s> 
2.8 <s> on est cool </s> 
3.8 <s> maison sympa </s> 

- Table de traduction: anglais=>francais avec 10 itérations : data_jouet/table_trad_en2fr_10.txt
 * table apprise sur les corpus : data_jouet/corpus_jouet_fr.txt.code et data_jouet/corpus_jouet_en.txt.code
 * en ne gardant que les traduction avec une proba > 0.001
 * évolution des probabilté de traduction à chaque itération :
Apres iteration 0, P(house|maison)=0.181548
Apres iteration 1, P(house|maison)=0.241913
Apres iteration 2, P(house|maison)=0.308246
Apres iteration 3, P(house|maison)=0.379128
Apres iteration 4, P(house|maison)=0.449528
Apres iteration 5, P(house|maison)=0.515449
Apres iteration 6, P(house|maison)=0.574690
Apres iteration 7, P(house|maison)=0.626523
Apres iteration 8, P(house|maison)=0.671143
Apres iteration 9, P(house|maison)=0.709214

- Traduction anglais => francais sur la phrase : the house is cool
  * sur le treillis : data_jouet/ex_treillis_fr.txt
  * avec uniquement les probabilités d'émission (table de traduction) = la maison la cool 
  * avec le modèle de langage = la maison est cool 


----

CAS2 : TRADUCTION DU FRANCAIS VERS L'ANGLAIS

- Modele de langage 2gram sur l'anglais: data_jouet/modele_jouet_2g_en.txt

- Calcul de perplexité sur les phrases du corpus d'apprentissage:
 * log base e et exp pour la perplexite, lissage de Laplace avec alpha=0,01
2.0 <s> the house is nice <s> 
2.3 <s> you are cool <s> 
2.4 <s> the cool house is nice <s> 
2.4 <s> the house is cool <s> 
2.7 <s> they are cool <s> 
2.7 <s> we are cool <s> 
2.7 <s> you are nice <s> 
5.0 <s> cool house </s> 

- Table de traduction: francais=>anglais avec 10 itérations : data_jouet/table_trad_fr2en_10.txt
 * table apprise sur les corpus : data_jouet/corpus_jouet_fr.txt.code et data_jouet/corpus_jouet_en.txt.code
 * en ne gardant que les traduction avec une proba > 0.001
 * évolution des probabilté de traduction à chaque itération :
Apres iteration 0, P(maison|house)=0.181548
Apres iteration 1, P(maison|house)=0.232585
Apres iteration 2, P(maison|house)=0.291118
Apres iteration 3, P(maison|house)=0.353370
Apres iteration 4, P(maison|house)=0.415321
Apres iteration 5, P(maison|house)=0.474174
Apres iteration 6, P(maison|house)=0.528487
Apres iteration 7, P(maison|house)=0.577780
Apres iteration 8, P(maison|house)=0.622111
Apres iteration 9, P(maison|house)=0.661799

- Traduction francais => anglais sur la phrase : la maison est cool
  * sur le treillis : data_jouet/ex_treillis_en.txt
  * avec uniquement les probabilités d'émission (table de traduction) = the house the we
  * avec le modèle de langage = the house is nice
 
