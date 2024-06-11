CREATE TABLE registration.verification_token (
    id uuid DEFAULT registration.uuid_generate_v4(),
    token VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    "user" VARCHAR(255),
    user_id UUID NOT NULL,

    FOREIGN KEY (user_id) REFERENCES registration.tbl_newuser (id)
);