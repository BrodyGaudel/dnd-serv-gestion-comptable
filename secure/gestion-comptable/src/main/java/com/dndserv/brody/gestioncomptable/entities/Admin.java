package com.dndserv.brody.gestioncomptable.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ADMIN")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "UTILISATEUR_ID")
    private Long userId;

    @ManyToOne
    @JsonBackReference
    private Enterprise enterprise;
}
