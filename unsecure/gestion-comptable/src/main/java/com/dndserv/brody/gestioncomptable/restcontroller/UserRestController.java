package com.dndserv.brody.gestioncomptable.restcontroller;

import com.dndserv.brody.gestioncomptable.dtos.UserRequestDTO;
import com.dndserv.brody.gestioncomptable.dtos.UserResponseDTO;
import com.dndserv.brody.gestioncomptable.exceptions.*;
import com.dndserv.brody.gestioncomptable.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*")
public class UserRestController {

    private final UserService service;

    public UserRestController(UserService service) {
        this.service = service;
    }

    @GetMapping("/list")
    @ResponseBody
    public List<UserResponseDTO> findAllUsers(){
        return service.findAllUsers();
    }

    @GetMapping("/search")
    @ResponseBody
    public List<UserResponseDTO> findUsersByNameOrFirstname(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
        return service.findUsersByNameOrFirstname("%"+keyword+"%");
    }

    @GetMapping("/findUserByLogin/{login}")
    @ResponseBody
    public UserResponseDTO findUserByLogin(@PathVariable(name = "login") String login) throws UserNotFoundException {
        return service.findUserByLogin(login);
    }

    @GetMapping("/findUserByEmail/{email}")
    @ResponseBody
    public UserResponseDTO findUserByEmail(@PathVariable(name = "email") String email) throws UserNotFoundException {
        return service.findUserByEmail(email);
    }

    @GetMapping("/findUserById/{id}")
    @ResponseBody
    public UserResponseDTO findUserById(@PathVariable(name = "id") Long id) throws UserNotFoundException {
        return service.findUserById(id);
    }

    @GetMapping("/getAllAdminsByEnterpriseId/{enterpriseId}")
    @ResponseBody
    public List<UserResponseDTO> getAllAdminsByEnterpriseId(@PathVariable(name = "enterpriseId")  Long enterpriseId){
        return service.getAllAdminsByEnterpriseId(enterpriseId);
    }

    @DeleteMapping("/deleteUserById/{id}")
    void deleteUserById(@PathVariable(name = "id")  Long id){
        service.deleteUserById(id);
    }

    @DeleteMapping("/deleteAllUsers")
    void deleteAllUsers(){
        service.deleteAllUsers();
    }

    @PostMapping("/save")
    @ResponseBody
    public UserResponseDTO addUser(@RequestBody UserRequestDTO userRequestDTO) throws PhoneAlreadyExistException, LoginAlreadyExistException, EmailAlreadyExistException {
        return service.addUser(userRequestDTO);
    }

    @PutMapping("/update")
    @ResponseBody
    public UserResponseDTO updateUser(@RequestBody UserRequestDTO userRequestDTO) throws UserNotFoundException {
        return service.updateUser(userRequestDTO);
    }

    @GetMapping("/sendSmsToAuthenticate/{phone}")
    @ResponseBody
    public String sendSmsToAuthenticate(@PathVariable(name = "phone") String phone){
        return service.verifyUserPhoneBeforeSignUp(phone);
    }

    @PostMapping("/signUp/{code}")
    @ResponseBody
    public UserResponseDTO signUp(@PathVariable(name = "code") String code,@RequestBody UserRequestDTO userRequestDTO) throws PhoneAlreadyExistException, LoginAlreadyExistException, EmailAlreadyExistException {
        return service.signup(code, userRequestDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
