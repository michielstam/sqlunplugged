Create Table Klant
      (klantnr          int(5)       ,
       naam             varchar(30)    ,
       adres            varchar(30)    ,
       plaats           varchar(30)    ,
       Primary Key(klantnr)                    );

Insert Into Klant Values (121, 'Smit'  , 'Hamburgerstraat 55'   , 'Utrecht'   );
Insert Into Klant Values (122, 'Staal' , 'Veronesestraat 6'     , 'Amsterdam' );
Insert Into Klant Values (123, 'Slager', 'Langestraat 3'        , 'Amersfoort');
Insert Into Klant Values (124, 'Snoek' , 'Neude 13'             , 'Utrecht'   );
Insert Into Klant Values (125, 'Boer'  , 'Prinses Irenelaan 19' , 'Utrecht'   );
Insert Into Klant Values (126, 'Groot' , 'Amsterdamseweg 99'    , 'Amersfoort');


Create Table Fabriek
      (fabnr            int(5)       ,
       naam             varchar(30)    ,
       adres            varchar(30)    ,
       plaats           varchar(30)    ,
       Primary Key(fabnr)                      );

Insert Into Fabriek Values (121, 'Kantoor'  , 'Joubertstraat 2', 'Den Haag'  );
Insert Into Fabriek Values (122, 'Popke'    , 'Popkenstraat 7' , 'Groningen' );
Insert Into Fabriek Values (123, 'Broese'   , 'Biltstraat 41'  , 'Utrecht'   );
Insert Into Fabriek Values (124, 'Office'   , 'Rijnkade 12'    , 'Utrecht'   );
Insert Into Fabriek Values (451, 'Webster'  , 'Damrak 64'      , 'Amsterdam' );
Insert Into Fabriek Values (452, 'Vermeulen', 'Lange Poten 42' , 'Den Haag'  );
Insert Into Fabriek Values (453, 'Ahrend'   , 'Utrechtseweg 87', 'Amersfoort');
Insert Into Fabriek Values (454, 'Hagemeyer', 'Berkenweg 12'   , 'Amersfoort');
Insert Into Fabriek Values (455, 'Office'   , 'Het Spui 12'    , 'Amsterdam' );
Insert Into Fabriek Values (456, 'Buhrmann' , 'Taagdreef 56'   , 'Utrecht'   );


Create Table Artikel
      (artnr            int(5)       ,
       naam             varchar(30)    ,
       adviesprijs      decimal(6, 2)    ,
       Primary Key(artnr)                      );

Insert Into Artikel Values (121, 'post-its'        ,  2.75);
Insert Into Artikel Values (122, 'high light pen'  ,  1.50);
Insert Into Artikel Values (123, 'diskettes 10pk'  ,  3.10);
Insert Into Artikel Values (124, 'nietmachine'     ,  4.75);
Insert Into Artikel Values (451, 'cd-rw'           ,  0.50);
Insert Into Artikel Values (452, 'bic pen'         ,  1.00);
Insert Into Artikel Values (453, 'tip-ex'          ,  2.10);
Insert Into Artikel Values (454, 'cd-rw 15pk'      ,  6.00);
Insert Into Artikel Values (456, 'bureaulamp'      , 10.00);
Insert Into Artikel Values (457, 'documenthouder'  , 22.50);
Insert Into Artikel Values (458, 'monitorstandaard', 10.90);
Insert Into Artikel Values (459, 'antistofhoes'    ,  8.10);


Create Table Voorraad
      (fabnr            int(5)       ,
       artnr            int(5)       ,
       vrd              int(5)       ,
       minvrd           int(5)       ,
       Primary Key(fabnr, artnr)                ,
       Foreign Key(fabnr) References Fabriek    ,
       Foreign Key(artnr) References Artikel   );

Insert Into Voorraad Values (121, 121, 200, 150);
Insert Into Voorraad Values (121, 122, 300,  75);
Insert Into Voorraad Values (121, 124, 150, 300);
Insert Into Voorraad Values (121, 456, 125, 125);
Insert Into Voorraad Values (122, 121, 700, 200);
Insert Into Voorraad Values (122, 122, 875, 100);
Insert Into Voorraad Values (122, 123,   0,  20);
Insert Into Voorraad Values (122, 124, 210, 210);
Insert Into Voorraad Values (122, 451, 370, 180);
Insert Into Voorraad Values (123, 123,  15,  30);
Insert Into Voorraad Values (123, 124, 175, 100);
Insert Into Voorraad Values (123, 456,  75, 100);
Insert Into Voorraad Values (124, 124, 300, 100);
Insert Into Voorraad Values (124, 459, 144,  50);
Insert Into Voorraad Values (451, 121, 200, 150);
Insert Into Voorraad Values (451, 457, 120, 175);
Insert Into Voorraad Values (451, 458,   0, 100);
Insert Into Voorraad Values (452, 123,  35,  15);
Insert Into Voorraad Values (452, 456,  80,  50);
Insert Into Voorraad Values (453, 459,  95,  70);
Insert Into Voorraad Values (454, 456, 555,  90);
Insert Into Voorraad Values (454, 459,  60, 100);
Insert Into Voorraad Values (455, 456, 300, 150);
Insert Into Voorraad Values (455, 457, 500, 100);
Insert Into Voorraad Values (455, 458, 499, 100);
Insert Into Voorraad Values (455, 459,   5,  80);


Create Table Bestelling
      (bestnr           int(5)       ,
       klantnr          int(5)       ,
       fabnr            int(5)       ,
       datum            date            ,
       Primary Key(bestnr)                      ,
       Foreign Key(klantnr) References Klant    ,
       Foreign Key(fabnr) References Fabriek   );

Insert Into Bestelling Values (121, 121, 124, '16-apr-2003');
Insert Into Bestelling Values (122, 123, 123, '25-aug-2003');
Insert Into Bestelling Values (123, 124, 122, '17-oct-2003');
Insert Into Bestelling Values (124, 122, 121, '04-may-2003');
Insert Into Bestelling Values (125, 123, 455, '19-jul-2003');
Insert Into Bestelling Values (451, 125, 452, '25-apr-2003');
Insert Into Bestelling Values (452, 126, 451, '02-aug-2003');
Insert Into Bestelling Values (453, 121, 122, '17-oct-2003');
Insert Into Bestelling Values (454, 124, 454, '04-may-2003');
Insert Into Bestelling Values (455, 126, 455, '19-jul-2003');
Insert Into Bestelling Values (456, 121, 455, '10-oct-2003');


Create Table BesteldArtikel
      (bestnr           int(5)       ,
       artnr            int(5)       ,
       aantal           int(5)       ,
       bestelprijs      decimal(6, 2)    ,
       Primary Key(bestnr, artnr)               ,
       Foreign Key(bestnr) References Bestelling,
       Foreign Key(artnr) References Artikel   );

Insert Into BesteldArtikel Values (121, 124,  200,  4.50);
Insert Into BesteldArtikel Values (122, 123,  150,  3.50);
Insert Into BesteldArtikel Values (122, 124,   75,  4.75);
insert Into BesteldArtikel Values (122, 456,   50,  9.50);
Insert Into BesteldArtikel Values (123, 121,  200,  2.75);
Insert Into BesteldArtikel Values (123, 122,  100,  2.00);
Insert Into BesteldArtikel Values (123, 123,  100,  3.50);
Insert Into BesteldArtikel Values (123, 124,   50,  5.00);
Insert Into BesteldArtikel Values (123, 451, 1000,  0.50);
Insert Into BesteldArtikel Values (124, 122,  300,  2.00);
Insert Into BesteldArtikel Values (124, 124,  100,  5.00);
Insert Into BesteldArtikel Values (125, 121,  200,  2.75);
Insert Into BesteldArtikel Values (125, 122,  300,  1.75);
insert Into BesteldArtikel Values (125, 451,  400,  0.50);
Insert Into BesteldArtikel Values (451, 123,  250,  3.25);
Insert Into BesteldArtikel Values (451, 456,   10,  9.75);
Insert Into BesteldArtikel Values (452, 121,   50,  2.90);
Insert Into BesteldArtikel Values (452, 457,   35, 22.50);
Insert Into BesteldArtikel Values (452, 458,  150,  8.50);
Insert Into BesteldArtikel Values (453, 121,  400,  2.50);
Insert Into BesteldArtikel Values (453, 123,  200,  3.25);
Insert Into BesteldArtikel Values (454, 121,   10,  2.50);
Insert Into BesteldArtikel Values (454, 456,  100, 11.00);
Insert Into BesteldArtikel Values (455, 458,    6, 10.00);
insert Into BesteldArtikel Values (455, 459,  300,  8.10);
Insert Into BesteldArtikel Values (456, 452,  100,  0.90);



