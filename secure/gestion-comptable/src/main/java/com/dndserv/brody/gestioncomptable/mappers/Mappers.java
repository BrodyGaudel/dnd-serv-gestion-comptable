package com.dndserv.brody.gestioncomptable.mappers;

import com.dndserv.brody.gestioncomptable.dtos.*;
import com.dndserv.brody.gestioncomptable.entities.*;

import java.util.List;

public interface Mappers {

    User fromUserRequestDTO(UserRequestDTO userRequestDTO);
    UserResponseDTO fromUser(User user);
    List<UserResponseDTO> fromListOfUsers(List<User> users);
    Enterprise fromEnterpriseDTO(EnterpriseDTO enterpriseDTO);
    EnterpriseDTO fromEnterprise(Enterprise enterprise);
    List<EnterpriseDTO> fromListOfEnterprises(List<Enterprise> enterprises);
    AdminDTO fromAdmin(Admin admin);
    Sale fromSaleDTO(SaleDTO saleDTO);
    SaleDTO fromSale(Sale sale);
    List<SaleDTO> fromListOfSales(List<Sale> sales);
    CustomerDTO fromCustomer(Customer customer);
    Customer fromCustomerDTO(CustomerDTO customerDTO);
    Tax fromTaxDTO(TaxDTO taxDTO);
    TaxDTO fromTax(Tax tax);
    List<Tax> fromListOfTaxDTOS(List<TaxDTO> taxDTOList);
    List<TaxDTO> fromListOfTax(List<Tax> taxes);
    Tva fromTvaDTO(TvaDTO tvaDTO);
    TvaDTO fromTva(Tva tva);
    List<Tva> fromListOfTvaDTO(List<TvaDTO> tvaDTOS);
    List<TvaDTO> fromListOfTva(List<Tva> tvas);
    SupplierDTO fromSupplier(Supplier supplier);
    Supplier fromSupplierDTO(SupplierDTO supplierDTO);
    PurchaseDTO fromPurchase(Purchase purchase);
    Purchase fromPurchaseDTO(PurchaseDTO purchaseDTO);
    List<PurchaseDTO> fromListOfPurchases(List<Purchase> purchases);

}
