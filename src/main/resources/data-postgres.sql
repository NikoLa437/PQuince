insert into country (id, name) values ('9f015ce6-52d2-11eb-ae93-0242ac130002','Srbija');
insert into country (id, name) values ('e0cbe3c6-52d2-11eb-ae93-0242ac130002','Bosna i Hercegovina');
insert into country (id, name) values ('e7ba2a3a-52d2-11eb-ae93-0242ac130002','Nemacka');
insert into country (id, name) values ('eec1937c-52d2-11eb-ae93-0242ac130002','Francuska');


insert into city (id, name, country_id) values ('12793162-52d3-11eb-ae93-0242ac130002','Beograd','9f015ce6-52d2-11eb-ae93-0242ac130002');
insert into city (id, name, country_id) values ('12793162-52d3-11eb-ae93-0242ac130001','Novi Sad','9f015ce6-52d2-11eb-ae93-0242ac130002');
insert into city (id, name, country_id) values ('12793162-52d3-11eb-ae93-0242ac130012','Sabac','9f015ce6-52d2-11eb-ae93-0242ac130002');
insert into city (id, name, country_id) values ('12793162-52d3-11eb-a193-0242ac130002','Sarajevo','e0cbe3c6-52d2-11eb-ae93-0242ac130002');
insert into city (id, name, country_id) values ('12293162-52d3-11eb-ae93-0242ac130002','Berlin','e7ba2a3a-52d2-11eb-ae93-0242ac130002');
insert into city (id, name, country_id) values ('12793162-52d3-11eb-ae93-0252ac130002','Pariz','eec1937c-52d2-11eb-ae93-0242ac130002');
insert into city (id, name, country_id) values ('12793162-52d3-11eb-ae93-0252ac130102','Lion','eec1937c-52d2-11eb-ae93-0242ac130002');

insert into users (id, active,email, name,password,phone_number,surname, address, city_id) values ('22793162-52d3-11eb-ae93-0242ac130002',true, 'example@example.com','Stefan','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Stefic','Boracka 2','12793162-52d3-11eb-ae93-0242ac130002');
insert into users (id, active,email, name,password,phone_number,surname, address ,city_id) values ('23793162-52d3-11eb-ae93-0242ac130002',true, 'example1@example.com','Nikola','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Stefic','Ustanicka 3','12793162-52d3-11eb-ae93-0242ac130002');

insert into patient (id, penalty, points, loyality_category) values ('22793162-52d3-11eb-ae93-0242ac130002',0,2, 'REGULAR');
insert into patient (id, penalty, points, loyality_category) values ('23793162-52d3-11eb-ae93-0242ac130002',0,2, 'REGULAR');

insert into allergen (id, name) values ('3b27db4c-48bb-4f78-a3da-745d9c9458ba', 'Penicilin');
insert into allergen (id, name) values ('062d319f-f393-464c-98fb-b3db29e77626', 'Amoxicilin');
insert into allergen (id, name) values ('7512234e-c9f9-44bd-94be-4f49c32a342e', 'Ibuprofen');

insert into patient_allergen (patient_id, allergen_id) values('22793162-52d3-11eb-ae93-0242ac130002','3b27db4c-48bb-4f78-a3da-745d9c9458ba');

insert into pharmacy (id, name, address, description) values ('cafeddee-56cb-11eb-ae93-0242ac130002', 'Benu', 'Novosadskog sajma 2', 'Benu apoteka');
insert into pharmacy (id, name, address, description) values ('cafeddee-56cb-11eb-ae93-0242ac111002', 'Ivancic i sinovi', 'Novosadskog sajma 32', 'Ivancic i sinovi');
insert into pharmacy (id, name, address, description) values ('cafeddee-56cb-11eb-ae93-0242ac130202', 'Zdravlje', 'Sremska 3', 'Zdravlje apoteka');
insert into pharmacy (id, name, address, description) values ('cafeddee-56cb-11eb-ae93-0242ac131302', 'Kopriva', 'Srpska 233', 'Kopriva apoteka');

insert into pharmacy_feedback (patient_id, pharmacy_id, date, grade, comment) values ('22793162-52d3-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac130002', '2020-03-03', 1, 'Uzas uzas');
insert into pharmacy_feedback (patient_id, pharmacy_id, date, grade, comment) values ('23793162-52d3-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac130002', '2020-03-03', 5, 'Sve naj naj');
insert into pharmacy_feedback (patient_id, pharmacy_id, date, grade, comment) values ('22793162-52d3-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac130202', '2020-03-03', 2, 'Solidno');