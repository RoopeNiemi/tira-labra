
# Viikkoraportti 4
Tällä viikolla tein prioriteettijonoluokan sekä vaihdoin projektin Maven projektiksi. Lisäksi tehnyt pieniä korjailuja, mm. kaaren painot oli laskettu väärin aikaisemmin. Aikaa tällä viikolla mennyt noin 15h.

#### Kaarista
Kaaren painot lasketaan nyt oikein, ottaa huomioon tieosuuden nopeuden sekä pituuden oikealla tavalla. 

#### Prioriteettijonosta
Prioriteettijono on tehty. Hetken aikaa luulin että A* toiminnassa jotain vikaa kun yhdessä ainoassa haussa 13 924:sta antoi A* eri reitin kuin Dijkstra, reitin pituuskin oli 2.4 pidempi kuin Dijkstran antama. Vika oli kuitenkin prioriteettijonon heapify-metodin yhdessä vertailukohdassa, jossa vertailtiin väärinpäin.  Nyt prioriteettijono näyttäisi toimivan  niinkuin pitääkin.

#### Reitinhausta
Testattu jokaisesta kaupungista jokaiseen kaupunkiin reitinhakua sekä A* että Dijkstralla. Jokainen haku antaa saman reitin, lisäksi A* nopeus on aina nopeampi tai yhtä nopea kuin Dijkstra. Hakutulokset ovat erillisissa tekstitiedostoissa muodossa läpikäydyt solmut, lähtökaupunki-kohdekaupunki. 

#### Tilastoja
Hakutuloksista tehty tilasto jota voi katsoa "näytä tilasto" nappia painamalla. Näkyy lähtökaupungin perusteella haut muihin kaupunkeihin viivadiagrammina. 

#### Ongelmia/muuta
Projektin vaihtamisessa Maveniksi oli ongelmia, yhdessä vaiheessa ohjelman jokaisen käynnistyskerran yhteydessä poistettiin ja ladattiin riippuvuudet täysin uusiksi, johon meni minuutteja. Nyt näyttäisi ainakin omalla kotikoneella ja fuksiläppärillä käynnistyvän nopeasti. On kuitenkin mahdollinen ongelmakohta.
