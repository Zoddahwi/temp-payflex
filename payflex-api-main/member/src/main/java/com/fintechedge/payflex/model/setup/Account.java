package com.fintechedge.payflex.model.setup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("tbl_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @Column("id")
    private UUID id;
    @Column("mobile_money_number")
    private String mobileMoneyNumber;
    @Column("bank_name_id")
    private UUID bankNameId;
    @Column("mobile_telco_id")
    private UUID mobileTelcoId;
    @Column("account_number")
    private String accountNumber;
}

