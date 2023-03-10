package com.dndserv.brody.gestioncomptable.repositories;

import com.dndserv.brody.gestioncomptable.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query(value = "SELECT * FROM ADMIN WHERE ENTERPRISE_ID = ?1", nativeQuery = true)
    List<Admin> findByEnterpriseId(Long id);
}
