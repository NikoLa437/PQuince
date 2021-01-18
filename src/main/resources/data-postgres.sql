insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude) values ('22793162-52d3-11eb-ae93-0242ac130002',true, 'example@example.com','Stefan','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Stefic','grad Novi Sad','Serbia','Ulica Novosadskog sajma',45.254488,19.827929);
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude) values ('23793162-52d3-11eb-ae93-0242ac130002',true, 'example1@example.com','Nikola','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Stefic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699);

insert into patient (id, penalty, points, loyality_category) values ('22793162-52d3-11eb-ae93-0242ac130002',0,2, 'REGULAR');
insert into patient (id, penalty, points, loyality_category) values ('23793162-52d3-11eb-ae93-0242ac130002',0,2, 'REGULAR');

insert into allergen (id, name) values ('3b27db4c-48bb-4f78-a3da-745d9c9458ba', 'Penicilin');
insert into allergen (id, name) values ('062d319f-f393-464c-98fb-b3db29e77626', 'Amoxicilin');
insert into allergen (id, name) values ('7512234e-c9f9-44bd-94be-4f49c32a342e', 'Ibuprofen');
insert into allergen (id, name) values ('b7f0e740-58c6-11eb-ae93-0242ac130002', 'Aspirin');
insert into allergen (id, name) values ('e4406e24-58c6-11eb-ae93-0242ac130002', 'Anticonvulsants');
insert into allergen (id, name) values ('becbdcae-58c7-11eb-ae93-0242ac130002', 'Hydroxyzine');
insert into allergen (id, name) values ('c38ee8f8-58c7-11eb-ae93-0242ac130002', 'Doxylamine');
insert into allergen (id, name) values ('c7bd9b04-58c7-11eb-ae93-0242ac130002', 'Acthar');


insert into patient_allergen (patient_id, allergen_id) values('22793162-52d3-11eb-ae93-0242ac130002','3b27db4c-48bb-4f78-a3da-745d9c9458ba');

insert into pharmacy (id, name, description, city, country, street, latitude, longitude) values ('cafeddee-56cb-11eb-ae93-0242ac130002', 'Benu', 'Benu apoteka','grad Novi Sad','Serbia','Ulica Novosadskog sajma',45.254488,19.827929);
insert into pharmacy (id, name, description, city, country, street, latitude, longitude) values ('cafeddee-56cb-11eb-ae93-0242ac111002', 'Ivancic i sinovi', 'Ivancic i sinovi','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699);
insert into pharmacy (id, name, description, city, country, street, latitude, longitude) values ('cafeddee-56cb-11eb-ae93-0242ac130202', 'Zdravlje', 'Zdravlje apoteka','gradska opština Zemun','Serbia','Šilerova ulica',44.845752,20.387903);
insert into pharmacy (id, name, description, city, country, street, latitude, longitude) values ('cafeddee-56cb-11eb-ae93-0242ac131302', 'Kopriva', 'Kopriva apoteka','grad Novi Sad','Serbia','Ulica Novosadskog sajma',45.254488,19.827929);

insert into pharmacy_feedback (patient_id, pharmacy_id, date, grade, comment) values ('22793162-52d3-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac130002', '2020-03-03', 1, 'Uzas uzas');
insert into pharmacy_feedback (patient_id, pharmacy_id, date, grade, comment) values ('23793162-52d3-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac130002', '2020-03-03', 5, 'Sve naj naj');
insert into pharmacy_feedback (patient_id, pharmacy_id, date, grade, comment) values ('22793162-52d3-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac130202', '2020-03-03', 2, 'Solidno');

insert into manufacturer (id, name) values ('20ddef44-5838-11eb-ae93-0242ac130002', 'Hemofarm');
insert into manufacturer (id, name) values ('574c3c20-5838-11eb-ae93-0242ac130002', 'Galenika');
insert into manufacturer (id, name) values ('5c49beb4-5838-11eb-ae93-0242ac130002', 'Ekosan');
insert into manufacturer (id, name) values ('61297672-5838-11eb-ae93-0242ac130002', 'Hemotehna');

insert into drug (id, code, name) values ('dac2b818-5838-11eb-ae93-0242ac130002', '1162531', 'Ibuprofen');
insert into drug (id, code, name) values ('2c797174-5839-11eb-ae93-0242ac130002', '1162513', 'Ibuprofen');
insert into drug (id, code, name) values ('2fe1cd8e-5839-11eb-ae93-0242ac130002', '3162089', 'Ibuprofen');


insert into drug_instance(drug_format, drug_instance_name, loyality_points, on_reciept, quantity, recommended_amount, side_effects, manufacturer_id, id) values('CAPSULE', 'Brufen',1, false, 500, '3x1 na dan', 'Nema nezeljenih dejstava', '20ddef44-5838-11eb-ae93-0242ac130002','2fe1cd8e-5839-11eb-ae93-0242ac130002');
insert into drug_instance(drug_format, drug_instance_name, loyality_points, on_reciept, quantity, recommended_amount, side_effects, manufacturer_id, id) values('CAPSULE', 'Rapidol',1, false, 600, '3x1 na dan', 'Nema nezeljenih dejstava, tako kazu', '574c3c20-5838-11eb-ae93-0242ac130002','2c797174-5839-11eb-ae93-0242ac130002');
insert into drug_instance(drug_format, drug_instance_name, loyality_points, on_reciept, quantity, recommended_amount, side_effects, manufacturer_id, id) values('CAPSULE', 'Ibumax',1, false, 400, '2x1 na dan', 'Nema nezeljenih dejstava', '61297672-5838-11eb-ae93-0242ac130002','dac2b818-5838-11eb-ae93-0242ac130002');

insert into drug_replacement(drug_id, replacement_drug_id) values ('dac2b818-5838-11eb-ae93-0242ac130002', '2c797174-5839-11eb-ae93-0242ac130002');
insert into drug_replacement(drug_id, replacement_drug_id) values ('dac2b818-5838-11eb-ae93-0242ac130002', '2fe1cd8e-5839-11eb-ae93-0242ac130002');
insert into drug_replacement(drug_id, replacement_drug_id) values ('2c797174-5839-11eb-ae93-0242ac130002', 'dac2b818-5838-11eb-ae93-0242ac130002');
insert into drug_replacement(drug_id, replacement_drug_id) values ('2c797174-5839-11eb-ae93-0242ac130002', '2fe1cd8e-5839-11eb-ae93-0242ac130002');
insert into drug_replacement(drug_id, replacement_drug_id) values ('2fe1cd8e-5839-11eb-ae93-0242ac130002', '2c797174-5839-11eb-ae93-0242ac130002');
insert into drug_replacement(drug_id, replacement_drug_id) values ('2fe1cd8e-5839-11eb-ae93-0242ac130002', 'dac2b818-5838-11eb-ae93-0242ac130002');

insert into drug_price_for_pharmacy(drug_instance_id, pharmacy_id, date_from, date_to, price) values ('dac2b818-5838-11eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130002','2020-11-11','2021-02-02', 340);
insert into drug_price_for_pharmacy(drug_instance_id, pharmacy_id, date_from, date_to, price) values ('dac2b818-5838-11eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac111002','2020-11-11','2021-02-02', 399.99);
insert into drug_price_for_pharmacy(drug_instance_id, pharmacy_id, date_from, date_to, price) values ('dac2b818-5838-11eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130202','2020-11-11','2021-02-02', 320);


insert into drug_storage(drug_instance_id, pharmacy_id, count) values ('dac2b818-5838-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac130002', 24);
insert into drug_storage(drug_instance_id, pharmacy_id, count) values ('dac2b818-5838-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac130202', 55);


insert into drug_reservation(id, amount, drug_peace_price, end_date, reservation_status, start_date, drug_instance_id, patient_id, pharmacy_id) values ('f96fe75e-59a5-11eb-ae93-0242ac130002', 3, 340, '2021-01-18 17:04:03',  'ACTIVE', '2021-01-16 17:04:03', 'dac2b818-5838-11eb-ae93-0242ac130002', '22793162-52d3-11eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130002');
