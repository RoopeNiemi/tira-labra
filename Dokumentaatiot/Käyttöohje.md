# Käyttöohje
Ohjelma toimii käyttöliittymän avulla. Käyttöliittymässä kaksi näkymää, joiden välillä pystyy liikkumaan "Näytä tilasto" ja "Näytä kartta" nappien avulla.
Karttanäkymässä on itse algoritmien toiminta. Reitinhaku tapahtuu valitsemalla lähtö- ja kohdekaupungit kirjoittamalla niiden nimet tekstikenttään, valitsemalla
vaihtoehdoista joko A* tai Dikjstra, ja painamalla "Hae". 

Kaupunkien nimiä kirjoitettaessa tulee ottaa huomioon että ääkköset eivät toimi, esim. Seinäjoki pitää
kirjoittaa muodossa Seinajoki. Lisäksi isoilla alkukirjaimilla ei ole merkitystä, samoin jos vahingossa lisää välilyönnin nimen perään ei se haittaa. Ohjelma piirtää haussa tapahtuneen solmujen käsittelyn, keltainen=löydetty solmu, punainen=prioriteettijonosta 
nostettu solmu. Haun jälkeen lyhin reitti piirretään ruudulle sinisenä. Lisäksi alas piirretään käytyjen solmujen lukumäärä, jotta hakuja
eri hakualgoritmeilla pystyy verrata suoraan ohjelmassa. 

Tilastonäkymässä näkyy tuloksia mitkä ollaan saatu kun ollaan haettu kaupungeista jokaiseen kaupunkiin hakuja sekä A* että Dijkstralla. Tilastossa näkyy kaupunkikohtaiset haut
ja siinä näytetään viivadiagrammien avulla läpikäydyt solmut molempien algoritmien tapauksessa. 
