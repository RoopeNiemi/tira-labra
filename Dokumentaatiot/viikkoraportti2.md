
# Viikkoraportti 2

Tällä viikolla tein käyttöliittymää. Ohjelmassa nyt taustakuva jonka perusteella lisätty solmut oikeisiin kohtiin. 
#### Solmuista
Kaupunkisolmut nimetty, kaupunkeja 106 kappaletta, loput ovat nimeämättömiä solmuja teiden varsilla. Kartalla keltaiset solmut nimeämättömiä punaiset kaupunkeja. Solmut tekstitiedostossa muotoa x-koordinaatti, y-koordinaatti, nimi ("NULL" jos nimeämätön), ja arvo (käytetään kun tarkistetaan onko solmussa jo käyty, myöhemmin algoritmien yhteydessä). 

#### Kaarista
Kaaret ovat muotoa x-koordinaatti, y-koordinaatti, pituus, nopeus. Nopeudet joko 60,80 tai 100km/h. Väreinä näkyvät kartalla 60km/h musta viiva, 80km/h sininen ja 100km/h punainen. Nopeudet talvinopeuksien mukaisia ja pääosin Suomen teiden nopeuksien mukaisia.

#### Tietorakenteista
Ohjelmassa on toteutettu Verkko, Solmu ja Kaari-luokat. Toimivat toistaiseksi valmiilla tietorakenteilla.

#### Muuta
Vaikeuksia ei juurikaan ollut sillä solmujen ja kaarien lisääminen oli suhteellisen mekaanista klikkailua. Ongelmia tuli kun kaupunkien nimet sekoilivat jostain syystä, ja piti kirjoittaa ne uusiksi. Lisäksi päänvaivaa aiheutti ääkkösten toimimattomuus. 
Tällä viikolla käyttänyt aikaa noin 10-12h. Seuraavaksi tarkoitus keskittyä itse algoritmien toimintaan.