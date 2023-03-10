package com.dndserv.brody.gestioncomptable.repositories;

import com.dndserv.brody.gestioncomptable.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
