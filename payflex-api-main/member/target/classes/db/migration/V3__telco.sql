CREATE TABLE IF NOT EXISTS member.tbl_telco(
    id uuid PRIMARY KEY UNIQUE DEFAULT member.uuid_generate_v4(),
    mobile_telco_id uuid,
    telco_name varchar(255),
    description varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
)