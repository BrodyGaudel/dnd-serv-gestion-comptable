package com.dndserv.brody.gestioncomptable.services.implementations;

import com.dndserv.brody.gestioncomptable.dtos.UserRequestDTO;
import com.dndserv.brody.gestioncomptable.dtos.UserResponseDTO;
import com.dndserv.brody.gestioncomptable.entities.Admin;
import com.dndserv.brody.gestioncomptable.entities.User;
import com.dndserv.brody.gestioncomptable.entities.Verification;
import com.dndserv.brody.gestioncomptable.exceptions.EmailAlreadyExistException;
import com.dndserv.brody.gestioncomptable.exceptions.LoginAlreadyExistException;
import com.dndserv.brody.gestioncomptable.exceptions.PhoneAlreadyExistException;
import com.dndserv.brody.gestioncomptable.exceptions.UserNotFoundException;
import com.dndserv.brody.gestioncomptable.mappers.Mappers;
import com.dndserv.brody.gestioncomptable.repositories.AdminRepository;
import com.dndserv.brody.gestioncomptable.repositories.UserRepository;
import com.dndserv.brody.gestioncomptable.services.PhoneVerification;
import com.dndserv.brody.gestioncomptable.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Mappers mappers;
    private final AdminRepository adminRepository;
    private final PhoneVerification phoneVerification;

    public UserServiceImpl(UserRepository userRepository, Mappers mappers, AdminRepository adminRepository, PhoneVerification phoneVerification) {
        this.userRepository = userRepository;
        this.mappers = mappers;
        this.adminRepository = adminRepository;
        this.phoneVerification = phoneVerification;
    }

    @Override
    public UserResponseDTO addUser(UserRequestDTO userRequestDTO) throws EmailAlreadyExistException, PhoneAlreadyExistException, LoginAlreadyExistException {
        log.info("in add user");
        if(Boolean.TRUE.equals(emailExist(userRequestDTO.getEmail()))){
            throw new EmailAlreadyExistException("This email is already used by another user.");
        }else if(Boolean.TRUE.equals(phoneExist(userRequestDTO.getPhone()))){
            throw new PhoneAlreadyExistException("This phone number is already used by another user.");
        } else if (Boolean.TRUE.equals(loginExist(userRequestDTO.getLogin()))) {
            throw new LoginAlreadyExistException("This login is already used by another user.");
        }else {
            User user = mappers.fromUserRequestDTO(userRequestDTO);
            User savedUser = userRepository.save(user);
            log.info("user saved successfully");
            return mappers.fromUser(savedUser);
        }

    }

    @Override
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) throws UserNotFoundException {
        log.info("in update user");
        User user = userRepository.findById(userRequestDTO.getId())
                .orElseThrow(() -> new UserNotFoundException("the user you want to update was not found"));

        user.setName(userRequestDTO.getName());
        user.setFirstname(userRequestDTO.getFirstname());
        user.setPassword(userRequestDTO.getPassword());
        user.setEnabled(userRequestDTO.getEnabled());

        User updatedUser = userRepository.save(user);
        log.info("user updated successfully");
        return mappers.fromUser(updatedUser);
    }

    @Override
    public UserResponseDTO findUserById(Long id) throws UserNotFoundException {
        log.info("In find user by id");
        User user = userRepository.findById(id)
                .orElseThrow( () -> new UserNotFoundException("user not found"));
        log.info("user not found");
        return mappers.fromUser(user);
    }

    @Override
    public List<UserResponseDTO> findAllUsers() {
        log.info("In find all users");
        List<User> users = userRepository.findAll();
        log.info("list of users found");
        return mappers.fromListOfUsers(users);
    }

    @Override
    public List<UserResponseDTO> findUsersByNameOrFirstname(String keyword) {
        log.info("in find users by name or firstname");
        List<User> users = userRepository.searchUsers(keyword);
        log.info("user(s) have been found");
        return mappers.fromListOfUsers(users);
    }

    @Override
    public UserResponseDTO findUserByLogin(String login) throws UserNotFoundException {
        log.info("in find user by login");
        User user = userRepository.findByLogin(login);
        if(user == null){
            throw new UserNotFoundException("the user not found");
        }else{
            log.info("the user found");
            return mappers.fromUser(user);
        }
    }

    @Override
    public UserResponseDTO findUserByEmail(String email) throws UserNotFoundException {
        log.info("in find user by email");
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UserNotFoundException("the user not found by mail");
        }else{
            log.info("the user found by mail");
            return mappers.fromUser(user);
        }
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("in delete user by id");
        userRepository.deleteById(id);
        log.info("user deleted");
    }

    @Override
    public void deleteAllUsers() {
        log.info("in delete all users");
        userRepository.deleteAll();
        log.info("all users deleted");
    }

    @Override
    public void sendSms(String phone) {
        log.info("sms");
    }

    @Override
    public List<UserResponseDTO> getAllAdminsByEnterpriseId(Long enterpriseId) {
        log.info("In getAllAdminsByEnterpriseId()");
        List<Admin> admins = adminRepository.findByEnterpriseId(enterpriseId);
        if(admins.isEmpty()){
            log.info("no admins found");
            return Collections.emptyList();
        }else{
            List<User> users = new ArrayList<>();
            for(Admin a: admins){
                users.add(userRepository.findById(a.getUserId()).orElse(null));
            }
            log.info("admins found");
            return mappers.fromListOfUsers(users);
        }

    }

    @Override
    public String verifyUserPhoneBeforeSignUp(String phone) {
        //send a sms with a code to this phone number
        Verification verification = phoneVerification.verifyPhoneNumber(phone);
        return verification.getCode();
    }

    @Override
    public UserResponseDTO signup(String code, UserRequestDTO userRequestDTO) throws PhoneAlreadyExistException, LoginAlreadyExistException, EmailAlreadyExistException {
        log.info("In SignUp() :");
        Boolean isVerified = phoneVerification.checkIfPhoneIsVerified(code, userRequestDTO.getPhone());
        if(Boolean.TRUE.equals(isVerified)){
            log.info("Phone verified so methode addUser() is calling");
            phoneVerification.deleteVerification(code);
            return addUser(userRequestDTO);
        }else{
            log.info("Phone not verified");
            return null;
        }
    }

    private Boolean emailExist(String email){
        return userRepository.verifyIfEmailExists(email);
    }

    private Boolean phoneExist(String phone){
        return userRepository.verifyIfPhoneExists(phone);
    }

    private Boolean loginExist(String login){
        return userRepository.verifyIfLoginExists(login);
    }


}
