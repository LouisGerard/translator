EXEMPLES DE FICHIERS ET RESULTATS SUR LE CORPUS RATP

- Modele de langage 2gram: data_ratp/modele_2g_ratp_fr.txt

- Calcul de perplexité sur un ensemble de phrase et tri par perplexité croissante
 * log base e et exp pour la perplexite, lissage de Laplace avec alpha=0,01

41.8 <s> je vais à Paris </s> 
61.4 <s> on va à Paris </s> 
89.7 <s> elle va à Paris </s> 
284.7 <s> je va à Paris </s> 
404.2 <s> on vais à Paris </s> 
429.2 <s> elle vais à Paris </s> 
478.7 <s> tu vais à Paris </s> 
512.6 <s> tu vas à Paris </s> 

- Table de traduction: Anglais=>Francais 20 itérations : data_ratp/table_ratp_en_fr_20iter.code
 * sur les corpus : data_ratp/corpus_ratp_bilang.en.code data_ratp/corpus_ratp_bilang.fr.code
 * en ne gardant que les traduction avec une proba > 0.001

Apres iteration 0, P(hello|bonjour)=0.387713
Apres iteration 1, P(hello|bonjour)=0.648234
Apres iteration 2, P(hello|bonjour)=0.769102
Apres iteration 3, P(hello|bonjour)=0.815874
Apres iteration 4, P(hello|bonjour)=0.834137
Apres iteration 5, P(hello|bonjour)=0.840395
Apres iteration 6, P(hello|bonjour)=0.841528
Apres iteration 7, P(hello|bonjour)=0.840585
Apres iteration 8, P(hello|bonjour)=0.838920
Apres iteration 9, P(hello|bonjour)=0.837116
Apres iteration 10, P(hello|bonjour)=0.835405
Apres iteration 11, P(hello|bonjour)=0.833866
Apres iteration 12, P(hello|bonjour)=0.832510
Apres iteration 13, P(hello|bonjour)=0.831324
Apres iteration 14, P(hello|bonjour)=0.830286
Apres iteration 15, P(hello|bonjour)=0.829374
Apres iteration 16, P(hello|bonjour)=0.828570
Apres iteration 17, P(hello|bonjour)=0.827857
Apres iteration 18, P(hello|bonjour)=0.827220
Apres iteration 19, P(hello|bonjour)=0.826650

- Traduction anglais -> francais sur la phrase : I want to go to Paris by bus or underground
  * avec uniquement les probabilités d'émission (table de traduction) = je veulent de rendez de Paris critères bus ou métro
  * avec le modèle de langage = je veux aller jusqu' à Paris par bus ou métro 

 
