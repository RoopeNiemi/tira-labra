
# Viikkoraportti 2

Tällä viikolla tein käyttöliittymää ja algoritmit. Ohjelmassa nyt taustakuva jonka perusteella lisätty solmut ja kaaret oikeisiin kohtiin. Dijkstra ja AStar algoritmit toimivat näitä solmuja ja kaaria hyödyntäen. Niiden käyttämistä varten luotu tekstikentät lähtökaupungille ja kohdekaupungille. Lisätty mahdollisuus valita kumpaa hakualgoritmia käyttää haussa, ja "Hae" ja "Reset" napit.  
#### Solmuista
Kaupunkisolmut nimetty, kaupunkeja 106 kappaletta, loput ovat nimeämättömiä solmuja teiden varsilla. Solmujen yhteismäärä 411 kappaletta. Solmut tekstitiedostossa muotoa x-koordinaatti, y-koordinaatti, nimi ("NULL" jos nimeämätön), ja arvo (käytetään kun tarkistetaan onko solmussa jo käyty, myöhemmin algoritmien yhteydessä). 

#### Kaarista
Kaaret ovat muotoa x-koordinaatti, y-koordinaatti, pituus, nopeus. Nopeudet joko 60,80 tai 100km/h. Väreinä näkyvät kartalla 60km/h musta viiva, 80km/h sininen ja 100km/h punainen. Nopeudet talvinopeuksien mukaisia ja pääosin Suomen teiden nopeuksien mukaisia. Kaaret eivät ole tällä hetkellä näkyvissä ohjelmaa suoritettaessa.
Teiden nopeudet katsottu osoitteesta https://liikennevirasto.maps.arcgis.com/apps/webappviewer/index.html?id=54679cc79970418f9bada23d6f9f9c29 . 

Alla kuva josta näkee ohjelmassa käytetyt nopeudet ja liikenneviraston sivuilla näkyvät nopeudet.
![alt text](https://github.com/RoopeNiemi/tira-labra/blob/master/Kuvia/nopeusrajoitusVertailu.png)

#### Tietorakenteista
Ohjelmassa on toteutettu Verkko, ASolmu, DijkstraSolmu ja Kaari-luokat. Toimivat toistaiseksi valmiilla tietorakenteilla. ASolmu ja DijkstraSolmut muuten samoja mutta ASolmun compareTo-metodi ottaa huomioon myös etäisyyden kohdesolmusta. ASolmu ja DijkstraSolmu molemmat hyödyntävät Solmu-rajapintaa. Koska solmuja on kahta erilaista luodaan näitä varten ohjelman käynnistyksen yhteydessä molemmille omat verkot ja vierussolmut.

#### Dijkstrasta ja AStarista
Dijkstran toiminta tehty, toimii kaupunkien nimien perusteella. Testattu kymmeniä kertoja ja tuntuu toimivan niin kuin pitääkin. Tekee haun ja jälkikäteen maalaa ajastimella hakuprosessin toiminnan jonka lopuksi maalaa lyhimmän polun solmut mustaksi. Nimihaku ei toimi ääkkösillä, vaan esim Kemijärvi pitää hakea muodossa Kemijarvi. AStar algoritmi myös lisätty. 

#### Muuta
Vaikeuksia ei juurikaan ollut sillä solmujen ja kaarien lisääminen oli suhteellisen mekaanista klikkailua. Ongelmia tuli kun kaupunkien nimet sekoilivat jostain syystä, ja piti kirjoittaa ne uusiksi. Lisäksi päänvaivaa aiheutti ääkkösten toimimattomuus. Ohjelman edistyminen vauhdittui huomattavasti solmujen ja kaarien luomisen jälkeen. 

Aikaa käyttänyt tällä viikolla noin 18-20h. Seuraavaksi tarkoitus aloittaa omien tietorakenteiden teko.

