package com.fintechedge.payflex.model.setup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("tbl_department")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Department {
    @Id
    private UUID id;
    @Column("department_name")
    private String departmentName;
    @Column("department_id")
    private UUID departmentId;
    @Column("description")
    private String description;


}

