package com.dndserv.brody.gestioncomptable.restcontroller;

import com.dndserv.brody.gestioncomptable.dtos.FormDate;
import com.dndserv.brody.gestioncomptable.dtos.PurchaseDTO;
import com.dndserv.brody.gestioncomptable.exceptions.PurchaseNotFoundException;
import com.dndserv.brody.gestioncomptable.services.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase")
@CrossOrigin(origins = "*")
public class PurchaseRestController {

    private final PurchaseService service;

    public PurchaseRestController(PurchaseService service) {
        this.service = service;
    }

    @GetMapping("/findById/{id}")
    @ResponseBody
    public PurchaseDTO findById(@PathVariable(name = "id") Long id) throws PurchaseNotFoundException{
        return service.findById(id);
    }

    @GetMapping("/findAll")
    @ResponseBody
    public List<PurchaseDTO> findAll(){
        return service.findAll();
    }

    @GetMapping("/findByEnterpriseId/{id}")
    @ResponseBody
    public List<PurchaseDTO> findByEnterpriseId(@PathVariable(name = "id") Long id){
        return service.findByEnterpriseId(id);
    }

    @PostMapping("/findByDateBetween")
    @ResponseBody
    public List<PurchaseDTO> findByDateBetween(@RequestBody FormDate formDate){
        return service.findByDateBetween(formDate.getStart(), formDate.getEnd());
    }

    @PostMapping("/save")
    @ResponseBody
    public PurchaseDTO save(@RequestBody PurchaseDTO purchaseDTO){
        return service.save(purchaseDTO);
    }

    @PutMapping("/update")
    @ResponseBody
    PurchaseDTO update(@RequestBody PurchaseDTO purchaseDTO) throws PurchaseNotFoundException{
        return service.update(purchaseDTO);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable(name = "id") Long id){
        service.deleteById(id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll(){
        service.deleteAll();
    }

    @DeleteMapping("/deleteAllByEnterpriseId/{id}")
    public void deleteAllByEnterpriseId(@PathVariable(name = "id") Long id){
        service.deleteAllByEnterpriseId(id);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
