package com.dndserv.brody.gestioncomptable.dtos;

import com.dndserv.brody.gestioncomptable.ennums.Type;
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
public class SaleDTO {
    private Long id;
    private Type type;
    private Date date;
    private BigDecimal amountHT;
    private BigDecimal timbre;
    private BigDecimal amountTTC;
    private List<TaxDTO> taxDTOList;
    private List<TvaDTO> tvaDTOList;
    private Long idEnterprise;
    private CustomerDTO customerDTO;
}
