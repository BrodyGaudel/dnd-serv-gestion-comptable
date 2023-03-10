package com.dndserv.brody.gestioncomptable.services.implementations;

import com.dndserv.brody.gestioncomptable.dtos.EnterpriseDTO;
import com.dndserv.brody.gestioncomptable.entities.Enterprise;
import com.dndserv.brody.gestioncomptable.entities.User;
import com.dndserv.brody.gestioncomptable.exceptions.EnterpriseNotFoundException;
import com.dndserv.brody.gestioncomptable.exceptions.UserNotFoundException;
import com.dndserv.brody.gestioncomptable.repositories.EnterpriseRepository;
import com.dndserv.brody.gestioncomptable.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EnterpriseServiceImplTest {

    @Autowired
    private EnterpriseServiceImpl service;

    @Autowired
    private EnterpriseRepository repository;

    @Autowired
    private UserRepository userRepository;


    @Test
    void findById() throws EnterpriseNotFoundException {
        User user = new User();
        user.setId((long)(Math.random()+1000));
        user.setName("name");
        user.setFirstname("firstname");
        user.setLogin(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID().toString());
        user.setPhone(UUID.randomUUID().toString());
        user.setPassword("password");
        user.setEnabled(true);
        User user1 = userRepository.save(user);

        Enterprise enterprise = new Enterprise();
        enterprise.setName("enterprise");
        enterprise.setDescription("the enterprise");
        enterprise.setId((long)(Math.random()+1000));
        enterprise.setUser(user1);
        Enterprise enterprise1 = repository.save(enterprise);

        EnterpriseDTO enterpriseDTO = service.findById(enterprise1.getId());
        assertNotNull(enterpriseDTO);
        assertEquals(enterpriseDTO.getId(), enterprise1.getId());

    }

    @Test
    void findAll() {
        User user = new User();
        user.setId((long)(Math.random()+1000));
        user.setName("name");
        user.setFirstname("firstname");
        user.setLogin(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID().toString());
        user.setPhone(UUID.randomUUID().toString());
        user.setPassword("password");
        user.setEnabled(true);
        User user1 = userRepository.save(user);

        Enterprise enterprise = new Enterprise();
        enterprise.setName("enterprise");
        enterprise.setDescription("the enterprise");
        enterprise.setId((long)(Math.random()+1000));
        enterprise.setUser(user1);
        repository.save(enterprise);
        List<EnterpriseDTO> enterpriseDTOS = service.findAll();
        assertNotNull(enterpriseDTOS);
    }

    @Test
    void findByNameOrDescription() {
        User user = new User();
        user.setId((long)(Math.random()+1000));
        user.setName("name");
        user.setFirstname("firstname");
        user.setLogin(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID().toString());
        user.setPhone(UUID.randomUUID().toString());
        user.setPassword("password");
        user.setEnabled(true);
        User user1 = userRepository.save(user);

        Enterprise enterprise = new Enterprise();
        enterprise.setName("enterprise");
        enterprise.setDescription("the enterprise");
        enterprise.setId((long)(Math.random()+1000));
        enterprise.setUser(user1);
        Enterprise enterprise1 = repository.save(enterprise);
        List<EnterpriseDTO> enterpriseDTOS = service.findByNameOrDescription(enterprise1.getName());
        assertNotNull(enterpriseDTOS);
    }

  @Test
    void findAllByUserId() {
      User user = new User();
      user.setId((long)(Math.random()+1000));
      user.setName("name");
      user.setFirstname("firstname");
      user.setLogin(UUID.randomUUID().toString());
      user.setEmail(UUID.randomUUID().toString());
      user.setPhone(UUID.randomUUID().toString());
      user.setPassword("password");
      user.setEnabled(true);
      User user1 = userRepository.save(user);

      Enterprise enterprise = new Enterprise();
      enterprise.setName("enterprise");
      enterprise.setDescription("the enterprise");
      enterprise.setId((long)(Math.random()+1000));
      enterprise.setUser(user1);
      Enterprise enterprise1 = repository.save(enterprise);
      List<EnterpriseDTO> enterpriseDTOS = service.findAllByUserId(enterprise1.getUser().getId());
      assertNotNull(enterpriseDTOS);

    }

      @Test
    void save() throws UserNotFoundException {
          User user = new User();
          user.setId((long)(Math.random()+1000));
          user.setName("name");
          user.setFirstname("firstname");
          user.setLogin(UUID.randomUUID().toString());
          user.setEmail(UUID.randomUUID().toString());
          user.setPhone(UUID.randomUUID().toString());
          user.setPassword("password");
          user.setEnabled(true);
          User user1 = userRepository.save(user);
        EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
        enterpriseDTO.setDescription("ENTERPRISE");
        enterpriseDTO.setName("THE NAME");
        enterpriseDTO.setUserId(user1.getId());
        EnterpriseDTO saved = service.save(enterpriseDTO);
        assertNotNull(saved);
    }

    @Test
    void update() throws EnterpriseNotFoundException {
        User user = new User();
        user.setId((long)(Math.random()+1000));
        user.setName("name");
        user.setFirstname("firstname");
        user.setLogin(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID().toString());
        user.setPhone(UUID.randomUUID().toString());
        user.setPassword("password");
        user.setEnabled(true);
        User user1 = userRepository.save(user);

        Enterprise enterprise = new Enterprise();
        enterprise.setName("enterprise");
        enterprise.setDescription("the enterprise");
        enterprise.setId((long)(Math.random()+1000));
        enterprise.setUser(user1);
        Enterprise enterprise1 = repository.save(enterprise);
        EnterpriseDTO enterpriseDTO = service.update( new EnterpriseDTO(
                enterprise1.getId(),
                "Name",
                "Description",
                enterprise1.getUser().getId()
        ));
        assertNotNull(enterpriseDTO);
        assertEquals("Name", enterpriseDTO.getName());
        assertEquals("Description", enterpriseDTO.getDescription());
        assertEquals(enterprise1.getId(), enterpriseDTO.getId());
    }

}