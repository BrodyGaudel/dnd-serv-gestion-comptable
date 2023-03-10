package com.dndserv.brody.gestioncomptable.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "TVA")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Tva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "POURCENTAGE")
    private Double percentage;

    private String type;

    @Column(name = "MONTANT")
    private BigDecimal amount;

    @ManyToOne
    @JsonBackReference
    private Sale sale;

    @ManyToOne
    @JsonBackReference
    private Purchase purchase;
}
