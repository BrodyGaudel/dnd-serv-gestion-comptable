package com.dndserv.brody.gestioncomptable.repositories;

import com.dndserv.brody.gestioncomptable.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("select s from Sale s where s.enterprise.id=?1")
    List<Sale> findByEnterpriseId(Long enterpriseId);

    List<Sale> findByDate(Date date);
}
