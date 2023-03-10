package com.dndserv.brody.gestioncomptable.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "TAXE")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "POURCENTAGE")
    private Double percentage;

    @Column(name = "MONTANT")
    private BigDecimal amount;

    @ManyToOne
    @JsonBackReference
    private Sale sale;


}
