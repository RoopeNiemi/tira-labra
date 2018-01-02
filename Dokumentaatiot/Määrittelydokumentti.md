# Määrittelydokumentti
### Aihe ja käytetyt tietorakenteet
Labratyön aiheeksi olen valinnut Dijkstran ja A* algoritmin, näiden toteuttamiseen käytän ainakin lista ja minimikeko tietorakenteita. Ohjelman tavoitteena on käyttää kahta yllämainittua reitinhakualgoritmia ja vertailla niiden toimintaa harvassa verkossa.  
### Syötteet ja niiden käyttö
Tarkoituksena tehdä ohjelma jossa taustalla Suomen kartta joka sisältää tietyn määrän kaupunkeja, joten syötteenä ohjelma saa lähtökaupungin ja kaupungin, johon reitti haetaan. Tämä syöte annetaan joko kirjoittamalla tekstikenttään kartalla olevan kaupungin nimi tai mahdollisesti valitsemalla listasta kaupungit klikkaamalla. 

Kaupungin nimellä pystytään hakemaan solmujen listasta kyseiset solmut ja antamaan ne syötteeksi reitinhakualgoritmeille, joka hakee reitin ja piirtää sen kartalle. 
### Aika -ja tilavaativuudet
Tavoitteena pysyä Dijkstran tapauksessa aikavaatimuksessa O((V+E)log V). Tilavaatimus O(V+E), sillä talletetaan muistiin sekä kaaret että solmut. A* nopeusvaatimukseksi tavoite O(V) ja tilavaatimus sama kuin Dijkstralla.


