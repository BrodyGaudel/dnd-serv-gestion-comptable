package com.dndserv.brody.gestioncomptable.services.implementations;

import com.dndserv.brody.gestioncomptable.dtos.PurchaseDTO;
import com.dndserv.brody.gestioncomptable.entities.Enterprise;
import com.dndserv.brody.gestioncomptable.entities.Purchase;
import com.dndserv.brody.gestioncomptable.entities.Supplier;
import com.dndserv.brody.gestioncomptable.entities.Tva;
import com.dndserv.brody.gestioncomptable.exceptions.PurchaseNotFoundException;
import com.dndserv.brody.gestioncomptable.mappers.Mappers;
import com.dndserv.brody.gestioncomptable.repositories.EnterpriseRepository;
import com.dndserv.brody.gestioncomptable.repositories.PurchaseRepository;
import com.dndserv.brody.gestioncomptable.repositories.SupplierRepository;
import com.dndserv.brody.gestioncomptable.repositories.TvaRepository;
import com.dndserv.brody.gestioncomptable.services.PurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final Mappers mappers;
    private final SupplierRepository supplierRepository;
    private final TvaRepository tvaRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, EnterpriseRepository enterpriseRepository, Mappers mappers, SupplierRepository supplierRepository, TvaRepository tvaRepository) {
        this.purchaseRepository = purchaseRepository;
        this.enterpriseRepository = enterpriseRepository;
        this.mappers = mappers;
        this.supplierRepository = supplierRepository;
        this.tvaRepository = tvaRepository;
    }


    @Override
    public PurchaseDTO findById(Long id) throws PurchaseNotFoundException {
        log.info("In findById()");
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow( () -> new PurchaseNotFoundException("Purchase Not Found"));
        log.info("Purchase Found");
        return mappers.fromPurchase(purchase);
    }

    @Override
    public List<PurchaseDTO> findAll() {
        log.info("In findAll()");
        List<Purchase> purchases = purchaseRepository.findAll();
        log.info("List of Purchases found");
        return mappers.fromListOfPurchases(purchases);
    }

    @Override
    public List<PurchaseDTO> findByEnterpriseId(Long enterpriseId) {
        log.info("In findByEnterpriseId()");
        List<Purchase> purchases = purchaseRepository.findAll();
        List<Purchase> purchaseList = new ArrayList<>();
        for(Purchase p: purchases){
            if(enterpriseId.equals(p.getEnterprise().getId())){
                purchaseList.add(p);
            }
        }
        log.info("Purchase(s) Found");
        return mappers.fromListOfPurchases(purchaseList);
    }

    @Override
    public List<PurchaseDTO> findByDateBetween(Date start, Date end) {
        log.info("In findByDateBetween()");
        List<Purchase> purchaseList = purchaseRepository.findAll();
        List<Purchase> purchases = new ArrayList<>();
        for(Purchase p: purchaseList){
            if((p.getDate().after(start))&&(p.getDate().before(end))){
                purchases.add(p);
            }
        }
        log.info("Purchase(s) fund by dates");
        return mappers.fromListOfPurchases(purchases);
    }

    @Override
    public PurchaseDTO save(PurchaseDTO purchaseDTO) {
        log.info("In save()");
        Purchase purchase = mappers.fromPurchaseDTO(purchaseDTO);
        Enterprise enterprise = enterpriseRepository.findById(purchase.getEnterprise().getId())
                .orElse(null);
        if(enterprise == null){
            log.warn("purchase not saved");
            return null;
        }else {
            purchase.setEnterprise(enterprise);
            Supplier supplier = mappers.fromSupplierDTO(purchaseDTO.getSupplierDTO());
            supplier.setPurchase(purchase);
            Supplier savedSupplier = supplierRepository.save(supplier);
            savedSupplier.setPurchase(purchase);
            purchase.setSupplier(savedSupplier);
            List<Tva> tvaList = purchase.getTvaList();
            purchase.setTvaList(new ArrayList<>());
            Purchase savedPurchase = purchaseRepository.save(purchase);
            log.info("purchase saved");
            if(!tvaList.isEmpty()){
                for(Tva t: tvaList){
                    t.setPurchase(savedPurchase);
                    t.setSale(null);
                }
                List<Tva> tvas = tvaRepository.saveAll(tvaList);
                log.info("Tva saved");
                savedPurchase.setTvaList(tvas);
            }
            return mappers.fromPurchase(savedPurchase);
        }
    }

    @Override
    public PurchaseDTO update(PurchaseDTO purchaseDTO) throws PurchaseNotFoundException {
        log.info("update()");
        Purchase purchase = purchaseRepository.findById(purchaseDTO.getId())
                .orElseThrow( () -> new PurchaseNotFoundException("Purchase you try to update not found"));

        purchase.setAmountHT(purchaseDTO.getAmountHT());
        purchase.setAmountTTC(purchaseDTO.getAmountTTC());
        purchase.setAmountNet(purchaseDTO.getAmountNet());
        purchase.setDate(purchaseDTO.getDate());
        Supplier supplier = purchase.getSupplier();
        supplier.setFirstname(purchaseDTO.getSupplierDTO().getFirstname());
        supplier.setName(purchaseDTO.getSupplierDTO().getName());
        supplier.setPhone(purchaseDTO.getSupplierDTO().getPhone());
        supplier.setEmail(purchaseDTO.getSupplierDTO().getEmail());
        purchase.setSupplier(supplier);

        Purchase updatedPurchase = purchaseRepository.save(purchase);
        log.info("Purchase updated");
        return mappers.fromPurchase(updatedPurchase);
    }

    @Override
    public void deleteById(Long id) {
        log.info("In deleteById()");
        purchaseRepository.deleteById(id);
        log.info("Purchase deleted");
    }

    @Override
    public void deleteAll() {
        log.info("In deleteAll()");
        purchaseRepository.deleteAll();
        log.info("All purchase deleted");
    }

    @Override
    public void deleteAllByEnterpriseId(Long enterpriseId) {
        log.info("In deleteAllByEnterpriseId()");
        List<Purchase> purchases = purchaseRepository.findAll();
        for(Purchase p: purchases){
            if(enterpriseId.equals(p.getEnterprise().getId())){
                purchaseRepository.deleteById(p.getId());
            }
        }
        log.info("All Purchase Have Been Deleted");
    }


}
