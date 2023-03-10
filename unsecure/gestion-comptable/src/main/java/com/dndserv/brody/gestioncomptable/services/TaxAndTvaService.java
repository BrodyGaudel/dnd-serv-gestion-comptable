package com.dndserv.brody.gestioncomptable.services;

import com.dndserv.brody.gestioncomptable.dtos.TaxDTO;
import com.dndserv.brody.gestioncomptable.dtos.TvaDTO;

public interface TaxAndTvaService {
    TaxDTO saveTax(TaxDTO taxDTO);
    TvaDTO saveTva(TvaDTO tvaDTO);
    TvaDTO updateTva(TvaDTO tvaDTO);
    TaxDTO updateTax(TaxDTO taxDTO);
    void deleteTax(Long id);
    void deleteTva(Long id);
}
