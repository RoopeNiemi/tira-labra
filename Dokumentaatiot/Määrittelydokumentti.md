# Määrittelydokumentti
### Aihe ja käytetyt tietorakenteet
Labratyön aiheeksi olen valinnut Dijkstran ja A* algoritmin, näiden toteuttamiseen käytän ainakin lista, pino, jono, joukko (HashSet) ja minimikeko tietorakenteita. Ohjelman tavoitteena on käyttää kahta yllämainittua reitinhakualgoritmia ja vertailla niiden toimintaa harvassa verkossa. Vertailutapoja on kaksi: kauan algoritmin suorituksessa menee aikaa millisekunteina, tai vastaavasti montako solmua algoritmi on käynyt läpi kunnes reitti on löytynyt. Olen päätynyt jälkimmäiseen eli läpikäytävien solmujen määrän mittaamiseen, sillä ne pysyvät samoina huolimatta siitä kuinka monta kertaa kyseinen haku tehdään. Lisäksi molemmat algoritmit käyttävät samaa hakualgoritmia,ja ero on vain solmujen ominaisuuksissa eli siinä, miten niiden paikka prioriteettijonossa määrittyy. Tästä syystä vertailu läpikäytyjen solmujen avulla on mahdollista.

### Syötteet ja niiden käyttö
Tarkoituksena tehdä ohjelma jossa taustalla Suomen kartta joka sisältää tietyn määrän kaupunkeja, joten syötteenä ohjelma saa lähtökaupungin ja kaupungin, johon reitti haetaan. Tämä syöte annetaan joko kirjoittamalla tekstikenttään kartalla olevan kaupungin nimi tai mahdollisesti valitsemalla listasta kaupungit klikkaamalla. 

Kaupungin nimellä pystytään hakemaan solmujen listasta kyseiset solmut ja antamaan ne syötteeksi reitinhakualgoritmeille, joka hakee reitin ja piirtää sen kartalle. 
### Aika -ja tilavaativuudet

Tavoitteena pysyä Dijkstran tapauksessa aikavaatimuksessa O((V+E)log V). Tilavaatimus O(V+E), sillä talletetaan muistiin sekä kaaret että solmut. A* nopeusvaatimukseksi sama kuin Dijkstran, ja myös tilavaatimus sama kuin Dijkstralla.


