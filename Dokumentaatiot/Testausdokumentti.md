# Testausdokumentti

### Mitä on testattu, miten tämä tehtiin? 
Testeissä on vertailtu A* ja Dijkstran hakualgoritmien suoritusnopeuksia. Koska molemmat algoritmit käyttävät samaa hakualgoritmia,
ja eroavaisuuksia käytännössä vain algoritmin alustusvaiheessa, olen päätynyt vertaamaan läpikäytyjä solmujen määrää. 

Algoritmin toiminnassa pidetään yllä läpikäytyjen solmujen joukkoja, joten kun prioriteettijonosta nostetaan solmu, tehdään sille 
jotain vain jos se ei ole läpikäytyjen solmujen joukossa. Lisäksi algoritmi on asetettu lopettamaan toimintansa kun solmu johon 
reitti halutaan etsiä nostetaan prioriteettijonosta. Tämä mahdollistaa sen, että algoritmin suorituksen loputtua voidaan ottaa 
talteen sen hetkinen läpikäytyjen solmujen joukon määrä. Tämän jälkeen voidaan suorittaa samoilla syötteillä toinen hakualgoritmi,
jonka jälkeen sen hetkinen läpikäytävien solmujen määrä otetaan talteen. Näitä kahta syötettä vertaillaan lopuksi.

Olen testannut ohjelmaa hakemalla reittejä kaupungista toiseen molemmilla algoritmeilla. Lisäksi ohjelman testeissä on testit, mitkä
testaavat haun jokaisesta kaupungista jokaiseen kaupunkiin kummallakin algoritmeilla. Testit varmistavat että molemmat algoritmit
tuottavat saman lyhimmän reitin, sekä sen että A* on aina vähintään yhtä nopea kuin Dijkstran algoritmi. 

### Minkälaisilla syötteillä testaus tehtiin?
Olen testannut itse algoritmien toimintaa hakemalla satunnaisista kaupungeista reittejä toisiin kaupunkeihin. Ohjelma piirtää läpikäydyt
solmut  sekä lyhimmän löydetyn reitin ruudulle.

### Testauksien tuloksia
Ohjelmassa on mahdollista katsoa tilastoja, joissa viivadiagrammien avulla vertaillaan läpikäytyjen solmujen määrää eri algoritmeilla.
Tilastot ovat kaupunkikohtaisia, ja niistä näkyy läpikäytyjen solmujen määrä haettaessa reittiä jokaiseen kaupunkiin (mukaan lukien
lähtökaupunkiin itseensä, jolloin läpikäytävien solmujen määrä on 0). 
