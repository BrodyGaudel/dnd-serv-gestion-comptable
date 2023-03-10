package com.dndserv.brody.gestioncomptable.repositories;

import com.dndserv.brody.gestioncomptable.entities.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepository extends JpaRepository<Tax, Long> {
}
