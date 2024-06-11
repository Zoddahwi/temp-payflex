package com.fintechedge.payflex.model.setup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Table("tbl_telco")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Telco {
    @Id
    private UUID id;
    @Column("mobile_telco_id")
    private UUID mobileTelcoId;
    @Column("telco_name")
    private String telcoName;
    @Column("description")
    private String description;
}
