CREATE TABLE IF NOT EXISTS tbl_destination_bank(
    id uuid PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    destination_bank_name varchar(255),
    description varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);
