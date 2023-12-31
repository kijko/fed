CREATE TABLE fed_user (
    uuid VARCHAR(36) PRIMARY KEY NOT NULL,
    login_hash CHAR(64) NOT NULL,
    password_hash CHAR(64) NOT NULL,
    firstname VARCHAR(32) NOT NULL,
    lastname VARCHAR(32) NOT NULL,
    blocked BOOLEAN NOT NULL DEFAULT FALSE,
    type VARCHAR(32) NOT NULL
);

-- test data - remove later on

-- admin/admin
INSERT INTO public.fed_user(uuid, login_hash, password_hash, firstname, lastname, blocked, type)
    VALUES ('16d9b090-5338-4db4-81ae-6a4aad8666a6', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'Edd', 'Gain', false, 'ADMIN');

-- client/client
INSERT INTO public.fed_user(uuid, login_hash, password_hash, firstname, lastname, blocked, type)
	VALUES ('582cbb20-dcfa-4ad7-bf73-32f3e9af5299', '948fe603f61dc036b5c596dc09fe3ce3f3d30dc90f024c85f3c82db2ccab679d', '948fe603f61dc036b5c596dc09fe3ce3f3d30dc90f024c85f3c82db2ccab679d', 'Ted', 'Kaczynski', false, 'CLIENT');

-- emp/emp
INSERT INTO public.fed_user(uuid, login_hash, password_hash, firstname, lastname, blocked, type)
	VALUES ('ea078695-2d28-4cb6-ae93-67d4a244ed37', '9d586dc0a48a2ed04839e0a69750893438e8d379e2fa45e94e82c5b3abb00daa', '9d586dc0a48a2ed04839e0a69750893438e8d379e2fa45e94e82c5b3abb00daa', 'Richard', 'Kuklinski', false, 'BANK_EMPLOYEE');
