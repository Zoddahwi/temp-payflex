CREATE TABLE registration.otp_new_user (
    id uuid DEFAULT registration.uuid_generate_v4(),
    otp INT NOT NULL,
    userId VARCHAR(6) NOT NULL,
    email VARCHAR(100) NOT NULL,
    code VARCHAR(100) NOT NULL,
    expiry TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP + INTERVAL '5 minutes',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id) REFERENCES registration.tbl_newuser(id)

);