CREATE TABLE IF NOT EXISTS member.tbl_account (
    id uuid PRIMARY KEY UNIQUE DEFAULT member.uuid_generate_v4(),
    mobile_money_number VARCHAR(255),
    bank_name_id UUID,
    mobile_telco_id UUID,
    account_number VARCHAR(255)
);