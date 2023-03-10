package com.dndserv.brody.gestioncomptable.repositories;

import com.dndserv.brody.gestioncomptable.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
