package com.fintechedge.payflex.model.setup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;
@Table("tbl_employer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employer {
    @Id
    private UUID id;
    @Column("employer_id")
    private UUID employerId;
    @Column("employer_name")
    private String employerName;
    @Column("description")
    private String description;
}
