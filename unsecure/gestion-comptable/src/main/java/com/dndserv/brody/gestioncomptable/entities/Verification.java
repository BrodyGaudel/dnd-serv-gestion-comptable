package com.dndserv.brody.gestioncomptable.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "VERIFICATION")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Verification {
    @Id
    private String code;
    private String phone;
}
