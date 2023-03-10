package com.dndserv.brody.gestioncomptable.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "UTILISATEUR")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRENOM", nullable = false)
    private String firstname;

    @Column(name = "NOM", nullable = false)
    private String name;

    @Column(name = "MATRICULE_FISCAL", nullable = false, unique = true)
    private String login;

    @Column(name = "NUMERO_TELEPHONE", nullable = false, unique = true)
    private String phone;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "MOT_DE_PASSE", nullable = false)
    private String password;

    private Boolean enabled;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="user_role",joinColumns = @JoinColumn(name="user_id") ,
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Enterprise> enterprises;

}
