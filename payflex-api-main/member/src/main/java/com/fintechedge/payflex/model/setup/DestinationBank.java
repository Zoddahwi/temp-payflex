package com.fintechedge.payflex.model.setup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("tbl_destination_bank")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DestinationBank {
    @Id
    private UUID id;
    @Column("destination_bank_name")
    private String destinationBankName;
    @Column("description")
    private String description;
}
