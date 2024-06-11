CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER EXTENSION "uuid-ossp" SET SCHEMA "registration";

CREATE TABLE IF NOT EXISTS registration.tbl_newuser (
    id uuid PRIMARY KEY UNIQUE DEFAULT registration.uuid_generate_v4(),
    email varchar(255),
    username varchar(255),
    token varchar(255),
    password varchar(255),
    passwordResetToken varchar(255),
    role varchar(255),
    phoneNumber varchar(255),
    isPhoneNumberVerified boolean DEFAULT false,
    verified boolean DEFAULT false,
    roles varchar(255)[] DEFAULT ARRAY['USER'],
    firstName varchar(255),
    lastName varchar(255),
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

