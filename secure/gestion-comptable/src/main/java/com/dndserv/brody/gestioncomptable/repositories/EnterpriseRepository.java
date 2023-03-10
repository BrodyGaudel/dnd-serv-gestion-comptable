package com.dndserv.brody.gestioncomptable.repositories;

import com.dndserv.brody.gestioncomptable.entities.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

    @Query("select e from Enterprise e where e.name like :kw")
    List<Enterprise> findByNameContains(@Param("kw") String keyword);

    @Query("select e from Enterprise e where e.description like :kw")
    List<Enterprise> findByDescriptionContains(@Param("kw") String keyword);

    @Query(value = "SELECT * FROM SOCIETE_GROUPE WHERE USER_ID = ?1", nativeQuery = true)
    List<Enterprise> findByUserId(Long userId);


}
