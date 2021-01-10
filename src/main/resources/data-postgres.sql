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

insert into users (id, active,email, name,password,phone_number,surname, address, city_id) values ('22793162-52d3-11eb-ae93-0242ac130002',true, 'example@example.com','Stefan','123','0600000','Stefic','Boracka 2','12793162-52d3-11eb-ae93-0242ac130002');
insert into users (id, active,email, name,password,phone_number,surname, address ,city_id) values ('23793162-52d3-11eb-ae93-0242ac130002',true, 'example1@example.com','Nikola','123','0600000','Stefic','Ustanicka 3','12793162-52d3-11eb-ae93-0242ac130002');

insert into patient (id, penalty) values ('22793162-52d3-11eb-ae93-0242ac130002',0);
insert into patient (id, penalty) values ('23793162-52d3-11eb-ae93-0242ac130002',0);
