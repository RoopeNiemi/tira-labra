
# Toteutusdokumentti

### Ohjelman yleisrakenne
Ohjelma koostuu seuraavista pakkauksista:
1. *tiralabra.reitinhakuohjelma.kayttoliittyma*  

   Sisältää käyttöliittymän. Piirtäminen ruudulle, lähtö- ja kohdekaupunkien valinta sekä kaikki käyttöliittymän toiminta tapahtuu tässä luokassa. Tässä luokassa tapahtuu myös tietojen lataaminen listoihin ja verkon luonti ohjelman käynnistyessä.
            
2. *tiralabra.reitinhakuohjelma.rakenne*

   Sisältää kaikki ohjelman tietorakenteet. Sisältää luokat Pino, Jono, Joukko, Prioriteettijono sekä
            Lista. 
            
3. *tiralabra.reitinhakuohjelma.verkko*

   Sisältää Verkko-luokan sekä sen käyttämiseen liittyvät apuluokat Solmu ja Kaari. Sisältää lisäksi
            reitinhakualgoritmia käyttävän Reitinhakija-luokan.
            
### Saavutetut aika- ja tilavaativuudet

### Suorituskyky ja O-analyysivertailut
Koska sekä A* että Dijkstra käyttävät molemmat samaa hakualgoritmia, eivät algoritmien aikavaativuudet oikeastaan eroa toisistaan. Pieni ero löytyy A* ja Dijkstran alustusvaiheessa, sillä A* alustuksen yhteydessä lasketaan jokaiselle solmulle lisäksi etäisyys maalista, mikä lisää hieman aikavaativuutta. Kuitenkin molemmat pysyvät alustusvaiheessa aikavaativuudessa O(V), missä V on solmujen määrä. 

### Mahdolliset puutteet ja parannusehdotukset

### Lähteet
Ohjelman taustalla on kuva Suomen kartasta. [Kuva](https://upload.wikimedia.org/wikipedia/commons/thumb/b/be/Map_of_Finland-fi.svg/2000px-Map_of_Finland-fi.svg.png) haettu Googlesta käyttöoikeuksilla "non-commercial reuse with modification".
Tienopeudet joita käytettiin kaarien painojen määrittelyssä hain [Liikenneviraston](https://liikennevirasto.maps.arcgis.com/apps/webappviewer/index.html?id=54679cc79970418f9bada23d6f9f9c29) sivuilta




