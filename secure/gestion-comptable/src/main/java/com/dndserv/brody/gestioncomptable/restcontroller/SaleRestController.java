package com.dndserv.brody.gestioncomptable.restcontroller;


import com.dndserv.brody.gestioncomptable.dtos.FormDate;
import com.dndserv.brody.gestioncomptable.dtos.SaleDTO;
import com.dndserv.brody.gestioncomptable.dtos.TaxDTO;
import com.dndserv.brody.gestioncomptable.dtos.TvaDTO;
import com.dndserv.brody.gestioncomptable.exceptions.SaleNotFoundException;
import com.dndserv.brody.gestioncomptable.services.SaleService;
import com.dndserv.brody.gestioncomptable.services.TaxAndTvaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sale")
@CrossOrigin(origins = "*")
public class SaleRestController {

    private final SaleService service;
    private final TaxAndTvaService taxAndTvaService;

    public SaleRestController(SaleService service, TaxAndTvaService taxAndTvaService) {
        this.service = service;
        this.taxAndTvaService = taxAndTvaService;
    }

    @GetMapping("/findById/{id}")
    @ResponseBody
    public SaleDTO findById(@PathVariable(name = "id") Long id) throws SaleNotFoundException{
        return service.findById(id);
    }

    @GetMapping("/findAll")
    @ResponseBody
    public List<SaleDTO> findAll(){
        return service.findAll();
    }

    @GetMapping("/findByEnterpriseId/{id}")
    @ResponseBody
    public List<SaleDTO> findByEnterpriseId(@PathVariable(name = "id") Long id){
        return service.findByEnterpriseId(id);
    }

    @PostMapping("/findByDateBetween")
    @ResponseBody
    public List<SaleDTO> findByDateBetween(@RequestBody FormDate formDate){
        return service.findByDateBetween(formDate.getStart(), formDate.getEnd());
    }


    @PostMapping("/save")
    @ResponseBody
    public SaleDTO save(@RequestBody SaleDTO saleDTO){
        return service.save(saleDTO);
    }

    @PutMapping("/update")
    @ResponseBody
    public SaleDTO update(@RequestBody SaleDTO saleDTO) throws SaleNotFoundException{
        return service.update(saleDTO);
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
    void deleteAllByEnterpriseId(@PathVariable(name = "id") Long id){
        service.deleteAllByEnterpriseId(id);
    }


    @PostMapping("/tax/save")
    @ResponseBody
    public TaxDTO saveTax(@RequestBody TaxDTO taxDTO){
        return taxAndTvaService.saveTax(taxDTO);
    }

    @PostMapping("/tva/save")
    @ResponseBody
    public TvaDTO saveTva(TvaDTO tvaDTO){
        return taxAndTvaService.saveTva(tvaDTO);
    }

    @PutMapping("/tva/update")
    @ResponseBody
    public TvaDTO updateTva(@RequestBody  TvaDTO tvaDTO){
        return taxAndTvaService.updateTva(tvaDTO);
    }

    @PutMapping("/tax/update")
    @ResponseBody
    public TaxDTO updateTax(@RequestBody TaxDTO taxDTO){
        return taxAndTvaService.updateTax(taxDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
