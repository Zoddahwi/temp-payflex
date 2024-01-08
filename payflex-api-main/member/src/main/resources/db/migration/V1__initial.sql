CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER EXTENSION "uuid-ossp" SET SCHEMA "member";

CREATE TABLE IF NOT EXISTS tbl_user (
    id uuid PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    first_name varchar(255),
    last_name varchar(255),
    staff_id varchar(255),
    email_address varchar(255),
    employer_id varchar(255),
    mobile_number varchar(255),
    account_number varchar(255),
    destination_bank varchar(255),
    salary varchar(255),
    destination_telco varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);




