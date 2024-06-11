CREATE TABLE IF NOT EXISTS registration.tbl_tokens (
    id uuid DEFAULT registration.uuid_generate_v4(),
    email VARCHAR(255),
    accessToken VARCHAR(255),
    expiresIn VARCHAR(255),
    tokenType VARCHAR(255),
    refreshToken VARCHAR(255),
    scope VARCHAR(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);