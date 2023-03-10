package com.dndserv.brody.gestioncomptable.entities;

import com.dndserv.brody.gestioncomptable.ennums.Type;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "VENTE")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private Type type;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "DATE")
    private Date date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID")
    private Customer customer;

    @Column(name = "MONTANT_HT")
    private BigDecimal amountHT;

    @Column(name = "TIMBRE")
    private BigDecimal timbre;

    @OneToMany(mappedBy = "sale")
    @JsonManagedReference
    private List<Tax> taxList;

    @OneToMany(mappedBy = "sale")
    @JsonManagedReference
    private List<Tva> tvaList;

    @Column(name = "MONTANT_TTC")
    private BigDecimal amountTTC;

    @ManyToOne
    @JsonBackReference
    private Enterprise enterprise;

}
