package com.dndserv.brody.gestioncomptable.services;

import com.dndserv.brody.gestioncomptable.dtos.PurchaseDTO;
import com.dndserv.brody.gestioncomptable.exceptions.PurchaseNotFoundException;

import java.util.Date;
import java.util.List;

public interface PurchaseService {

    PurchaseDTO findById(Long id) throws PurchaseNotFoundException;
    List<PurchaseDTO> findAll();
    List<PurchaseDTO> findByEnterpriseId(Long enterpriseId);
    List<PurchaseDTO> findByDateBetween(Date start, Date end);
    PurchaseDTO save(PurchaseDTO purchaseDTO);
    PurchaseDTO update(PurchaseDTO purchaseDTO) throws PurchaseNotFoundException;
    void deleteById(Long id);
    void deleteAll();
    void deleteAllByEnterpriseId(Long enterpriseId);

}
