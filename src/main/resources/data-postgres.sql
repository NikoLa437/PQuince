insert into authority (id, name) values ('7852aa5e-7040-4d99-8255-537a0b226c75','ROLE_PATIENT');
insert into authority (id, name) values ('563e9925-cff6-42b7-99fa-6b1235f67655','ROLE_SYSADMIN');
insert into authority (id, name) values ('09af8857-2f78-4eec-970f-059d3ed4589f','ROLE_DERMATHOLOGIST');
insert into authority (id, name) values ('ef9a3723-a72e-44ec-83ac-9d748fd0240f','ROLE_SUPPLIER');
insert into authority (id, name) values ('a1e3bac1-6093-4705-b835-eed75c3e5f21','ROLE_PHARMACIST');
insert into authority (id, name) values ('ea16767c-2c1f-49fb-ac98-c7739c0036e8','ROLE_PHARMACYADMIN');


--password - 123
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude) values ('22793162-52d3-11eb-ae93-0242ac130002',true, 'example@example.com','Stefan','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Stefic','grad Novi Sad','Serbia','Ulica Novosadskog sajma',45.254488,19.827929);
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude) values ('23793162-52d3-11eb-ae93-0242ac130002',true, 'example1@example.com','Nikola','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Stefic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699);

insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude) values ('11355678-52d3-11eb-ae93-0242ac130002',true, 'example5@example.com','Jelena','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Stefic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699);
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude) values ('22345678-52d3-11eb-ae93-0242ac130002',true, 'example6@example.com','Dusan','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Stefic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699);
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude) values ('25345678-52d3-11eb-ae93-0242ac130002',true, 'example8@example.com','Nemanja','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Stefic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699);


insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude) values ('25345278-52d3-11eb-ae93-0242ac130002',true, 'exampla11@example.com','Vidoje','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Stefic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699);
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude) values ('25345278-52d3-12eb-ac93-0242ac130002',true, 'exampla21@example.com','Sredoje','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Mikic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699);
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude) values ('25345278-52d3-13eb-ae93-0242ac130002',true, 'exampla31@example.com','Radoje','$2a$10$sqes3IpPL4mBgAAmimisyOWj5DlqPRndrJFNFw9zWiJjyNa5ozKS6','0600000','Bikic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699);
insert into users (id, active,email, name,password,phone_number,surname, city, country, street, latitude, longitude) values ('9ba98d47-1a8a-4ae1-b109-af7b56e94788',true, 'pharmacyadmin@example.com','Petar','$2a$10$Amd0M3ETJ/9hhxh5zJebvOf9Bx33aDyLS2qmCWbIksaHTS1h9DSAy','0624932342','Dusanic','grad Novi Sad','Serbia','Futoška ulica',45.249222,19.826699);

insert into patient (id, penalty, points) values ('22793162-52d3-11eb-ae93-0242ac130002',2,152);
insert into patient (id, penalty, points) values ('23793162-52d3-11eb-ae93-0242ac130002',0,2);

insert into user_authority (user_id, authority_id) values ('22793162-52d3-11eb-ae93-0242ac130002', '7852aa5e-7040-4d99-8255-537a0b226c75');
insert into user_authority (user_id, authority_id) values ('23793162-52d3-11eb-ae93-0242ac130002', '7852aa5e-7040-4d99-8255-537a0b226c75');
insert into user_authority (user_id, authority_id) values ('11355678-52d3-11eb-ae93-0242ac130002', '09af8857-2f78-4eec-970f-059d3ed4589f');
insert into user_authority (user_id, authority_id) values ('22345678-52d3-11eb-ae93-0242ac130002', '09af8857-2f78-4eec-970f-059d3ed4589f');
insert into user_authority (user_id, authority_id) values ('25345678-52d3-11eb-ae93-0242ac130002', '09af8857-2f78-4eec-970f-059d3ed4589f');
insert into user_authority (user_id, authority_id) values ('9ba98d47-1a8a-4ae1-b109-af7b56e94788', 'ea16767c-2c1f-49fb-ac98-c7739c0036e8');

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
insert into pharmacy (id, name, description, city, country, street, latitude, longitude, consultation_price) values ('cafeddee-56cb-11eb-ae93-0242ac130202', 'Zdravlje', 'Zdravlje apoteka','gradska opština Zemun','Serbia','Šilerova ulica',44.845752,20.387903, 999);
insert into pharmacy (id, name, description, city, country, street, latitude, longitude, consultation_price) values ('cafeddee-56cb-11eb-ae93-0242ac131302', 'Kopriva', 'Kopriva apoteka','grad Novi Sad','Serbia','Ulica Novosadskog sajma',45.254488,19.827929, 650);

insert into staff (id, staff_type) values ('11355678-52d3-11eb-ae93-0242ac130002','DERMATOLOGIST');
insert into staff (id, staff_type) values ('22345678-52d3-11eb-ae93-0242ac130002','DERMATOLOGIST');
insert into staff (id, staff_type) values ('25345678-52d3-11eb-ae93-0242ac130002','DERMATOLOGIST');
insert into staff (id, staff_type) values ('25345278-52d3-11eb-ae93-0242ac130002','PHARMACIST');
insert into staff (id, staff_type) values ('25345278-52d3-12eb-ac93-0242ac130002','PHARMACIST');
insert into staff (id, staff_type) values ('25345278-52d3-13eb-ae93-0242ac130002','PHARMACIST');
insert into staff (id, staff_type) values ('9ba98d47-1a8a-4ae1-b109-af7b56e94788','PHARMACYADMIN');


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


insert into drug_instance(drug_format, drug_instance_name, loyality_points, on_reciept, quantity, recommended_amount, side_effects, manufacturer_id, id, drug_kind) values('CAPSULE', 'Brufen',1, false, 500, '3x1 na dan', 'Nema nezeljenih dejstava', '20ddef44-5838-11eb-ae93-0242ac130002','2fe1cd8e-5839-11eb-ae93-0242ac130002', 'HUMAN');
insert into drug_instance(drug_format, drug_instance_name, loyality_points, on_reciept, quantity, recommended_amount, side_effects, manufacturer_id, id, drug_kind) values('CAPSULE', 'Rapidol',1, false, 600, '3x1 na dan', 'Nema nezeljenih dejstava, tako kazu', '574c3c20-5838-11eb-ae93-0242ac130002','2c797174-5839-11eb-ae93-0242ac130002', 'HUMAN');
insert into drug_instance(drug_format, drug_instance_name, loyality_points, on_reciept, quantity, recommended_amount, side_effects, manufacturer_id, id, drug_kind) values('CAPSULE', 'Ibumax',1, false, 400, '2x1 na dan', 'Nema nezeljenih dejstava', '61297672-5838-11eb-ae93-0242ac130002','dac2b818-5838-11eb-ae93-0242ac130002', 'HUMAN');

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


insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id) values ('2ba86116-5a40-11eb-ae93-0242ac130002','FINISHED','EXAMINATION', '2021-01-11 11:00:00', '2021-01-11 11:30:00', 1450, 1450, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002');
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id) values ('2ba86116-5c40-11eb-ae93-0242ac130002','FINISHED','EXAMINATION', '2021-01-14 11:00:00', '2021-01-14 11:15:00', 1400, 1400, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002');
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id) values ('2bc86116-5c40-11eb-ae93-0242ac130002','SCHEDULED','EXAMINATION', '2021-02-15 11:00:00', '2021-02-15 11:16:00', 1800, 1800, 'cafeddee-56cb-11eb-ae93-0242ac130002', '25345678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002');
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id, patient_id) values ('2bc86117-5c40-11eb-ae93-0242ac130002','SCHEDULED','EXAMINATION', '2021-02-17 11:00:00', '2021-02-17 11:16:00', 1800, 1800, 'cafeddee-56cb-11eb-ae93-0242ac130002', '22345678-52d3-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002');

insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, staff_id, pharmacy_id, patient_id) values ('2bc86117-5c40-11ab-ae93-0242ac130002','SCHEDULED','CONSULTATION', '2021-02-17 11:00:00', '2021-02-17 11:15:00', 450, 450, '25345278-52d3-11eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130002','22793162-52d3-11eb-ae93-0242ac130002');
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, staff_id, pharmacy_id, patient_id) values ('2bc86117-5c40-12ab-ae93-0242ac130002','SCHEDULED','CONSULTATION', '2021-02-18 11:00:00', '2021-02-18 11:15:00', 999, 999, '25345278-52d3-12eb-ac93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130202','22793162-52d3-11eb-ae93-0242ac130002');
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, staff_id, pharmacy_id, patient_id) values ('2bc86117-5c40-13ab-ae93-0242ac130002','SCHEDULED','CONSULTATION', '2021-02-19 11:00:00', '2021-02-19 11:15:00', 999, 999, '25345278-52d3-13eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130202','22793162-52d3-11eb-ae93-0242ac130002');
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, staff_id, pharmacy_id, patient_id) values ('2cc86117-5c40-13ab-ae93-0242ac130002','FINISHED','CONSULTATION', '2021-01-29 11:55:00', '2021-01-29 12:10:00', 999, 999, '25345278-52d3-12eb-ac93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130202','22793162-52d3-11eb-ae93-0242ac130002');


insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id) values ('2ba86116-5f40-11eb-ae93-0242ac130002','CREATED','EXAMINATION', '2021-02-11 11:00:00', '2021-02-11 11:30:00', 1400, 1400, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002');
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id) values ('2ba86116-5f40-11eb-ae93-0242ac134002','CREATED','EXAMINATION', '2021-02-11 11:30:00', '2021-02-11 12:00:00', 1300, 1300, 'cafeddee-56cb-11eb-ae93-0242ac130002', '11355678-52d3-11eb-ae93-0242ac130002');
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id) values ('2ca86116-5f40-11eb-ae93-0242ac134002','CREATED','EXAMINATION', '2021-02-13 11:30:00', '2021-02-13 12:00:00', 1500, 1500, 'cafeddee-56cb-11eb-ae93-0242ac130202', '22345678-52d3-11eb-ae93-0242ac130002');
insert into appointment (id, appointment_status, appointment_type, start_date_time, end_date_time, price, price_to_pay, pharmacy_id, staff_id) values ('2da86116-5f40-11eb-ae93-0242ac134002','CREATED','EXAMINATION', '2021-02-13 11:30:00', '2021-02-13 12:00:00', 1500, 1500, 'cafeddee-56cb-11eb-ae93-0242ac130002', '25345678-52d3-11eb-ae93-0242ac130002');

insert into work_time (id, start_date, start_time, end_date, end_time, staff_id, pharmacy_id) values ('8fb41318-60d4-11eb-ae93-0242ac130002', '2021-01-20', 8, '2021-02-20', 17, '25345278-52d3-11eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130002');
insert into work_time (id, start_date, start_time, end_date, end_time, staff_id, pharmacy_id) values ('8fc41318-60d4-12db-ae93-0242ac130002', '2021-01-23', 13, '2021-03-20', 20, '25345278-52d3-12eb-ac93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130202');
insert into work_time (id, start_date, start_time, end_date, end_time, staff_id, pharmacy_id) values ('8fa41318-60d4-13eb-ae93-0242ac130002', '2021-01-28', 12, '2021-02-23', 21, '25345278-52d3-13eb-ae93-0242ac130002','cafeddee-56cb-11eb-ae93-0242ac130202');
insert into work_time (id, end_date,end_time,start_date,start_time,pharmacy_id,staff_id) values ('1d16e66e-669b-49ca-9a7a-1a4f57e7f14e','2021-02-15',17,'2021-01-15',8,'cafeddee-56cb-11eb-ae93-0242ac130202','22345678-52d3-11eb-ae93-0242ac130002');
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

insert into loyaltyprogram (id, points_for_appointment, points_for_consulting, points_to_enter_regular_cathegory, points_to_enter_silver_cathegory, points_to_enter_gold_cathegory, appointment_discount_regular, drug_discount_regular, consultation_discount_regular, appointment_discount_silver,drug_discount_silver, consultation_discount_silver, appointment_discount_gold,  drug_discount_gold, consultation_discount_gold) values ('791fee27-bb12-4340-9b0a-a7c9ef575278', 5, 4, 10, 50, 100, 2, 2, 2, 5, 6, 7, 15, 20, 25);

