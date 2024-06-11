CREATE TABLE IF NOT EXISTS registration.password_reset_token
(
    id          UUID DEFAULT registration.uuid_generate_v4() NOT NULL,
    token       VARCHAR(255),
    user_id     UUID NOT NULL,
    expiry_date TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_password_reset_token PRIMARY KEY (id),
    CONSTRAINT FK_PASSWORD_RESET_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES "registration"."tbl_newuser" (id)
);