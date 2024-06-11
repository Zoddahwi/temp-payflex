CREATE TABLE IF NOT EXISTS member.tbl_destination_bank(
    id uuid PRIMARY KEY UNIQUE DEFAULT member.uuid_generate_v4(),
    destination_bank_name varchar(255),
    bank_name_id uuid,
    bank_name varchar(255),
    description varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);
