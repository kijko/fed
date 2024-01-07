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

-- end test data

CREATE TABLE account (
    account_number VARCHAR(28) PRIMARY KEY NOT NULL,
    owner_id VARCHAR(36) NOT NULL,
    balance DECIMAL(19,4) NOT NULL DEFAULT 0.0, -- 19,4 is a common precision for money
    CONSTRAINT fk_owner
        FOREIGN KEY(owner_id)
        REFERENCES fed_user(uuid)
        ON DELETE CASCADE
);

-- account for test user client

INSERT INTO public.account(account_number, owner_id, balance)
	VALUES ('1111111111111111111111111111', '582cbb20-dcfa-4ad7-bf73-32f3e9af5299', 0.0);

-- end account for test user client

CREATE TABLE transfer_request (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    from_account VARCHAR(28) NOT NULL,
    to_account VARCHAR(28) NOT NULL,
    status VARCHAR(32) NOT NULL,
    finalized_by VARCHAR(36),
    amount DECIMAL(19,4) NOT NULL, -- Same type as "account"."balance"
    requested_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_from_account
        FOREIGN KEY(from_account)
        REFERENCES account(account_number)
        ON DELETE CASCADE,
    CONSTRAINT fk_to_account
        FOREIGN KEY(to_account)
        REFERENCES account(account_number)
        ON DELETE CASCADE,
    CONSTRAINT fk_finalized_by
        FOREIGN KEY(finalized_by)
        REFERENCES fed_user(uuid)
);

