package com.dndserv.brody.gestioncomptable.services.implementations;

import com.dndserv.brody.gestioncomptable.dtos.UserRequestDTO;
import com.dndserv.brody.gestioncomptable.dtos.UserResponseDTO;
import com.dndserv.brody.gestioncomptable.entities.User;
import com.dndserv.brody.gestioncomptable.exceptions.EmailAlreadyExistException;
import com.dndserv.brody.gestioncomptable.exceptions.LoginAlreadyExistException;
import com.dndserv.brody.gestioncomptable.exceptions.PhoneAlreadyExistException;
import com.dndserv.brody.gestioncomptable.exceptions.UserNotFoundException;
import com.dndserv.brody.gestioncomptable.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl service;

    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addUser() throws PhoneAlreadyExistException, LoginAlreadyExistException, EmailAlreadyExistException {
        UserRequestDTO userRequest = new UserRequestDTO(
                (long)(Math.random()+1000),
                "brody",
                "gaudel",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                "password",
                true
        );
        UserResponseDTO userResponse = service.addUser(userRequest);
        assertNotNull(userResponse);
    }

    @Test
    void updateUser() throws UserNotFoundException {
        User user = new User();
        user.setId((long)(Math.random()+1000));
        user.setName("name");
        user.setFirstname("firstname");
        user.setLogin(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID().toString());
        user.setPhone(UUID.randomUUID().toString());
        user.setPassword("password");
        user.setEnabled(true);
        User user1 = repository.save(user);

        UserResponseDTO userUpdated = service.updateUser(
                new UserRequestDTO(
                        user1.getId(),
                        "the firstname",
                        "the name",
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        "password",
                        true
                ));
        assertNotNull(userUpdated);
        assertEquals(userUpdated.getId(),user1.getId());
        assertEquals("the firstname", userUpdated.getFirstname());
        assertEquals("the name", userUpdated.getName());
    }

    @Test
    void findUserById() throws UserNotFoundException {
        User user = new User();
        user.setId((long)(Math.random()+1000));
        user.setName("name");
        user.setFirstname("firstname");
        user.setLogin(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID().toString());
        user.setPhone(UUID.randomUUID().toString());
        user.setPassword("password");
        user.setEnabled(true);
        User user1 = repository.save(user);

        UserResponseDTO userResponseDTO = service.findUserById(user1.getId());
        assertNotNull(userResponseDTO);
        assertEquals(userResponseDTO.getId(),user1.getId());
    }

    @Test
    void findAllUsers() {
        User user = new User();
        user.setId((long)(Math.random()+1000));
        user.setName("name");
        user.setFirstname("firstname");
        user.setLogin(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID().toString());
        user.setPhone(UUID.randomUUID().toString());
        user.setPassword("password");
        user.setEnabled(true);
        repository.save(user);
        List<UserResponseDTO> userResponseDTOS = service.findAllUsers();
        assertNotNull(userResponseDTOS);
    }

    @Test
    void findUsersByNameOrFirstname() {
        User user = new User();
        user.setId((long)(Math.random()+1000));
        user.setName("name");
        user.setFirstname("firstname");
        user.setLogin(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID().toString());
        user.setPhone(UUID.randomUUID().toString());
        user.setPassword("password");
        user.setEnabled(true);
        User user1 = repository.save(user);
        List<UserResponseDTO> userResponseDTOS = service.findUsersByNameOrFirstname(user1.getName());
        assertNotNull(userResponseDTOS);
    }

    @Test
    void findUserByLogin() throws UserNotFoundException {
        User user = new User();
        user.setId((long)(Math.random()+1000));
        user.setName("name");
        user.setFirstname("firstname");
        user.setLogin(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID().toString());
        user.setPhone(UUID.randomUUID().toString());
        user.setPassword("password");
        user.setEnabled(true);
        User user1 = repository.save(user);
        UserResponseDTO userResponseDTO = service.findUserByLogin(user1.getLogin());
        assertNotNull(userResponseDTO);
        assertEquals(userResponseDTO.getLogin(),user1.getLogin());
    }

    @Test
    void findUserByEmail() throws UserNotFoundException {
        User user = new User();
        user.setId((long)(Math.random()+1000));
        user.setName("name");
        user.setFirstname("firstname");
        user.setLogin(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID().toString());
        user.setPhone(UUID.randomUUID().toString());
        user.setPassword("password");
        user.setEnabled(true);
        User user1 = repository.save(user);
        UserResponseDTO userResponseDTO = service.findUserByEmail(user1.getEmail());
        assertNotNull(userResponseDTO);
        assertEquals(userResponseDTO.getEmail(),user1.getEmail());
    }

    @Test
    void deleteUserById() {
        User user = new User();
        user.setId((long)(Math.random()+1000));
        user.setName("name");
        user.setFirstname("firstname");
        user.setLogin(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID().toString());
        user.setPhone(UUID.randomUUID().toString());
        user.setPassword("password");
        user.setEnabled(true);
        User user1 = repository.save(user);

        service.deleteUserById(user1.getId());
        User user2 = repository.findById(user1.getId()).orElse(null);
        assertNull(user2);
    }


}