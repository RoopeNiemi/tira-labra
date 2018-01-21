
# Toteutusdokumentti

## Ohjelman yleisrakenne
Ohjelma koostuu seuraavista pakkauksista:
1. *tiralabra.reitinhakuohjelma.kayttoliittyma*  

   Sisältää käyttöliittymän. Piirtäminen ruudulle, lähtö- ja kohdekaupunkien valinta sekä kaikki käyttöliittymän toiminta tapahtuu tässä luokassa. Tässä luokassa tapahtuu myös tietojen lataaminen listoihin ja verkon luonti ohjelman käynnistyessä.
            
2. *tiralabra.reitinhakuohjelma.rakenne*

   Sisältää kaikki ohjelman tietorakenteet. Sisältää luokat Pino, Jono, Joukko, Prioriteettijono sekä
            Lista. Näistä Pino, Jono ja Lista ovat geneerisiä. Prioriteettijono toimii solmuilla ja Joukko integereillä. 
            
3. *tiralabra.reitinhakuohjelma.verkko*

   Sisältää Verkko-luokan sekä sen käyttämiseen liittyvät apuluokat Solmu ja Kaari. Sisältää lisäksi
            reitinhakualgoritmia käyttävän Reitinhakija-luokan. Verkon kaaret on tallennettu vieruslistamuodossa solmuihin. 
            
            
## Saavutetut aika- ja tilavaativuudet
Molempien algoritmien alustus toimii ajassa O(V), sillä alustuksissa solmut käydään kerran läpi. 

#### Aikavaativuudet
Hakualgoritmien toiminta toimii ajassa O((V+E)log V). Koko algoritmi toistuu niin kauan kuin prioriteettijonossa on solmuja. Kuitenkin, jos solmussa on jo käyty aloitetaan alusta, joten koko toiminto tehdään vain enintään solmujen määrän kerran. Huonoimmassa tapauksessa käydään siis kaikki solmut. Tästä johtuen myös kaikki kaaret käydään tällöin silmukalla läpi, sillä algoritmi käy käymättömän solmun vieruslistan läpi. Vieruslistaa läpikäydessä kutsutaan relax metodia, jonka lopussa solmu lisätään uudelleen minimikekoon. Tähän menee keon rakenteen mukaisesti enintään keon korkeuden verran, mikä on logaritmi keossa olevien solmujen määrästä, eli log V. Pahimmassa tapauksessa kun käydään kaikki solmut ja kaikki vieruslistat, lisätään solmu prioriteettijonoon jokaisen uuden kaaren kohdalla. Aikavaativuudeksi tulee täten O((V+E) log V). 

#### Tilavaativuudet
Algoritmit käyttävät yhtä prioriteettijonoa. Koska algoritmissa ei käytetä keon decrease-key metodia, voidaan sama solmu lisätä kekoon useamman kerran. Kuitenkin koska verkko on harva, tarkoittaa tämä että yksittäinen solmu lisätään huonoimmassa tapauksessa vain muutaman kerran. Lisäksi prioriteettijonosta poistetaan jatkuvasti solmuja. Jokainen solmu sisältää tiedon siitä lähtevistä kaarista. Tilavaativuudeksi tulee näin O(V+E).


## Suorituskyky ja O-analyysivertailut
Koska sekä A* että Dijkstra käyttävät molemmat samaa hakualgoritmia, eivät algoritmien huonoimman tapauksen aikavaativuudet  eroa toisistaan. Pieni ero löytyy A* ja Dijkstran alustusvaiheessa, sillä A* alustuksen yhteydessä lasketaan jokaiselle solmulle lisäksi etäisyys maalista, mikä lisää hieman aikavaativuutta. Kuitenkin molemmat pysyvät alustusvaiheessa aikavaativuudessa O(V), missä V on solmujen määrä. Tarkemmin suorituskykyvertailusta ja sen tuloksista testausdokumentissa.

### Lähteet
Ohjelman taustalla on kuva Suomen kartasta. [Kuva](https://upload.wikimedia.org/wikipedia/commons/thumb/b/be/Map_of_Finland-fi.svg/2000px-Map_of_Finland-fi.svg.png) haettu Googlesta käyttöoikeuksilla "non-commercial reuse with modification".
Tienopeudet joita käytettiin kaarien painojen määrittelyssä hain [Liikenneviraston](https://liikennevirasto.maps.arcgis.com/apps/webappviewer/index.html?id=54679cc79970418f9bada23d6f9f9c29) sivuilta




