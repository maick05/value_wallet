package br.speck.valuewallet.api.domain.entity.sqlserver;

import jakarta.persistence.*;

@Entity
@Table(name = "operation_type")
public class OperationTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 25, unique = true)
    private String name_key;
    @Column(name = "translations", columnDefinition = "NVARCHAR(MAX)")
    private String translations;
}
