package com.dndserv.brody.gestioncomptable.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ACHAT")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATE")
    private Date date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FOURNISSEUR_ID", referencedColumnName = "ID")
    private Supplier supplier;

    @Column(name = "MONTANT_HT")
    private BigDecimal amountHT;

    @OneToMany(mappedBy = "purchase")
    @JsonManagedReference
    private List<Tva> tvaList;

    @Column(name = "MONTANT_TTC")
    private BigDecimal amountTTC;

    @Column(name = "MONTANT_NET")
    private BigDecimal amountNet;

    @ManyToOne
    @JsonBackReference
    private Enterprise enterprise;

}
