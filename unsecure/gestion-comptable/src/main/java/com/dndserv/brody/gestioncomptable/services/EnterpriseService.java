package com.dndserv.brody.gestioncomptable.services;

import com.dndserv.brody.gestioncomptable.dtos.AdminDTO;
import com.dndserv.brody.gestioncomptable.dtos.EnterpriseDTO;
import com.dndserv.brody.gestioncomptable.exceptions.EnterpriseNotFoundException;
import com.dndserv.brody.gestioncomptable.exceptions.UserNotFoundException;

import java.util.List;

public interface EnterpriseService {
    EnterpriseDTO findById(Long id) throws EnterpriseNotFoundException;
    List<EnterpriseDTO> findAll();
    List<EnterpriseDTO> findByNameOrDescription(String keyword);
    List<EnterpriseDTO> findAllByUserId(Long id);
    EnterpriseDTO save(EnterpriseDTO enterpriseDTO) throws UserNotFoundException;
    EnterpriseDTO update(EnterpriseDTO enterpriseDTO) throws EnterpriseNotFoundException;
    void deleteById(Long id);
    void deleteAll();
    AdminDTO addAdminToEnterprise(Long enterpriseId, Long userId) throws UserNotFoundException, EnterpriseNotFoundException;
    void removeAdminToEnterprise(Long enterpriseId, Long userId)throws EnterpriseNotFoundException;

}
