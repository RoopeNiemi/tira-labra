# Testausdokumentti

### Mitä on testattu, miten tämä tehtiin? 
Testeissä on vertailtu A* ja Dijkstran hakualgoritmien toimintaa. Koska molemmat algoritmit käyttävät samaa hakualgoritmia,
ja eroavaisuuksia käytännössä vain algoritmin alustusvaiheessa, olen päätynyt vertaamaan läpikäytyjä solmujen määrää. Tästä on se hyöty, että ei tarvitse ottaa mediaania suorituksista, sillä läpikäytyjen solmujen määrä samassa haussa pysyy samana riippumatta siitä kuinka monta kertaa haku toistetaan. 

Algoritmin toiminnassa pidetään yllä läpikäytyjen solmujen joukkoja, joten kun prioriteettijonosta nostetaan solmu, tehdään sille 
jotain vain jos se ei ole läpikäytyjen solmujen joukossa. Lisäksi algoritmi on asetettu lopettamaan toimintansa kun solmu johon 
reitti halutaan etsiä nostetaan prioriteettijonosta. Tämä mahdollistaa sen, että algoritmin suorituksen loputtua voidaan ottaa 
talteen sen hetkinen läpikäytyjen solmujen joukon määrä. Tämän jälkeen voidaan suorittaa samoilla syötteillä toinen hakualgoritmi,
jonka jälkeen sen hetkinen läpikäytävien solmujen määrä otetaan talteen. Näitä kahta syötettä vertaillaan lopuksi.

Mainittakoon tässä vaiheessa että erona ajan mittaamiseen algoritmien suorituksissa tässä ei oteta huomioon alustusvaihetta, jossa A* tekee hieman enemmän työtä. 

### Minkälaisilla syötteillä testaus tehtiin?
Olen testannut itse algoritmien toimintaa hakemalla satunnaisista kaupungeista reittejä toisiin kaupunkeihin. Ohjelma piirtää läpikäydyt solmut, lyhimmän löydetyn reitin sekä läpikäytyjen solmujen määrän ruudulle.

### Miten testit voidaan toistaa
MainApp-luokan start-metodissa on kommentoitu metodi haeKaikki(). Kun tämän kommentimerkit poistaa käydään käynnistettäessä läpi haku kaikista kaupungeista kaikkiin kaupunkeihin, ja tulostetaan ruudulle läpikäytyjen solmujen keskiarvo sekä suurin ja pienin määrä läpikäytyjä solmuja per algoritmi. Lisäksi ohjelman testiluokassa ReitinhakijaTesti on testit, mitkä
testaavat haun jokaisesta kaupungista jokaiseen kaupunkiin kummallakin algoritmeilla. Testit varmistavat että molemmat algoritmit
tuottavat saman lyhimmän reitin, sekä sen että A* käy aina enintään yhtä monta solmua läpi kuin Dijkstran algoritmi. 

### Testauksien tuloksia
Ohjelmassa on mahdollista katsoa tilastoja, joissa viivadiagrammien avulla vertaillaan läpikäytyjen solmujen määrää eri algoritmeilla.
Tilastot ovat kaupunkikohtaisia, ja niistä näkyy läpikäytyjen solmujen määrä haettaessa reittiä jokaiseen kaupunkiin (mukaan lukien
lähtökaupunkiin itseensä, jolloin läpikäytävien solmujen määrä on 0). Kaupunkeja on yhteensä 118, eli haettaessa jokaisesta kaupungista jokaiseen kaupunkiin sekä Dijkstralla että A*, tulee hakuja 13 924 per algoritmi eli yhteensä 27 848. 
Alla muutamia esimerkkejä.

![alt text](https://github.com/RoopeNiemi/tira-labra/blob/master/Kuvia/helsinkiTilasto.png) ![alt text](https://github.com/RoopeNiemi/tira-labra/blob/master/Kuvia/jyvaskylaTilasto.png) ![alt text](https://github.com/RoopeNiemi/tira-labra/blob/master/Kuvia/rovaniemiTilasto.png)

Kaikkien hakujen pohjalta seuraavia tilastoja vielä:

#### Dijkstra                                
Kaikkien hakujen keskiarvo: 190 solmua            
Alin solmumäärä: 0 (solmusta itseensä)             
Ylin solmumäärä: 411 (Helsinki-Utsjoki)             

#### Astar

Kaikkien hakujen keskiarvo: 73 solmua   
Alin solmumäärä: 0 (solmusta itseensä)   
Ylin solmumäärä: 405 (Helsinki-Utsjoki)   
