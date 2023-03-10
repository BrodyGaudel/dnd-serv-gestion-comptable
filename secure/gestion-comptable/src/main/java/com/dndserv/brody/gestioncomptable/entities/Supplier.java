package com.dndserv.brody.gestioncomptable.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FOURNISSEUR")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRENOM")
    private String firstname;

    @Column(name = "NOM")
    private String name;

    @Column(name = "TELEPHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Transient
    @OneToOne(mappedBy = "supplier")
    private Purchase purchase;

}
