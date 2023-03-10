package com.dndserv.brody.gestioncomptable.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SupplierDTO {
    private Long id;
    private String firstname;
    private String name;
    private String phone;
    private String email;
    private Long purchaseId;
}
