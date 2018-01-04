
# Viikkoraportti 3

Tällä viikolla tein tietorakenteita Pino, Jono, Joukko ja Lista. Lisäksi olen pilkkonut start-metodia hieman osiin, jotta ei olisi niin järjettömän kokoinen. Jokaisen tietorakenteen paitsi Joukon taustalla on taulukko, jonka koko tuplaantuu kun kyseinen tietorakenne on täynnä. Poistotilanteessa kokoa ei puoliteta vaikka tietorakenteessa olisi vain alle puolet taulukon määrästä, sillä joko tietorakenteista ei poisteta ollenkaan kuten listan tapauksessa asia on, tai niistä poistetaan kaikki solmut peräkkäin, jolloin jatkuva tietorakenteen pienennys vain lisäisi käytettävää aikaa turhaan. Lisäksi Pino, Jono ja Lista ovat geneerisiä tietorakenteita.  

#### Joukosta

Joukon tapauksessa taulukon koko on 412, mikä on verkon solmujen määrä. Tämä valittu siksi että joukkoa ei käytetä muussa tapauksessa kuin tarkistettaessa onko jossain solmussa jo käyty algoritmin nykyisen suorituksen aikana. Joukossa numeron sijainti taulukossa on yhtä kuin numero itse, joten päällekkäisyyksiä ei tule. Lisättäessä otetaan huomioon onko taulukossa jo kyseisessä kohdassa lisätty luku, jotta joukon oikea koko ei muutu turhaan. Jos numeroa ei ole on taulukon kyseisessä kohdassa luku -5. Koska joukkoa ei käytetä muussa kuin tässä yhteydessä on se näistä neljästä tietorakenteesta ainoa joka ei ole geneerinen,vaan se toimii ainoastaan integereillä.  

#### Start metodista
Start metodin pilkoin osiin, nyt vasemman valikon luominen tapahtuu yhdessä erillisessä metodissa, joka käyttää kolmea muuta metodia luodakseen vasemman valikon osat. Start metodissa kutsutaan ainoastaan yhtä metodia koko vasemman valikon luomiseen. 
