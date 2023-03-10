package com.dndserv.brody.gestioncomptable.services.implementations;

import com.dndserv.brody.gestioncomptable.dtos.CustomerDTO;
import com.dndserv.brody.gestioncomptable.dtos.SaleDTO;
import com.dndserv.brody.gestioncomptable.ennums.Type;
import com.dndserv.brody.gestioncomptable.exceptions.SaleNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SaleServiceImplTest {

    @Autowired
    private SaleServiceImpl service;

    @Test
    void findAll() {
        List<SaleDTO> saleDTOList = service.findAll();
        assertNotNull(saleDTOList);
    }

    @Test
    void save() {
        CustomerDTO customerDTO = new CustomerDTO(
                1L,
                "firstname",
                "name",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
        SaleDTO saleDTO = new SaleDTO(
                10L,
                Type.VENTE,
                new Date(),
                new BigDecimal(500),
                new BigDecimal(500),
                new BigDecimal(500),
                null,
                null,
                1L,
                customerDTO
        );

        SaleDTO sale = service.save(saleDTO);
        assertNotNull(sale);
    }

    @Test
    void update() throws SaleNotFoundException {
        CustomerDTO customerDTO = new CustomerDTO(
                1L,
                "firstname",
                "name",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
        SaleDTO saleDTO = new SaleDTO(
                1L,
                Type.VENTE,
                new Date(),
                new BigDecimal(900),
                new BigDecimal(900),
                new BigDecimal(900),
                null,
                null,
                1L,
                customerDTO
        );

        SaleDTO sale = service.update(saleDTO);
        assertNotNull(sale);
        assertEquals(sale.getId(), saleDTO.getId());
        assertEquals(new BigDecimal(900), sale.getAmountHT());
    }

}