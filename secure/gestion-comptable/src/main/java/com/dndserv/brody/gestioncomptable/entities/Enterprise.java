package com.dndserv.brody.gestioncomptable.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "SOCIETE_GROUPE")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Enterprise {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOM")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "enterprise")
    @JsonManagedReference
    private List<Sale> sales;


    @OneToMany(mappedBy = "enterprise")
    @JsonManagedReference
    private List<Purchase> purchases;

    @ManyToOne
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "enterprise")
    @JsonManagedReference
    private List<Admin> admins;
}
