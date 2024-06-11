CREATE TABLE IF NOT EXISTS member.tbl_employer(
     id uuid PRIMARY KEY UNIQUE DEFAULT member.uuid_generate_v4(),
     employer_id uuid,
     employer_name varchar(255),
     description varchar(255),
     created_at timestamp default current_timestamp,
     updated_at timestamp default current_timestamp
);
