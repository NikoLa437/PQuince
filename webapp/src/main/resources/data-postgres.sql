insert into authority (id, name) values ('7852aa5e-7040-4d99-8255-537a0b226c75','ROLE_PATIENT');
insert into authority (id, name) values ('563e9925-cff6-42b7-99fa-6b1235f67655','ROLE_SYSADMIN');
insert into authority (id, name) values ('09af8857-2f78-4eec-970f-059d3ed4589f','ROLE_DERMATHOLOGIST');
insert into authority (id, name) values ('ef9a3723-a72e-44ec-83ac-9d748fd0240f','ROLE_SUPPLIER');
insert into authority (id, name) values ('a1e3bac1-6093-4705-b835-eed75c3e5f21','ROLE_PHARMACIST');
insert into authority (id, name) values ('ea16767c-2c1f-49fb-ac98-c7739c0036e8','ROLE_PHARMACYADMIN');

--password - 123
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude, version) values ('22793162-52d3-11eb-ae93-0242ac130002',true, 'example@example.com','Stefan','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Stefic','grad Novi Sad','Serbia','Ulica Novosadskog sajma',45.254488,19.827929, 1);
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude, version) values ('23793162-52d3-11eb-ae93-0242ac130002',true, 'example1@example.com','Nikola','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Stefic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699, 1);

insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude, version) values ('11355678-52d3-11eb-ae93-0242ac130002',true, 'example5@example.com','Jelena','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Stefic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699, 1);
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude, version) values ('22345678-52d3-11eb-ae93-0242ac130002',true, 'example6@example.com','Dusan','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Stefic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699, 1);
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude, version) values ('25345678-52d3-11eb-ae93-0242ac130002',true, 'example8@example.com','Nemanja','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Stefic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699, 1);

insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude, version) values ('25345278-52d3-11eb-ae93-0242ac130002',true, 'exampla11@example.com','Vidoje','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Stefic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699, 1);
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude, version) values ('25345278-52d3-12eb-ac93-0242ac130002',true, 'exampla21@example.com','Sredoje','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Mikic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699, 1);
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude, version) values ('25345278-52d3-13eb-ae93-0242ac130002',true, 'exampla31@example.com','Radoje','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Bikic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699, 1);
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude, version) values ('9ba98d47-1a8a-4ae1-b109-af7b56e94788',true, 'pharmacyadmin@example.com','Petar','$2a$10$Amd0M3ETJ/9hhxh5zJebvOf9Bx33aDyLS2qmCWbIksaHTS1h9DSAy','0624932342','Dusanic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699, 1);
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude, version) values ('44444d47-1a8a-4ae1-b109-af7b56e94788',true, 'admin@example.com','Petar','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0624932342','Dusanic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699, 1);
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude, version) values ('55555d47-1a8a-4ae1-b109-af7b56e94788',true, 'supplier@example.com','Petar','$2y$10$/vLDfCyu.2agCDFchcXnM.EURgHs7f.aoKoLStnzWKq19okEphrpS','0624932342','Dusanic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699, 1);
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude, version) values ('66666d47-1a8a-4ae1-b109-af7b56e94788',true, 'supplier2@example.com','Mika','$2a$10$Amd0M3ETJ/9hhxh5zJebvOf9Bx33aDyLS2qmCWbIksaHTS1h9DSAy','0624932342','Dusanic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699, 1);

insert into patient (id, penalty, points) values ('22793162-52d3-11eb-ae93-0242ac130002',0,5);
insert into patient (id, penalty, points) values ('23793162-52d3-11eb-ae93-0242ac130002',0,2);

insert into user_authority (user_id, authority_id) values ('22793162-52d3-11eb-ae93-0242ac130002', '7852aa5e-7040-4d99-8255-537a0b226c75');
insert into user_authority (user_id, authority_id) values ('23793162-52d3-11eb-ae93-0242ac130002', '7852aa5e-7040-4d99-8255-537a0b226c75');
insert into user_authority (user_id, authority_id) values ('11355678-52d3-11eb-ae93-0242ac130002', '09af8857-2f78-4eec-970f-059d3ed4589f');
insert into user_authority (user_id, authority_id) values ('22345678-52d3-11eb-ae93-0242ac130002', '09af8857-2f78-4eec-970f-059d3ed4589f');
insert into user_authority (user_id, authority_id) values ('25345678-52d3-11eb-ae93-0242ac130002', '09af8857-2f78-4eec-970f-059d3ed4589f');
insert into user_authority (user_id, authority_id) values ('9ba98d47-1a8a-4ae1-b109-af7b56e94788', 'ea16767c-2c1f-49fb-ac98-c7739c0036e8');
insert into user_authority (user_id, authority_id) values ('44444d47-1a8a-4ae1-b109-af7b56e94788', '563e9925-cff6-42b7-99fa-6b1235f67655');
insert into user_authority (user_id, authority_id) values ('55555d47-1a8a-4ae1-b109-af7b56e94788', 'ef9a3723-a72e-44ec-83ac-9d748fd0240f');
insert into user_authority (user_id, authority_id) values ('66666d47-1a8a-4ae1-b109-af7b56e94788', 'ef9a3723-a72e-44ec-83ac-9d748fd0240f');

insert into user_authority (user_id, authority_id) values ('25345278-52d3-11eb-ae93-0242ac130002', 'a1e3bac1-6093-4705-b835-eed75c3e5f21');

insert into allergen (id, name) values ('3b27db4c-48bb-4f78-a3da-745d9c9458ba', 'Penicilin');
insert into allergen (id, name) values ('062d319f-f393-464c-98fb-b3db29e77626', 'Amoxicilin');
insert into allergen (id, name) values ('7512234e-c9f9-44bd-94be-4f49c32a342e', 'Ibuprofen');
insert into allergen (id, name) values ('b7f0e740-58c6-11eb-ae93-0242ac130002', 'Aspirin');
insert into allergen (id, name) values ('e4406e24-58c6-11eb-ae93-0242ac130002', 'Anticonvulsants');
insert into allergen (id, name) values ('becbdcae-58c7-11eb-ae93-0242ac130002', 'Hydroxyzine');
insert into allergen (id, name) values ('c38ee8f8-58c7-11eb-ae93-0242ac130002', 'Doxylamine');
insert into allergen (id, name) values ('c7bd9b04-58c7-11eb-ae93-0242ac130002', 'Acthar');


insert into patient_allergen (patient_id, allergen_id) values('22793162-52d3-11eb-ae93-0242ac130002','3b27db4c-48bb-4f78-a3da-745d9c9458ba');

insert into pharmacy (id, name, description, city, country, street, latitude, longitude, consultation_price) values ('cafeddee-56cb-11eb-ae93-0242ac130002', 'Benu', 'Benu apoteka','grad Novi Sad','Serbia','Ulica Novosadskog sajma',45.254488,19.827929, 450);
insert into pharmacy (id, name, description, city, country, street, latitude, longitude, consultation_price) values ('cafeddee-56cb-11eb-ae93-0242ac111002', 'Ivancic i sinovi', 'Ivancic i sinovi','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699, 729);
insert into pharmacy (id, name, description, city, country, street, latitude, longitude, consultation_price) values ('cafeddee-56cb-11eb-ae93-0242ac130202', 'Zdravlje', 'Zdravlje apoteka','gradska opština Zemun','Serbia','Šilerova ulica',44.845752,20.387903, 400);
insert into pharmacy (id, name, description, city, country, street, latitude, longitude, consultation_price) values ('cafeddee-56cb-11eb-ae93-0242ac131302', 'Kopriva', 'Kopriva apoteka','grad Novi Sad','Serbia','Ulica Novosadskog sajma',45.254488,19.827929, 650);

insert into staff (id, staff_type) values ('11355678-52d3-11eb-ae93-0242ac130002','DERMATOLOGIST');
insert into staff (id, staff_type) values ('22345678-52d3-11eb-ae93-0242ac130002','DERMATOLOGIST');
insert into staff (id, staff_type) values ('25345678-52d3-11eb-ae93-0242ac130002','DERMATOLOGIST');
insert into staff (id, staff_type) values ('25345278-52d3-11eb-ae93-0242ac130002','PHARMACIST');
insert into staff (id, staff_type) values ('25345278-52d3-12eb-ac93-0242ac130002','PHARMACIST');
insert into staff (id, staff_type) values ('25345278-52d3-13eb-ae93-0242ac130002','PHARMACIST');
insert into staff (id, staff_type) values ('9ba98d47-1a8a-4ae1-b109-af7b56e94788','PHARMACYADMIN');
insert into staff (id, staff_type) values ('44444d47-1a8a-4ae1-b109-af7b56e94788','SYSADMIN');
insert into staff (id, staff_type) values ('55555d47-1a8a-4ae1-b109-af7b56e94788','SUPPLIER');
insert into staff (id, staff_type) values ('66666d47-1a8a-4ae1-b109-af7b56e94788','SUPPLIER');

insert into dermatologist (id) values ('11355678-52d3-11eb-ae93-0242ac130002');
insert into dermatologist (id) values ('22345678-52d3-11eb-ae93-0242ac130002');
insert into dermatologist (id) values ('25345678-52d3-11eb-ae93-0242ac130002');

insert into pharmacist (id, pharmacy_id) values ('25345278-52d3-11eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130002');
insert into pharmacist (id, pharmacy_id) values ('25345278-52d3-12eb-ac93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130202');
insert into pharmacist (id, pharmacy_id) values ('25345278-52d3-13eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130202');

insert into dermatologist_pharmacy (dermatologist_id, pharmacy_id) values ('11355678-52d3-11eb-ae93-0242ac130002' ,'cafeddee-56cb-11eb-ae93-0242ac130002');
insert into dermatologist_pharmacy (dermatologist_id, pharmacy_id) values ('22345678-52d3-11eb-ae93-0242ac130002' ,'cafeddee-56cb-11eb-ae93-0242ac130202');
insert into dermatologist_pharmacy (dermatologist_id, pharmacy_id) values ('25345678-52d3-11eb-ae93-0242ac130002' ,'cafeddee-56cb-11eb-ae93-0242ac130202');


insert into pharmacy_feedback (patient_id, pharmacy_id, date, grade) values ('22793162-52d3-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac130002', '2020-03-03', 1);
insert into pharmacy_feedback (patient_id, pharmacy_id, date, grade) values ('23793162-52d3-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac130002', '2020-03-03', 5);
insert into pharmacy_feedback (patient_id, pharmacy_id, date, grade) values ('22793162-52d3-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac130202', '2020-03-03', 2);

insert into manufacturer (id, name) values ('20ddef44-5838-11eb-ae93-0242ac130002', 'Hemofarm');
insert into manufacturer (id, name) values ('574c3c20-5838-11eb-ae93-0242ac130002', 'Galenika');
insert into manufacturer (id, name) values ('5c49beb4-5838-11eb-ae93-0242ac130002', 'Ekosan');
insert into manufacturer (id, name) values ('61297672-5838-11eb-ae93-0242ac130002', 'Hemotehna');

insert into drug (id, code, name) values ('dac2b818-5838-11eb-ae93-0242ac130002', '1162531', 'Ibuprofen');
insert into drug (id, code, name) values ('2c797174-5839-11eb-ae93-0242ac130002', '1162513', 'Ibuprofen');
insert into drug (id, code, name) values ('2fe1cd8e-5839-11eb-ae93-0242ac130002', '3162089', 'Ibuprofen');
insert into drug (id, code, name) values ('aecf0eac-68cb-11eb-9f52-838764dae934', '3162001', 'Ibuprofen');  -- blokmax
insert into drug (id, code, name) values ('b4abdfc6-68cb-11eb-a102-d7374f9803ba', '3162325', 'Ibuprofen');  -- ibalgin
insert into drug (id, code, name) values ('1fe1cd8e-5839-11eb-ae93-0242ac130002', '3162002', 'Ibuprofen');

insert into drug_instance(drug_format, drug_instance_name, loyality_points, on_reciept, quantity, recommended_amount, side_effects, manufacturer_id, id, drug_kind) values('CAPSULE', 'Brufen',1, false, 500, '3x1 na dan', 'Nema nezeljenih dejstava', '20ddef44-5838-11eb-ae93-0242ac130002','2fe1cd8e-5839-11eb-ae93-0242ac130002', 'HUMAN');
insert into drug_instance(drug_format, drug_instance_name, loyality_points, on_reciept, quantity, recommended_amount, side_effects, manufacturer_id, id, drug_kind) values('CAPSULE', 'Rapidol',1, false, 600, '3x1 na dan', 'Nema nezeljenih dejstava, tako kazu', '574c3c20-5838-11eb-ae93-0242ac130002','2c797174-5839-11eb-ae93-0242ac130002', 'HUMAN');
insert into drug_instance(drug_format, drug_instance_name, loyality_points, on_reciept, quantity, recommended_amount, side_effects, manufacturer_id, id, drug_kind) values('CAPSULE', 'Ibumax',1, false, 400, '2x1 na dan', 'Nema nezeljenih dejstava', '61297672-5838-11eb-ae93-0242ac130002','dac2b818-5838-11eb-ae93-0242ac130002', 'HUMAN');
insert into drug_instance(drug_format, drug_instance_name, loyality_points, on_reciept, quantity, recommended_amount, side_effects, manufacturer_id, id, drug_kind) values('CAPSULE', 'BlokMax',1, false, 500, '3x1 na dan', 'Nema nezeljenih dejstava', '5c49beb4-5838-11eb-ae93-0242ac130002','aecf0eac-68cb-11eb-9f52-838764dae934', 'HUMAN'); 
insert into drug_instance(drug_format, drug_instance_name, loyality_points, on_reciept, quantity, recommended_amount, side_effects, manufacturer_id, id, drug_kind) values('CAPSULE', 'IBALGIN',1, false, 400, '2x1 na dan', 'Nema nezeljenih dejstava', '574c3c20-5838-11eb-ae93-0242ac130002','b4abdfc6-68cb-11eb-a102-d7374f9803ba', 'HUMAN');
insert into drug_instance(drug_format, drug_instance_name, loyality_points, on_reciept, quantity, recommended_amount, side_effects, manufacturer_id, id, drug_kind) values('CAPSULE', 'Blokmax',1, false, 500, '2x1 na dan', 'Nema nezeljenih dejstava', '61297672-5838-11eb-ae93-0242ac130002','1fe1cd8e-5839-11eb-ae93-0242ac130002', 'BIOLOGICAL');

insert into drug_allergen (drug_id, allergen_id) values ('2c797174-5839-11eb-ae93-0242ac130002','062d319f-f393-464c-98fb-b3db29e77626');

insert into patient_allergen (patient_id, allergen_id) values ('22793162-52d3-11eb-ae93-0242ac130002','062d319f-f393-464c-98fb-b3db29e77626');


insert into drug_replacement(drug_id, replacement_drug_id) values ('dac2b818-5838-11eb-ae93-0242ac130002', '2c797174-5839-11eb-ae93-0242ac130002');
insert into drug_replacement(drug_id, replacement_drug_id) values ('dac2b818-5838-11eb-ae93-0242ac130002', '2fe1cd8e-5839-11eb-ae93-0242ac130002');
insert into drug_replacement(drug_id, replacement_drug_id) values ('2c797174-5839-11eb-ae93-0242ac130002', 'dac2b818-5838-11eb-ae93-0242ac130002');
insert into drug_replacement(drug_id, replacement_drug_id) values ('2c797174-5839-11eb-ae93-0242ac130002', '2fe1cd8e-5839-11eb-ae93-0242ac130002');
insert into drug_replacement(drug_id, replacement_drug_id) values ('2fe1cd8e-5839-11eb-ae93-0242ac130002', '2c797174-5839-11eb-ae93-0242ac130002');
insert into drug_replacement(drug_id, replacement_drug_id) values ('2fe1cd8e-5839-11eb-ae93-0242ac130002', 'dac2b818-5838-11eb-ae93-0242ac130002');

insert into drug_price_for_pharmacy(id,drug_instance_id, pharmacy_id, date_from, date_to, price) values ('ffc6bec4-6964-11eb-9439-0242ac130123','dac2b818-5838-11eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130002','2020-11-11','2021-02-22', 340);
insert into drug_price_for_pharmacy(id,drug_instance_id, pharmacy_id, date_from, date_to, price) values ('ffc6bec4-6964-11eb-9439-0242ac130124','dac2b818-5838-11eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac111002','2020-11-11','2021-02-22', 399.99);
insert into drug_price_for_pharmacy(id,drug_instance_id, pharmacy_id, date_from, date_to, price) values ('ffc6bec4-6964-11eb-9439-0242ac130125','dac2b818-5838-11eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130202','2020-11-11','2021-02-22', 320);
insert into drug_price_for_pharmacy(id,drug_instance_id, pharmacy_id, date_from, date_to, price) values ('ffc6bec4-6964-11eb-9439-0242ac130126','dac2b818-5838-11eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac131302','2020-11-11','2021-02-22', 420);
insert into drug_price_for_pharmacy(id,drug_instance_id, pharmacy_id, date_from, date_to, price) values ('ffc6bec4-6964-11eb-9439-0242ac130127','2c797174-5839-11eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac111002','2020-11-11','2021-02-22', 999);
insert into drug_price_for_pharmacy(id,drug_instance_id, pharmacy_id, date_from, date_to, price) values ('ffc6bec4-6964-11eb-9439-0242ac130128','aecf0eac-68cb-11eb-9f52-838764dae934','cafeddee-56cb-11eb-ae93-0242ac130002','2020-11-11','2021-02-22', 230);
insert into drug_price_for_pharmacy(id,drug_instance_id, pharmacy_id, date_from, date_to, price) values ('ffc6bec4-6964-11eb-9439-0242ac130129','b4abdfc6-68cb-11eb-a102-d7374f9803ba','cafeddee-56cb-11eb-ae93-0242ac130002','2020-11-11','2021-02-22', 550);


insert into drug_storage(drug_instance_id, pharmacy_id, count, version) values ('dac2b818-5838-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac130002', 24, 1);
insert into drug_storage(drug_instance_id, pharmacy_id, count, version) values ('dac2b818-5838-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac130202', 55, 1);
insert into drug_storage(drug_instance_id, pharmacy_id, count, version) values ('dac2b818-5838-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac131302', 55, 1);
insert into drug_storage(drug_instance_id, pharmacy_id, count, version) values ('2c797174-5839-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac111002', 40, 1);
insert into drug_storage(drug_instance_id, pharmacy_id, count, version) values ('aecf0eac-68cb-11eb-9f52-838764dae934', 'cafeddee-56cb-11eb-ae93-0242ac130002', 30, 1);
insert into drug_storage(drug_instance_id, pharmacy_id, count, version) values ('b4abdfc6-68cb-11eb-a102-d7374f9803ba', 'cafeddee-56cb-11eb-ae93-0242ac130002', 20, 1);

insert into supplier_drug_storage(drug_instance_id, count, version) values ('dac2b818-5838-11eb-ae93-0242ac130002', 20, 1);
insert into supplier_drug_storage(drug_instance_id, count, version) values ('2c797174-5839-11eb-ae93-0242ac130002', 40, 1);
insert into supplier_drug_storage(drug_instance_id, count, version) values ('2fe1cd8e-5839-11eb-ae93-0242ac130002', 60, 1);
insert into supplier_drug_storage(drug_instance_id, count, version) values ('aecf0eac-68cb-11eb-9f52-838764dae934', 70, 1);
insert into supplier_drug_storage(drug_instance_id, count, version) values ('1fe1cd8e-5839-11eb-ae93-0242ac130002', 60, 1);

insert into drug_reservation(id, amount, drug_peace_price, end_date, reservation_status, start_date, drug_instance_id, patient_id, pharmacy_id) values ('f96fe75e-59a5-11eb-ae93-0242ac130002', 3, 340, '2021-01-18 17:04:03',  'PROCESSED', '2021-01-16 17:04:03', 'dac2b818-5838-11eb-ae93-0242ac130002', '22793162-52d3-11eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130002');
insert into drug_reservation(id, amount, drug_peace_price, end_date, reservation_status, start_date, drug_instance_id, patient_id, pharmacy_id) values ('f46fe75e-59a5-11eb-ae93-0242ac130002', 5, 340, '2021-02-18 17:04:03',  'ACTIVE', '2021-01-16 17:04:03', 'dac2b818-5838-11eb-ae93-0242ac130002', '22793162-52d3-11eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130002');

insert into staff_feedback (staff_id, patient_id, date, grade) values ('22345678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002','2020-03-03',4);
insert into staff_feedback (staff_id, patient_id, date, grade) values ('22345678-52d3-11eb-ae93-0242ac130002','23793162-52d3-11eb-ae93-0242ac130002','2020-03-03',2);
insert into staff_feedback (staff_id, patient_id, date, grade) values ('11355678-52d3-11eb-ae93-0242ac130002','23793162-52d3-11eb-ae93-0242ac130002','2020-03-03',3);
insert into staff_feedback (staff_id, patient_id, date, grade) values ('11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002','2020-03-03',1);
insert into staff_feedback (staff_id, patient_id, date, grade) values ('25345678-52d3-11eb-ae93-0242ac130002','23793162-52d3-11eb-ae93-0242ac130002','2020-03-03',5);

insert into staff_feedback (staff_id, patient_id, date, grade) values ('25345278-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002','2020-03-03',2);
insert into staff_feedback (staff_id, patient_id, date, grade) values ('25345278-52d3-11eb-ae93-0242ac130002','23793162-52d3-11eb-ae93-0242ac130002','2020-03-03',5);
insert into staff_feedback (staff_id, patient_id, date, grade) values ('25345278-52d3-13eb-ae93-0242ac130002','23793162-52d3-11eb-ae93-0242ac130002','2020-03-03',3);

insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('2ba86116-5a40-11eb-ae93-0242ac130002','FINISHED','EXAMINATION', '2021-01-11 11:00:00', '2021-01-11 11:30:00', 1450, 1450, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('2ba86116-5c40-11eb-ae93-0242ac130002','FINISHED','EXAMINATION', '2021-01-14 11:00:00', '2021-01-14 11:15:00', 1400, 1400, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('2bc86116-5c40-11eb-ae93-0242ac130002','SCHEDULED','EXAMINATION', '2021-02-15 11:00:00', '2021-02-15 11:16:00', 1800, 1800, 'cafeddee-56cb-11eb-ae93-0242ac130002', '25345678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('2bc86117-5c40-11eb-ae93-0242ac130002','SCHEDULED','EXAMINATION', '2021-02-21 11:00:00', '2021-02-21 11:16:00', 1800, 1800, 'cafeddee-56cb-11eb-ae93-0242ac130002', '22345678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);

insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, staff_id, pharmacy_id, patient_id, version) values ('2bc86117-5c40-11ab-ae93-0242ac130002','SCHEDULED','CONSULTATION', '2021-02-17 11:00:00', '2021-02-17 11:15:00', 450, 450, '25345278-52d3-11eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, staff_id, pharmacy_id, patient_id, version) values ('2bc86117-5c40-12ab-ae93-0242ac130002','SCHEDULED','CONSULTATION', '2021-02-18 11:00:00', '2021-02-18 11:15:00', 999, 999, '25345278-52d3-12eb-ac93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130202','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, staff_id, pharmacy_id, patient_id, version) values ('2bc86117-5c40-13ab-ae93-0242ac130002','SCHEDULED','CONSULTATION', '2021-02-19 11:00:00', '2021-02-19 11:15:00', 999, 999, '25345278-52d3-13eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130202','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, staff_id, pharmacy_id, patient_id, version) values ('2cc86117-5c40-13ab-ae93-0242ac130002','FINISHED','CONSULTATION', '2021-01-29 11:55:00', '2021-01-29 12:10:00', 999, 999, '25345278-52d3-12eb-ac93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130202','22793162-52d3-11eb-ae93-0242ac130002', 1);


insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('2ba86116-5f40-11eb-ae93-0242ac130002','SCHEDULED','EXAMINATION', '2021-02-11 11:00:00', '2021-02-11 11:30:00', 1400, 1400, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, version) values ('2ba86116-5f40-11eb-ae93-0242ac134002','CREATED','EXAMINATION', '2021-02-11 11:30:00', '2021-02-11 12:00:00', 1300, 1300, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, version) values ('2ca86116-5f40-11eb-ae93-0242ac134002','CREATED','EXAMINATION', '2021-02-13 11:30:00', '2021-02-13 12:00:00', 1500, 1500, 'cafeddee-56cb-11eb-ae93-0242ac130202', '22345678-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, version) values ('2da86116-5f40-11eb-ae93-0242ac134002','CREATED','EXAMINATION', '2021-02-13 11:30:00', '2021-02-13 12:00:00', 1500, 1500, 'cafeddee-56cb-11eb-ae93-0242ac130002', '25345678-52d3-11eb-ae93-0242ac130002', 1);

insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, version) values ('2da86116-5f41-13eb-ab93-0242ac134002','CREATED','EXAMINATION', '2021-02-23 11:30:00', '2021-02-23 12:00:00', 1500, 1500, 'cafeddee-56cb-11eb-ae93-0242ac130202', '22345678-52d3-11eb-ae93-0242ac130002', 1);

--appointments for statistics for Benu pharmacy

insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('699296ba-6c0e-11eb-9439-0242ac130002','FINISHED','EXAMINATION', '2020-01-17 11:00:00', '2020-01-17 11:30:00', 1400, 1400, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('003c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-01-25 11:00:00', '2020-01-25 11:30:00', 1400, 1400, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('004c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-03-15 11:00:00', '2020-03-15 11:30:00', 1500, 1500, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('005c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-03-17 11:00:00', '2020-03-17 11:30:00', 1500, 1000, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('006c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-04-15 11:00:00', '2020-04-15 11:30:00', 1400, 1300, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('007c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-04-17 11:00:00', '2020-04-17 11:30:00', 1400, 1200, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('008c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-05-19 11:00:00', '2020-05-19 11:30:00', 1400, 1200, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('009c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-05-21 11:00:00', '2020-05-21 11:30:00', 1400, 1200, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('010c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-06-15 11:00:00', '2020-06-15 11:30:00', 1700, 1200, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('011c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-06-16 11:00:00', '2020-06-16 11:30:00', 1400, 1300, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('012c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-06-17 11:00:00', '2020-06-17 11:30:00', 1400, 1300, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('013c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-07-15 11:00:00', '2020-07-15 11:30:00', 1800, 1200, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('014c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-07-18 11:00:00', '2020-07-18 11:30:00', 1600, 1200, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('015c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-08-15 11:00:00', '2020-08-15 11:30:00', 1500, 1000, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('016c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-08-16 11:00:00', '2020-08-16 11:30:00', 2500, 2000, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('017c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-08-17 11:00:00', '2020-08-17 11:30:00', 1400, 1200, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('018c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-10-15 11:00:00', '2020-10-15 11:30:00', 2000, 1500, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('019c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-10-16 11:00:00', '2020-10-16 11:30:00', 1400, 1300, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('020c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-10-17 11:00:00', '2020-10-17 11:30:00', 2000, 1500, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('021c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-11-15 11:00:00', '2020-11-15 11:30:00', 1400, 1200, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('022c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-11-16 11:00:00', '2020-11-16 11:30:00', 2000, 1500, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('023c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-11-17 11:00:00', '2020-11-17 11:30:00', 1400, 1200, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('024c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-12-15 11:00:00', '2020-12-15 11:30:00', 1800, 1500, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('025c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2020-12-16 11:00:00', '2020-12-16 11:30:00', 1800, 1500, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('026c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2021-01-15 11:00:00', '2021-01-15 11:30:00', 1800, 1500, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id, version) values ('027c7fb3-df04-4c0c-8ebe-411717961cac','FINISHED','EXAMINATION', '2021-01-16 11:00:00', '2021-01-16 11:30:00', 1800, 1500, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002', 1);


insert into work_time (id, start_date, start_time, end_date, end_time, staff_id, pharmacy_id) values ('8fb41318-60d4-11eb-ae93-0242ac130002', '2021-01-20', 8, '2021-02-20', 17, '25345278-52d3-11eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130002');
insert into work_time (id, start_date, start_time, end_date, end_time, staff_id, pharmacy_id) values ('8fc41318-60d4-12db-ae93-0242ac130002', '2021-01-23', 13, '2021-03-20', 20, '25345278-52d3-12eb-ac93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130202');
insert into work_time (id, start_date, start_time, end_date, end_time, staff_id, pharmacy_id) values ('8fa41318-60d4-13eb-ae93-0242ac130002', '2021-01-28', 12, '2021-02-23', 21, '25345278-52d3-13eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130202');
insert into work_time (id, end_date,end_time,start_date,start_time,pharmacy_id,staff_id) values ('1d16e66e-669b-49ca-9a7a-1a4f57e7f14e','2021-03-15',17,'2021-01-15',8,'cafeddee-56cb-11eb-ae93-0242ac130202','22345678-52d3-11eb-ae93-0242ac130002');
insert into work_time (id, end_date,end_time,start_date,start_time,pharmacy_id,staff_id) values ('42b6d408-665e-4329-8879-98a821f4cfbe','2021-01-14',15,'2021-01-01',8,'cafeddee-56cb-11eb-ae93-0242ac130202','22345678-52d3-11eb-ae93-0242ac130002');

insert into drug_kind_id (id, type) values ('33345278-52d3-13eb-ae93-0242ac130002','HERBAL');
insert into drug_kind_id (id, type) values ('34345278-52d3-13eb-ae93-0242ac130002','BIOLOGICAL');
insert into drug_kind_id (id, type) values ('35345278-52d3-13eb-ae93-0242ac130002','HOMEOPATIC');
insert into drug_kind_id (id, type) values ('36345278-52d3-13eb-ae93-0242ac130002','HUMAN');
insert into drug_kind_id (id, type) values ('37345278-52d3-13eb-ae93-0242ac130002','BLOOD');
insert into drug_kind_id (id, type) values ('38345278-52d3-13eb-ae93-0242ac130002','RADIOFARMACEUTICAL');
insert into drug_kind_id (id, type) values ('39345278-52d3-13eb-ae93-0242ac130002','RADIONUCLIDE');
insert into drug_kind_id (id, type) values ('40345278-52d3-13eb-ae93-0242ac130002','TRADICIONAL');

insert into drug_format_id (id, type) values ('38445278-52d3-13eb-ae93-0242ac130002','VACCINE');
insert into drug_format_id (id, type) values ('39545278-52d3-13eb-ae93-0242ac130002','CAPSULE');
insert into drug_format_id (id, type) values ('40645278-52d3-13eb-ae93-0242ac130002','INJECTION');


insert into ereceipt (id, price, creation_date, status, patient_id, version) values ('e672e844-67ac-11eb-ae93-0242ac130002', -1, '2021-01-20', 'NEW', '22793162-52d3-11eb-ae93-0242ac130002',1);
insert into ereceipt (id, price, creation_date, status, patient_id, pharmacy_id, version) values ('15df3e98-67ad-11eb-ae93-0242ac130002', 2200, '2020-12-20', 'PROCESSED', '22793162-52d3-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac111002',1);
insert into ereceipt (id, price, creation_date, status, patient_id, version) values ('19e26092-67ad-11eb-ae93-0242ac130002', -1, '2021-01-05', 'NEW', '22793162-52d3-11eb-ae93-0242ac130002',1);
insert into ereceipt (id, price, creation_date, status, patient_id, version) values ('1d4ac49a-67ad-11eb-ae93-0242ac130002', -1, '2021-01-13', 'NEW', '22793162-52d3-11eb-ae93-0242ac130002',1);
insert into ereceipt (id, price, creation_date, status, patient_id, version) values ('20e5ba6a-67ad-11eb-ae93-0242ac130002', -1, '2021-01-20', 'NEW', '22793162-52d3-11eb-ae93-0242ac130002',1);
insert into ereceipt (id, price, creation_date, status, patient_id, version) values ('8872e844-67ac-11eb-ae93-0242ac130002', -1, '2021-01-20', 'NEW', '22793162-52d3-11eb-ae93-0242ac130002',1);
insert into ereceipt (id, price, creation_date, status, patient_id, version) values ('9972e844-67ac-11eb-ae93-0242ac130002', -1, '2021-01-20', 'NEW', '22793162-52d3-11eb-ae93-0242ac130002',1);

insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('8872e844-67ac-11eb-ae93-0242ac130002','dac2b818-5838-11eb-ae93-0242ac130002',2);

insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('e672e844-67ac-11eb-ae93-0242ac130002','2fe1cd8e-5839-11eb-ae93-0242ac130002',2);
insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('e672e844-67ac-11eb-ae93-0242ac130002','2c797174-5839-11eb-ae93-0242ac130002',3);
insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('e672e844-67ac-11eb-ae93-0242ac130002','dac2b818-5838-11eb-ae93-0242ac130002',1);

insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('15df3e98-67ad-11eb-ae93-0242ac130002','2fe1cd8e-5839-11eb-ae93-0242ac130002',1);
insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('19e26092-67ad-11eb-ae93-0242ac130002','2c797174-5839-11eb-ae93-0242ac130002',2);
insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('1d4ac49a-67ad-11eb-ae93-0242ac130002','2fe1cd8e-5839-11eb-ae93-0242ac130002',1);
insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('20e5ba6a-67ad-11eb-ae93-0242ac130002','2fe1cd8e-5839-11eb-ae93-0242ac130002',1);
insert into ereceipt_items(e_receipt_id, drug_instance_id, amount) values ('9972e844-67ac-11eb-ae93-0242ac130002','2c797174-5839-11eb-ae93-0242ac130002',1);

insert into loyaltyprogram (id, points_for_appointment, points_for_consulting, points_to_enter_regular_cathegory, points_to_enter_silver_cathegory, points_to_enter_gold_cathegory, appointment_discount_regular, drug_discount_regular, consultation_discount_regular, appointment_discount_silver,drug_discount_silver, consultation_discount_silver, appointment_discount_gold,  drug_discount_gold, consultation_discount_gold) values ('791fee27-bb12-4340-9b0a-a7c9ef575278', 5, 4, 0, 50, 100, 0, 0, 0, 5, 6, 7, 15, 20, 25);

insert into drug_feedback (date, grade, drug_id, patient_id) values ('2020-03-03',4,'2c797174-5839-11eb-ae93-0242ac130002', '22793162-52d3-11eb-ae93-0242ac130002');
insert into drug_feedback (date, grade, drug_id, patient_id) values ('2020-05-03',5,'b4abdfc6-68cb-11eb-a102-d7374f9803ba', '22793162-52d3-11eb-ae93-0242ac130002');

insert into pharmacy_admin (id,pharmacy_id) values ('9ba98d47-1a8a-4ae1-b109-af7b56e94788','cafeddee-56cb-11eb-ae93-0242ac130002');

insert into absence (id, for_staff_id ,absence_status, start_date, end_date, reject_reason,pharmacy_id) values ('eba44ad0-6940-11eb-9439-0242ac130002', '25345278-52d3-13eb-ae93-0242ac130002', 'ACCEPTED','2021-02-18','2021-02-23', '','cafeddee-56cb-11eb-ae93-0242ac130002');
insert into absence (id, for_staff_id ,absence_status, start_date, end_date, reject_reason,pharmacy_id) values ('76eb2495-1499-4f68-8397-48e2a410aa6b', '25345278-52d3-11eb-ae93-0242ac130002', 'WAIT','2021-04-01','2021-04-23', '','cafeddee-56cb-11eb-ae93-0242ac130002');
insert into absence (id, for_staff_id ,absence_status, start_date, end_date, reject_reason,pharmacy_id) values ('281707fe-6a54-11eb-9439-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002', 'WAIT','2021-03-01','2021-03-14', '','cafeddee-56cb-11eb-ae93-0242ac130002');


insert into action_and_promotion (id,type,date_from,date_to,percent_of_discount,pharmacy_id) values ('2965458c-6976-11eb-9439-0242ac130002', 'DRUGDISCOUNT', '2021-02-03','2021-03-15', 15.0,'cafeddee-56cb-11eb-ae93-0242ac130002');
insert into action_and_promotion (id,type,date_from,date_to,percent_of_discount,pharmacy_id) values ('2965458c-6976-11eb-9439-0242ac130555', 'CONSULTATIONDISCOUNT', '2021-02-05','2021-03-15',25.0, 'cafeddee-56cb-11eb-ae93-0242ac130002');
insert into action_and_promotion (id,type,date_from,date_to,percent_of_discount,pharmacy_id) values ('2365458c-6976-11eb-9439-0242ac130555', 'EXAMINATIONDISCOUNT', '2021-02-05','2021-03-15',25.0, 'cafeddee-56cb-11eb-ae93-0242ac130002');

insert into orders (id, date, type, pharmacy_id, pharmacy_admin_id) values ('11111111-67ac-11eb-ae93-0242ac130002', '2021-05-05' , 'CREATED' , 'cafeddee-56cb-11eb-ae93-0242ac130002' , '9ba98d47-1a8a-4ae1-b109-af7b56e94788');
insert into orders (id, date, type, pharmacy_id, pharmacy_admin_id) values ('11112222-67ac-11eb-ae93-0242ac130002', '2021-06-05' , 'CREATED' , 'cafeddee-56cb-11eb-ae93-0242ac130002' , '9ba98d47-1a8a-4ae1-b109-af7b56e94788');

insert into drug_order (id, amount, drug_instance_id) values ('99e26092-67ad-11eb-ae93-0242ac130002', 2, 'dac2b818-5838-11eb-ae93-0242ac130002' );
insert into drug_order (id, amount, drug_instance_id) values ('44e26092-67ad-11eb-ae93-0242ac130002', 1, 'aecf0eac-68cb-11eb-9f52-838764dae934' );
insert into drug_order (id, amount, drug_instance_id) values ('55e26092-67ad-11eb-ae93-0242ac130002', 2, 'aecf0eac-68cb-11eb-9f52-838764dae934' );
insert into drug_order (id, amount, drug_instance_id) values ('66e26092-67ad-11eb-ae93-0242ac130002', 3, 'aecf0eac-68cb-11eb-9f52-838764dae934' );

insert into order_drug_for_order (order_id, drug_order_id) values ('11111111-67ac-11eb-ae93-0242ac130002', '99e26092-67ad-11eb-ae93-0242ac130002');
insert into order_drug_for_order (order_id, drug_order_id) values ('11111111-67ac-11eb-ae93-0242ac130002', '44e26092-67ad-11eb-ae93-0242ac130002');
insert into order_drug_for_order (order_id, drug_order_id) values ('11112222-67ac-11eb-ae93-0242ac130002', '55e26092-67ad-11eb-ae93-0242ac130002');
insert into order_drug_for_order (order_id, drug_order_id) values ('11112222-67ac-11eb-ae93-0242ac130002', '66e26092-67ad-11eb-ae93-0242ac130002');


insert into offers (id, date_to_delivery, status, price) values ('33331111-67ac-11eb-ae93-0242ac130002', '2021-04-04', 'WAITING', 9000 );
insert into offers (id, date_to_delivery, status, price) values ('44441111-67ac-11eb-ae93-0242ac130002', '2021-04-05', 'WAITING', 7500 );
insert into offers (id, date_to_delivery, status, price) values ('55551111-67ac-11eb-ae93-0242ac130002', '2021-05-02', 'WAITING', 8300 );

insert into offers_for_order (order_id, offers_id) values ('11111111-67ac-11eb-ae93-0242ac130002', '33331111-67ac-11eb-ae93-0242ac130002');
insert into offers_for_order (order_id, offers_id) values ('11111111-67ac-11eb-ae93-0242ac130002', '44441111-67ac-11eb-ae93-0242ac130002');
insert into offers_for_order (order_id, offers_id) values ('11111111-67ac-11eb-ae93-0242ac130002', '55551111-67ac-11eb-ae93-0242ac130002');


insert into offers_for_order (order_id, offers_id) values ('11111111-67ac-11eb-ae93-0242ac130002', '33331111-67ac-11eb-ae93-0242ac130002');
insert into offers_for_order (order_id, offers_id) values ('11112222-67ac-11eb-ae93-0242ac130002', '44441111-67ac-11eb-ae93-0242ac130002');

insert into drug_request(id, date_time, drug_instance_id, pharmacy_id, staff_Id) values('2219010c-a30e-477c-a61f-2301c7222c6a', '2021-02-11 15:42:33.936', '2fe1cd8e-5839-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac130002', '25345278-52d3-11eb-ae93-0242ac130002');
insert into drug_request(id, date_time, drug_instance_id, pharmacy_id, staff_Id) values('28c98597-0d66-4619-b4ea-64584aab0db1', '2021-02-11 15:40:24.324', '1fe1cd8e-5839-11eb-ae93-0242ac130002', 'cafeddee-56cb-11eb-ae93-0242ac130002', '25345278-52d3-11eb-ae93-0242ac130002');