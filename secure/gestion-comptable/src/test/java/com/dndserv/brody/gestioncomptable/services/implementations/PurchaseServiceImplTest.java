package com.dndserv.brody.gestioncomptable.services.implementations;

import com.dndserv.brody.gestioncomptable.dtos.PurchaseDTO;
import com.dndserv.brody.gestioncomptable.dtos.SupplierDTO;
import com.dndserv.brody.gestioncomptable.exceptions.PurchaseNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PurchaseServiceImplTest {

    @Autowired
    private PurchaseServiceImpl service;

    @Test
    void findAll() {
        List<PurchaseDTO> purchaseDTOList = service.findAll();
        assertNotNull(purchaseDTOList);
    }

    @Test
    void findByEnterpriseId() {
        Long id = (long)1;
        List<PurchaseDTO> purchaseDTOList = service.findByEnterpriseId(id);
        assertNotNull(purchaseDTOList);
    }

    @Test
    void save() {
        Long idEnterprise = (long)1;
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setDate(new Date());
        purchaseDTO.setId(idEnterprise);
        purchaseDTO.setAmountNet( new BigDecimal(100000));
        purchaseDTO.setAmountTTC( new BigDecimal(100100));
        purchaseDTO.setAmountHT(new BigDecimal(100000));
        purchaseDTO.setEnterpriseId(idEnterprise);
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setEmail("email@email.com");
        supplierDTO.setName("name");
        supplierDTO.setFirstname("firstname");
        supplierDTO.setPhone("12345678");
        supplierDTO.setPurchaseId(purchaseDTO.getId());
        purchaseDTO.setSupplierDTO(supplierDTO);


        PurchaseDTO saved = service.save(purchaseDTO);
        assertNotNull(saved);
    }

    @Test
    void update() throws PurchaseNotFoundException {
        Long id = (long)1;
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setDate(new Date());
        purchaseDTO.setId(id);
        purchaseDTO.setAmountNet( new BigDecimal(100000));
        purchaseDTO.setAmountTTC( new BigDecimal(100100));
        purchaseDTO.setAmountHT(new BigDecimal(100000));
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setEmail("email@email.com");
        supplierDTO.setName("the name");
        supplierDTO.setId(id);
        supplierDTO.setFirstname("the firstname");
        supplierDTO.setPhone("12345678");
        purchaseDTO.setSupplierDTO(supplierDTO);

        PurchaseDTO updated = service.update(purchaseDTO);
        assertNotNull(updated);

    }
}