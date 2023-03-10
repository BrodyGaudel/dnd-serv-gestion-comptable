package com.dndserv.brody.gestioncomptable.restcontroller;

import com.dndserv.brody.gestioncomptable.dtos.AdminDTO;
import com.dndserv.brody.gestioncomptable.dtos.EnterpriseDTO;
import com.dndserv.brody.gestioncomptable.exceptions.EnterpriseNotFoundException;
import com.dndserv.brody.gestioncomptable.exceptions.UserNotFoundException;
import com.dndserv.brody.gestioncomptable.services.EnterpriseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enterprise")
@CrossOrigin(origins = "*")
public class EnterpriseRestController {

    private final EnterpriseService service;

    public EnterpriseRestController(EnterpriseService service) {
        this.service = service;
    }

    @GetMapping("/findById/{id}")
    @ResponseBody
    public EnterpriseDTO findById(@PathVariable(name="id") Long id) throws EnterpriseNotFoundException{
        return service.findById(id);
    }

    @GetMapping("/findAll")
    @ResponseBody
    public List<EnterpriseDTO> findAll(){
        return service.findAll();
    }

    @GetMapping("/search")
    @ResponseBody
    public List<EnterpriseDTO> findByNameOrDescription(@RequestParam(name = "keyword", defaultValue = "") String keyword){
        return service.findByNameOrDescription("%"+keyword+"%");
    }

    @GetMapping("/findAllByUserId/{id}")
    @ResponseBody
    public List<EnterpriseDTO> findAllByUserId(@PathVariable(name="id") Long id){
        return service.findAllByUserId(id);
    }

    @PostMapping("/save")
    @ResponseBody
    public EnterpriseDTO save(@RequestBody EnterpriseDTO enterpriseDTO) throws UserNotFoundException{
        return service.save(enterpriseDTO);
    }

    @PutMapping("/update")
    @ResponseBody
    public EnterpriseDTO update(@RequestBody EnterpriseDTO enterpriseDTO) throws EnterpriseNotFoundException{
        return service.update(enterpriseDTO);
    }

    @DeleteMapping("delete/{id}")
    public void deleteById(@PathVariable(name="id") Long id){
        service.deleteById(id);
    }

    @DeleteMapping("deleteAll")
    public void deleteAll(){
        service.deleteAll();
    }

    @GetMapping("/addAdminToEnterprise/{enterpriseId}/{userId}")
    @ResponseBody
    public AdminDTO addAdminToEnterprise(@PathVariable(name="enterpriseId") Long enterpriseId,
                                         @PathVariable(name="userId") Long userId) throws UserNotFoundException, EnterpriseNotFoundException{
        return service.addAdminToEnterprise(enterpriseId, userId);
    }

    @DeleteMapping("/removeAdminToEnterprise/{enterpriseId}/{userId}")
    public void removeAdminToEnterprise(@PathVariable(name="enterpriseId") Long enterpriseId,
                                        @PathVariable(name="userId") Long userId)throws EnterpriseNotFoundException{
        service.removeAdminToEnterprise(enterpriseId,userId);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
