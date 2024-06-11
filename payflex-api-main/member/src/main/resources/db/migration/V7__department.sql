CREATE TABLE IF NOT EXISTS member.tbl_department(
    id uuid PRIMARY KEY UNIQUE DEFAULT member.uuid_generate_v4(),
    department_name varchar(255),
    department_id uuid,
    description varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);