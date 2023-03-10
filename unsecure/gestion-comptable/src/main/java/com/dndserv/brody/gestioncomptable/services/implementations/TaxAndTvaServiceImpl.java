package com.dndserv.brody.gestioncomptable.services.implementations;

import com.dndserv.brody.gestioncomptable.dtos.TaxDTO;
import com.dndserv.brody.gestioncomptable.dtos.TvaDTO;
import com.dndserv.brody.gestioncomptable.entities.Purchase;
import com.dndserv.brody.gestioncomptable.entities.Sale;
import com.dndserv.brody.gestioncomptable.entities.Tax;
import com.dndserv.brody.gestioncomptable.entities.Tva;
import com.dndserv.brody.gestioncomptable.mappers.Mappers;
import com.dndserv.brody.gestioncomptable.repositories.PurchaseRepository;
import com.dndserv.brody.gestioncomptable.repositories.SaleRepository;
import com.dndserv.brody.gestioncomptable.repositories.TaxRepository;
import com.dndserv.brody.gestioncomptable.repositories.TvaRepository;
import com.dndserv.brody.gestioncomptable.services.TaxAndTvaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class TaxAndTvaServiceImpl implements TaxAndTvaService {

    private final SaleRepository repository;
    private final Mappers mappers;
    private final TvaRepository tvaRepository;
    private final PurchaseRepository purchaseRepository;
    private final TaxRepository taxRepository;

    public TaxAndTvaServiceImpl(SaleRepository repository, Mappers mappers, TvaRepository tvaRepository, PurchaseRepository purchaseRepository, TaxRepository taxRepository) {
        this.repository = repository;
        this.mappers = mappers;
        this.tvaRepository = tvaRepository;
        this.purchaseRepository = purchaseRepository;
        this.taxRepository = taxRepository;
    }


    @Override
    public TaxDTO saveTax(TaxDTO taxDTO) {
        log.info("In saveTax()");
        Tax tax = mappers.fromTaxDTO(taxDTO);
        Long idSale = tax.getSale().getId();
        Sale sale = repository.findById(idSale).orElse(null);
        if(sale == null){
            log.info("Tax not saved");
            return null;
        }else {
            tax.setSale(sale);
            Tax savedTax = taxRepository.save(tax);
            log.info("Tax Saved");
            return mappers.fromTax(savedTax);
        }
    }

    @Override
    public TvaDTO saveTva(TvaDTO tvaDTO) {
        log.info("In saveTva()");
        Tva tva = mappers.fromTvaDTO(tvaDTO);
        Long idPurchase = tva.getPurchase().getId();
        Long idSale = tva.getSale().getId();
        Sale sale = repository.findById(idSale).orElse(null);
        tva.setSale(sale);
        Purchase purchase = purchaseRepository.findById(idPurchase).orElse(null);
        tva.setPurchase(purchase);

        Tva savedTva = tvaRepository.save(tva);
        log.info("TVA SAVED");
        return mappers.fromTva(savedTva);
    }

    @Override
    public TvaDTO updateTva(TvaDTO tvaDTO) {
        log.info("In updateTva()");
        Tva tva = tvaRepository.findById(tvaDTO.getId()).orElse(null);
        if(tva == null){
            log.warn("TVA Not Updated , due to TVA Not Found");
            return null;
        }else {
            tva.setType(tvaDTO.getType());
            tva.setAmount(tvaDTO.getAmount());
            tva.setPercentage(tvaDTO.getPercentage());
            Tva tvaUpdated = tvaRepository.save(tva);
            log.info("TVA Updated");
            return mappers.fromTva(tvaUpdated);
        }
    }

    @Override
    public TaxDTO updateTax(TaxDTO taxDTO) {
        log.info("In updateTax()");
        Tax tax = taxRepository.findById(taxDTO.getId()).orElse(null);
        if(tax == null){
            log.warn("TAX Not Updated , due to TAX Not Found");
            return null;
        }else {
            tax.setType(taxDTO.getType());
            tax.setAmount(taxDTO.getAmount());
            tax.setPercentage(taxDTO.getPercentage());
            Tax updatedTax = taxRepository.save(tax);
            log.info("TAX Updated");
            return mappers.fromTax(updatedTax);
        }
    }

    @Override
    public void deleteTax(Long id) {
        log.info("In deleteTax()");
        taxRepository.deleteById(id);
        log.info("Tax deleted");
    }

    @Override
    public void deleteTva(Long id) {
        log.info("deleteTva");
        tvaRepository.deleteById(id);
        log.info("Tva deleted");
    }
}
