package com.dndserv.brody.gestioncomptable.services;

import com.dndserv.brody.gestioncomptable.dtos.SaleDTO;
import com.dndserv.brody.gestioncomptable.exceptions.SaleNotFoundException;

import java.util.Date;
import java.util.List;

public interface SaleService {
    SaleDTO findById(Long id) throws SaleNotFoundException;
    List<SaleDTO> findAll();
    List<SaleDTO> findByEnterpriseId(Long enterpriseId);
    List<SaleDTO> findByDateBetween(Date start, Date end);
    SaleDTO save(SaleDTO saleDTO);
    SaleDTO update(SaleDTO saleDTO) throws SaleNotFoundException;
    void deleteById(Long id);
    void deleteAll();
    void deleteAllByEnterpriseId(Long enterpriseId);

}
