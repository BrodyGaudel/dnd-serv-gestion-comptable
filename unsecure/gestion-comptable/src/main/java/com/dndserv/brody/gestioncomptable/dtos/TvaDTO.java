package com.dndserv.brody.gestioncomptable.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TvaDTO {
    private Long id;
    private Double percentage;
    private String type;
    private BigDecimal amount;
    private Long idSale;
    private Long idPurchase;
}
