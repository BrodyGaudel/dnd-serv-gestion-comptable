package com.dndserv.brody.gestioncomptable.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TaxDTO {
    private Long id;
    private String type;
    private Double percentage;
    private BigDecimal amount;
    private Long idSale;
}
