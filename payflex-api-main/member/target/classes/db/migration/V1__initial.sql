CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER EXTENSION "uuid-ossp" SET SCHEMA "member";

CREATE TABLE IF NOT EXISTS tbl_user (
        id uuid PRIMARY KEY UNIQUE DEFAULT member.uuid_generate_v4(),
        first_name VARCHAR(255),
        middle_name VARCHAR(255),
        last_name VARCHAR(255),
        staff_id VARCHAR(255),
        email_address VARCHAR(255) UNIQUE NOT NULL,
        employerId VARCHAR(255),
        mobile_number VARCHAR(255) UNIQUE,
        salary VARCHAR(255),
        email_verified BOOLEAN,
        mobile_verified BOOLEAN,
        account_approved BOOLEAN,
        department_id UUID,
        account_id UUID,
        created_at timestamp default current_timestamp,
        updated_at timestamp default current_timestamp

);





