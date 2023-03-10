package com.dndserv.brody.gestioncomptable.mappers.implementation;

import com.dndserv.brody.gestioncomptable.dtos.*;
import com.dndserv.brody.gestioncomptable.entities.*;
import com.dndserv.brody.gestioncomptable.mappers.Mappers;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MappersImpl implements Mappers {
    @Override
    public User fromUserRequestDTO(UserRequestDTO userRequestDTO) {
        try{
            User user = new User();
            user.setEmail(userRequestDTO.getEmail());
            user.setId(userRequestDTO.getId());
            user.setName(userRequestDTO.getName());
            user.setPhone(userRequestDTO.getPhone());
            user.setFirstname(userRequestDTO.getFirstname());
            user.setPassword(userRequestDTO.getPassword());
            user.setLogin(userRequestDTO.getLogin());
            user.setEnabled(userRequestDTO.getEnabled());
            return user;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public UserResponseDTO fromUser(User user) {
        try{
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setPhone(user.getPhone());
            userResponseDTO.setFirstname(user.getFirstname());
            userResponseDTO.setEmail(user.getEmail());
            userResponseDTO.setId(user.getId());
            userResponseDTO.setName(user.getName());
            userResponseDTO.setLogin(user.getLogin());
            userResponseDTO.setEnabled(user.getEnabled());
            return userResponseDTO;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<UserResponseDTO> fromListOfUsers(List<User> users) {
        try{
            return users.stream().map(this::fromUser).toList();
        }catch (Exception e){
            return Collections.emptyList();
        }
    }

    @Override
    public Enterprise fromEnterpriseDTO(EnterpriseDTO enterpriseDTO) {
        try {
            User user = new User();
            user.setId(enterpriseDTO.getUserId());
            return new Enterprise(
                    enterpriseDTO.getId(),
                    enterpriseDTO.getName(),
                    enterpriseDTO.getDescription(),
                    null,
                    null,
                    user,
                    null
            );
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public EnterpriseDTO fromEnterprise(Enterprise enterprise) {
        try {
            return new EnterpriseDTO(
                    enterprise.getId(),
                    enterprise.getName(),
                    enterprise.getDescription(),
                    enterprise.getUser().getId()
            );
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<EnterpriseDTO> fromListOfEnterprises(List<Enterprise> enterprises) {
        try{
            return enterprises.stream().map(this::fromEnterprise).toList();
        }catch (Exception e){
            return Collections.emptyList();
        }
    }

    @Override
    public AdminDTO fromAdmin(Admin admin) {
        try {
            return new AdminDTO(
                    admin.getId(),
                    admin.getUserId(),
                    admin.getEnterprise().getId()
            );
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Sale fromSaleDTO(SaleDTO saleDTO) {
        try{
            Enterprise enterprise = new Enterprise();
            enterprise.setId(saleDTO.getIdEnterprise());
            return new Sale(
                    saleDTO.getId(),
                    saleDTO.getType(),
                    saleDTO.getDate(),
                    fromCustomerDTO(saleDTO.getCustomerDTO()),
                    saleDTO.getAmountHT(),
                    saleDTO.getTimbre(),
                    fromListOfTaxDTOS(saleDTO.getTaxDTOList()),
                    fromListOfTvaDTO(saleDTO.getTvaDTOList()),
                    saleDTO.getAmountTTC(),
                    enterprise
            );
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public SaleDTO fromSale(Sale sale) {
        try {
            return new SaleDTO(
                    sale.getId(),
                    sale.getType(),
                    sale.getDate(),
                    sale.getAmountHT(),
                    sale.getTimbre(),
                    sale.getAmountTTC(),
                    fromListOfTax(sale.getTaxList()),
                    fromListOfTva(sale.getTvaList()),
                    sale.getEnterprise().getId(),
                    fromCustomer(sale.getCustomer())
            );
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<SaleDTO> fromListOfSales(List<Sale> sales) {
        try{
            return sales.stream().map(this::fromSale).toList();
        }catch (Exception e){
            return Collections.emptyList();
        }
    }

    @Override
    public CustomerDTO fromCustomer(Customer customer) {
        try{
            return new CustomerDTO(
                    customer.getId(),
                    customer.getFirstname(),
                    customer.getName(),
                    customer.getPhone(),
                    customer.getEmail()
            );
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Customer fromCustomerDTO(CustomerDTO customerDTO) {
        try{
            return new Customer(
                    customerDTO.getId(),
                    customerDTO.getFirstname(),
                    customerDTO.getName(),
                    customerDTO.getPhone(),
                    customerDTO.getEmail(),
                    null
            );
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Tax fromTaxDTO(TaxDTO taxDTO){
        try{
            Sale sale = new Sale();
            sale.setId(taxDTO.getIdSale());
            return new Tax(
                    taxDTO.getId(),
                    taxDTO.getType(),
                    taxDTO.getPercentage(),
                    taxDTO.getAmount(),
                    sale
            );
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Tax> fromListOfTaxDTOS(List<TaxDTO> taxDTOList){
        try{
            return taxDTOList.stream().map(this::fromTaxDTO).toList();
        }catch (Exception e){
            return Collections.emptyList();
        }
    }

    @Override
    public TaxDTO fromTax(Tax tax){
        try {
            return new TaxDTO(
                    tax.getId(),
                    tax.getType(),
                    tax.getPercentage(),
                    tax.getAmount(),
                    tax.getSale().getId()
            );
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<TaxDTO> fromListOfTax(List<Tax> taxes){
        try {
            return taxes.stream().map(this::fromTax).toList();
        }catch (Exception e){
            return Collections.emptyList();
        }
    }

    @Override
    public Tva fromTvaDTO(TvaDTO tvaDTO){
        try{
            Sale sale = new Sale();
            sale.setId(tvaDTO.getIdSale());
            Purchase purchase = new Purchase();
            purchase.setId(tvaDTO.getIdPurchase());
            return new Tva(
                    tvaDTO.getId(),
                    tvaDTO.getPercentage(),
                    tvaDTO.getType(),
                    tvaDTO.getAmount(),
                    sale,
                    purchase
            );
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public TvaDTO fromTva(Tva tva){
        try{
            Long idSale = null;
            Long idPurchase = null;
            Sale sale = tva.getSale();
            if(sale != null){
                idSale = sale.getId();
            }
            Purchase purchase = tva.getPurchase();
            if(purchase != null ){
                idPurchase = purchase.getId();
            }


            return new TvaDTO(
                    tva.getId(),
                    tva.getPercentage(),
                    tva.getType(),
                    tva.getAmount(),
                    idSale,
                    idPurchase
            );
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Tva> fromListOfTvaDTO(List<TvaDTO> tvaDTOS){
        try{
            return tvaDTOS.stream().map(this::fromTvaDTO).toList();
        }catch (Exception e){
            return Collections.emptyList();
        }
    }

    @Override
    public List<TvaDTO> fromListOfTva(List<Tva> tvas){
        try{
            return tvas.stream().map(this::fromTva).toList();
        }catch (Exception e){
            return Collections.emptyList();
        }
    }

    @Override
    public SupplierDTO fromSupplier(Supplier supplier) {
        try {
            Long idPurchase = null;
            Purchase purchase = supplier.getPurchase();
            if(purchase != null){
                idPurchase = purchase.getId();
            }
            return new SupplierDTO(
                    supplier.getId(),
                    supplier.getFirstname(),
                    supplier.getName(),
                    supplier.getPhone(),
                    supplier.getEmail(),
                    idPurchase
            );
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Supplier fromSupplierDTO(SupplierDTO supplierDTO) {
        try {
            Purchase purchase = new Purchase();
            purchase.setId(supplierDTO.getId());
            return new Supplier(
                    supplierDTO.getId(),
                    supplierDTO.getFirstname(),
                    supplierDTO.getName(),
                    supplierDTO.getPhone(),
                    supplierDTO.getEmail(),
                    purchase
            );
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public PurchaseDTO fromPurchase(Purchase purchase) {
        try {
            List<TvaDTO> tvaList = fromListOfTva(purchase.getTvaList());
            SupplierDTO supplierDTO = fromSupplier(purchase.getSupplier());
            return new PurchaseDTO(
                    purchase.getId(),
                    purchase.getDate(),
                    supplierDTO,
                    purchase.getAmountHT(),
                    tvaList,
                    purchase.getAmountTTC(),
                    purchase.getAmountNet(),
                    purchase.getEnterprise().getId()
            );
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Purchase fromPurchaseDTO(PurchaseDTO purchaseDTO) {
        try {
            List<Tva> tvaList = fromListOfTvaDTO(purchaseDTO.getTvaList());
            Supplier supplier = fromSupplierDTO(purchaseDTO.getSupplierDTO());
            Enterprise enterprise = new Enterprise();
            enterprise.setId(purchaseDTO.getEnterpriseId());
            return new Purchase(
                    purchaseDTO.getId(),
                    purchaseDTO.getDate(),
                    supplier,
                    purchaseDTO.getAmountHT(),
                    tvaList,
                    purchaseDTO.getAmountTTC(),
                    purchaseDTO.getAmountNet(),
                    enterprise
            );
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<PurchaseDTO> fromListOfPurchases(List<Purchase> purchases) {
        try {
            return purchases.stream().map(this::fromPurchase).toList();
        }catch (Exception e){
            return Collections.emptyList();
        }
    }


}
