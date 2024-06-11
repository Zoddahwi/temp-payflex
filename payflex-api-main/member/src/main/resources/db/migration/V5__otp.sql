CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER EXTENSION "uuid-ossp" SET SCHEMA "member";

CREATE TABLE IF NOT EXISTS member.tbl_otp (
    id uuid DEFAULT member.uuid_generate_v4() PRIMARY KEY,
    otp varchar NOT NULL,
    user_id varchar NOT NULL,
    email varchar(255) NOT NULL,
    mobile_number varchar(255) NOT NULL,
    status varchar(255) NOT NULL,
    type varchar(255) NOT NULL,
    code varchar(255) NOT NULL,
    verification_type varchar(255),
    expiry_time timestamp,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);


