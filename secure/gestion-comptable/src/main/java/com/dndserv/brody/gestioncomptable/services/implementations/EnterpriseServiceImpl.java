package com.dndserv.brody.gestioncomptable.services.implementations;

import com.dndserv.brody.gestioncomptable.dtos.AdminDTO;
import com.dndserv.brody.gestioncomptable.dtos.EnterpriseDTO;
import com.dndserv.brody.gestioncomptable.entities.Admin;
import com.dndserv.brody.gestioncomptable.entities.Enterprise;
import com.dndserv.brody.gestioncomptable.entities.User;
import com.dndserv.brody.gestioncomptable.exceptions.EnterpriseNotFoundException;
import com.dndserv.brody.gestioncomptable.exceptions.UserNotFoundException;
import com.dndserv.brody.gestioncomptable.mappers.Mappers;
import com.dndserv.brody.gestioncomptable.repositories.AdminRepository;
import com.dndserv.brody.gestioncomptable.repositories.EnterpriseRepository;
import com.dndserv.brody.gestioncomptable.repositories.UserRepository;
import com.dndserv.brody.gestioncomptable.services.EnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseRepository repository;
    private final Mappers mappers;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public EnterpriseServiceImpl(EnterpriseRepository repository, Mappers mappers, UserRepository userRepository, AdminRepository adminRepository) {
        this.repository = repository;
        this.mappers = mappers;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }


    @Override
    public EnterpriseDTO findById(Long id) throws EnterpriseNotFoundException {
        log.info("In findById()");
        Enterprise enterprise = repository.findById(id)
                .orElseThrow( () -> new EnterpriseNotFoundException("enterprise not found"));
        log.info("enterprise found");
        return mappers.fromEnterprise(enterprise);
    }

    @Override
    public List<EnterpriseDTO> findAll() {
        log.info("In findAll()");
        List<Enterprise> enterprises = repository.findAll();
        log.info("list of enterprises found");
        return mappers.fromListOfEnterprises(enterprises);
    }

    @Override
    public List<EnterpriseDTO> findByNameOrDescription(String keyword) {
        log.info("In findByNameOrDescription()");
        List<Enterprise> enterprises = new ArrayList<>();
        enterprises.addAll(repository.findByNameContains(keyword));
        enterprises.addAll(repository.findByDescriptionContains(keyword));
        log.info("enterprises found");
        return mappers.fromListOfEnterprises(enterprises);
    }

    @Override
    public List<EnterpriseDTO> findAllByUserId(Long id) {
        log.info("In findAllByUserId()");
        List<Enterprise> enterprises = repository.findByUserId(id);
        log.info("enterprises found by user id");
        return mappers.fromListOfEnterprises(enterprises);
    }

    @Override
    public EnterpriseDTO save(EnterpriseDTO enterpriseDTO) throws UserNotFoundException {
        log.info("In save()");
        User user = userRepository.findById(enterpriseDTO.getUserId())
                .orElseThrow( () -> new UserNotFoundException("user not found"));
        Enterprise enterprise = mappers.fromEnterpriseDTO(enterpriseDTO);
        enterprise.setUser(user);
        Enterprise savedEnterprise = repository.save(enterprise);
        log.info("enterprise saved");
        return mappers.fromEnterprise(savedEnterprise);
    }

    @Override
    public EnterpriseDTO update(EnterpriseDTO enterpriseDTO) throws  EnterpriseNotFoundException {
        log.info("In update()");
        Enterprise enterprise = repository.findById(enterpriseDTO.getId())
                .orElseThrow( () -> new EnterpriseNotFoundException("the enterprise you try to update not found"));

        enterprise.setName(enterpriseDTO.getName());
        enterprise.setDescription(enterpriseDTO.getDescription());
        Enterprise enterpriseUpdated = repository.save(enterprise);
        log.info("enterprise updated");
        return mappers.fromEnterprise(enterpriseUpdated);
    }

    @Override
    public void deleteById(Long id) {
        log.info("In deleteById()");
        repository.deleteById(id);
        log.info("enterprise deleted");
    }

    @Override
    public void deleteAll() {
        log.info("In deleteAll()");
        repository.deleteAll();
        log.info("all enterprises deleted");
    }

    @Override
    public AdminDTO addAdminToEnterprise(Long enterpriseId, Long userId) throws UserNotFoundException, EnterpriseNotFoundException {
        log.info("In addAdminToEnterprise()");
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new UserNotFoundException("user not found"));
        Enterprise enterprise = repository.findById(enterpriseId)
                .orElseThrow( () -> new EnterpriseNotFoundException("enterprise not found"));

        Admin admin = new Admin();
        admin.setUserId(user.getId());
        admin.setEnterprise(enterprise);
        List<Admin> adminList = enterprise.getAdmins();
        Admin adminSaved = adminRepository.save(admin);
        adminList.add(adminSaved);
        enterprise.setAdmins(adminList);
        repository.save(enterprise);
        log.info("admin added successfully");
        return mappers.fromAdmin(adminSaved);
    }

    @Override
    public void removeAdminToEnterprise(Long enterpriseId, Long userId) throws EnterpriseNotFoundException {
        log.info("In removeAdminToEnterprise()");
        Enterprise enterprise = repository.findById(enterpriseId)
                .orElseThrow( () -> new EnterpriseNotFoundException("this enterprise not found"));
        List<Admin> adminList = enterprise.getAdmins();
        for(Admin admin: adminList){
            if(admin.getUserId().equals(userId)){
                adminRepository.deleteById(admin.getId());
                log.info("admin removed successfully");
            }
        }

    }

}
