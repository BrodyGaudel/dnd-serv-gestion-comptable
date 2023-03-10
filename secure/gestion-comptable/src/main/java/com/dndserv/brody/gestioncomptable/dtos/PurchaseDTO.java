package com.dndserv.brody.gestioncomptable.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PurchaseDTO {
    private Long id;
    private Date date;
    private SupplierDTO supplierDTO;
    private BigDecimal amountHT;
    private List<TvaDTO> tvaList;
    private BigDecimal amountTTC;
    private BigDecimal amountNet;
    private Long enterpriseId;
}
