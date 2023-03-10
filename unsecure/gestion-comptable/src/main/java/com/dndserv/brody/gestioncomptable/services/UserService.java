package com.dndserv.brody.gestioncomptable.services;

import com.dndserv.brody.gestioncomptable.dtos.UserRequestDTO;
import com.dndserv.brody.gestioncomptable.dtos.UserResponseDTO;
import com.dndserv.brody.gestioncomptable.exceptions.EmailAlreadyExistException;
import com.dndserv.brody.gestioncomptable.exceptions.LoginAlreadyExistException;
import com.dndserv.brody.gestioncomptable.exceptions.PhoneAlreadyExistException;
import com.dndserv.brody.gestioncomptable.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {

    UserResponseDTO addUser(UserRequestDTO userRequestDTO) throws EmailAlreadyExistException, PhoneAlreadyExistException, LoginAlreadyExistException;
    UserResponseDTO updateUser(UserRequestDTO userRequestDTO) throws UserNotFoundException;
    UserResponseDTO findUserById(Long id) throws UserNotFoundException;
    List<UserResponseDTO> findAllUsers();
    List<UserResponseDTO> findUsersByNameOrFirstname(String keyword);
    UserResponseDTO findUserByLogin(String login) throws UserNotFoundException;
    UserResponseDTO findUserByEmail(String email) throws UserNotFoundException;
    void deleteUserById(Long id);
    void deleteAllUsers();
    void sendSms(String phone);
    List<UserResponseDTO> getAllAdminsByEnterpriseId(Long enterpriseId);

    String verifyUserPhoneBeforeSignUp(String phone);

    UserResponseDTO signup(String code, UserRequestDTO userRequestDTO) throws PhoneAlreadyExistException, LoginAlreadyExistException, EmailAlreadyExistException;

}
