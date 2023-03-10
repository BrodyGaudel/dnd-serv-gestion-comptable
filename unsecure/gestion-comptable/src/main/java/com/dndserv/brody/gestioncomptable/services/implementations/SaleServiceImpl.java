package com.dndserv.brody.gestioncomptable.services.implementations;

import com.dndserv.brody.gestioncomptable.dtos.SaleDTO;
import com.dndserv.brody.gestioncomptable.entities.Customer;
import com.dndserv.brody.gestioncomptable.entities.Sale;
import com.dndserv.brody.gestioncomptable.entities.Tax;
import com.dndserv.brody.gestioncomptable.entities.Tva;
import com.dndserv.brody.gestioncomptable.exceptions.SaleNotFoundException;
import com.dndserv.brody.gestioncomptable.mappers.Mappers;
import com.dndserv.brody.gestioncomptable.repositories.SaleRepository;
import com.dndserv.brody.gestioncomptable.repositories.TaxRepository;
import com.dndserv.brody.gestioncomptable.repositories.TvaRepository;
import com.dndserv.brody.gestioncomptable.services.SaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final Mappers mappers;
    private final TaxRepository taxRepository;
    private final TvaRepository tvaRepository;

    public SaleServiceImpl(SaleRepository saleRepository, Mappers mappers, TaxRepository taxRepository, TvaRepository tvaRepository) {
        this.saleRepository = saleRepository;
        this.mappers = mappers;
        this.taxRepository = taxRepository;
        this.tvaRepository = tvaRepository;
    }

    @Override
    public SaleDTO findById(Long id) throws SaleNotFoundException {
        log.info("In findById()");
        Sale sale = saleRepository.findById(id)
                .orElseThrow( () -> new SaleNotFoundException("Sale Not Found"));
        log.info("Sale Found");
        return mappers.fromSale(sale);
    }

    @Override
    public List<SaleDTO> findAll() {
        log.info("In findAll()");
        List<Sale> sales = saleRepository.findAll();
        log.info("Sales found");
        return mappers.fromListOfSales(sales);
    }

    @Override
    public List<SaleDTO> findByEnterpriseId(Long enterpriseId) {
        log.info("In findByEnterpriseId()");
        List<Sale> saleList = saleRepository.findAll()
                .stream()
                .filter(s -> s.getEnterprise().getId().equals(enterpriseId))
                .toList();
        log.info("Sale(s) Found By Enterprise Id");
        return mappers.fromListOfSales(saleList);
    }


    @Override
    public List<SaleDTO> findByDateBetween(Date start, Date end) {
        log.info("In findByDateBetween()");
        List<Sale> saleList = saleRepository.findAll();
        List<Sale> sales = new ArrayList<>();
        for(Sale s: saleList){
            if((s.getDate().after(start))&&(s.getDate().before(end))){
                sales.add(s);
            }
        }
        log.info("Sale(s) fund by dates");
        return mappers.fromListOfSales(sales);
    }

    @Override
    public SaleDTO save(SaleDTO saleDTO) {
        log.info("In save()");
        Sale sale = mappers.fromSaleDTO(saleDTO);
        if(sale == null){
            log.info("Sale Not Saved");
            return null;
        }else{
            List<Tax> taxList = sale.getTaxList();
            List<Tva> tvaList = sale.getTvaList();
            sale.setTvaList(new ArrayList<>());
            sale.setTaxList(new ArrayList<>());
            Sale saleSaved = saleRepository.save(sale);
            log.info("Sale Saved Successfully");
            if(!taxList.isEmpty()){
                for(Tax t: taxList){
                    t.setSale(saleSaved);
                }
                List<Tax> taxes = taxRepository.saveAll(taxList);
                log.info("Taxes saved");
                saleSaved.setTaxList(taxes);
            }
            if(!tvaList.isEmpty()){
                for (Tva tva: tvaList){
                    tva.setSale(saleSaved);
                    tva.setPurchase(null);
                }
                List<Tva> tvas = tvaRepository.saveAll(tvaList);
                log.info("tvas saved");
                saleSaved.setTvaList(tvas);
            }
            return mappers.fromSale(saleSaved);
        }

    }

    @Override
    public SaleDTO update(SaleDTO saleDTO) throws SaleNotFoundException {
        log.info("In updated()");
        Sale sale = saleRepository.findById(saleDTO.getId())
                .orElseThrow( () -> new SaleNotFoundException("Sale not Found"));

        sale.setTimbre(saleDTO.getTimbre());
        sale.setAmountHT(saleDTO.getAmountHT());
        sale.setAmountTTC(saleDTO.getAmountTTC());
        List<Tax> taxList = mappers.fromListOfTaxDTOS(saleDTO.getTaxDTOList());
        sale.setTaxList(taxList);
        List<Tva> tvaList = mappers.fromListOfTvaDTO(saleDTO.getTvaDTOList());
        sale.setTvaList(tvaList);
        Customer customer = sale.getCustomer();
        customer.setEmail(saleDTO.getCustomerDTO().getEmail());
        customer.setName(saleDTO.getCustomerDTO().getName());
        customer.setFirstname(saleDTO.getCustomerDTO().getFirstname());
        customer.setPhone(saleDTO.getCustomerDTO().getPhone());
        sale.setCustomer(customer);
        Sale saleUpdated = saleRepository.save(sale);
        log.info("Sale updated");
        return mappers.fromSale(saleUpdated);
    }

    @Override
    public void deleteById(Long id) {
        log.info("In deleteById()");
        saleRepository.deleteById(id);
        log.info("Sale deleted");
    }

    @Override
    public void deleteAll() {
        log.info("In deleteAll()");
        saleRepository.deleteAll();
        log.info("All Sale deleted");
    }


    @Override
    public void deleteAllByEnterpriseId(Long enterpriseId) {
        List<Sale> sales = saleRepository.findAll();
        for(Sale s: sales){
            if(s.getEnterprise().getId().equals(enterpriseId)){
                saleRepository.deleteById(s.getId());
            }
        }
    }


}
