# Käyttöohje
Ohjelma toimii käyttöliittymän avulla. Käyttöliittymässä kaksi näkymää, karttanäkymä ja tilastonäkymä.
Karttanäkymässä on itse algoritmien toiminta. Reitinhaku tapahtuu valitsemalla lähtö- ja kohdekaupungit kirjoittamalla niiden nimet tekstikenttään, valitsemalla
vaihtoehdoista joko A* tai Dikjstra, ja painamalla "Hae". "Reset" nappi tyhjentää maalatut solmut ja keskeyttää haun jos sellainen on käynnissä.

Kaupunkien nimiä kirjoitettaessa tulee ottaa huomioon että ääkköset eivät toimi. Isoilla alkukirjaimilla ei ole merkitystä, samoin jos vahingossa lisää välilyönnin nimen perään ei se haittaa. Ohjelma piirtää haussa tapahtuneen solmujen käsittelyn seuraavasti: keltainen=löydetty solmu, punainen=prioriteettijonosta nostettu solmu jonka vieruslista läpikäyty. Haun jälkeen lyhimmän reitin solmut piirretään ruudulle sinisenä. Lisäksi alas piirretään käytyjen solmujen lukumäärä, jotta hakuja eri hakualgoritmeilla pystyy verrata suoraan ohjelmassa. 

Tilastonäkymässä näkyy tuloksia mitkä ollaan saatu kun ollaan haettu kaupungeista jokaiseen kaupunkiin hakuja sekä A* että Dijkstralla. Tilastossa näkyy kaupunkikohtaiset haut
ja siinä näytetään viivadiagrammien avulla läpikäydyt solmut molempien algoritmien tapauksessa. 
