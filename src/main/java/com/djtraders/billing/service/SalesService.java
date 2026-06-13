package com.djtraders.billing.service;

import com.djtraders.billing.model.Product;
import com.djtraders.billing.model.Sales;
import com.djtraders.billing.repository.ProductRepository;
import com.djtraders.billing.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SalesService {
    private final SalesRepository salesRepo;
    private final ProductRepository productRepo;

    public SalesService(SalesRepository salesRepo, ProductRepository productRepo) {
        this.salesRepo = salesRepo;
        this.productRepo = productRepo;
    }

    public void sellProduct(Long productId,
                            String customer,
                            int qty,
                            double paid){

        Product product = productRepo.findById(productId)
                .orElseThrow();

        if(product.getQuantity() < qty){
            throw new RuntimeException("Stock not available");
        }

        product.setQuantity(product.getQuantity() - qty);

        double total = qty * product.getSellingPrice();
        double pending = total - paid;

        Sales sale = new Sales();
        sale.setCustomerName(customer);
        sale.setProduct(product);
        sale.setQuantity(qty);
        sale.setTotalAmount(total);
        sale.setPaidAmount(paid);
        sale.setPendingAmount(pending);
        sale.setDate(LocalDate.now());

        productRepo.save(product);
        salesRepo.save(sale);
    }

    // 🔥 Customer Wise Sales
    public List<Sales> getByCustomer(String name){
        return salesRepo.findByCustomerName(name);
    }

    public double getCustomerTotal(String name){
        return salesRepo.findByCustomerName(name)
                .stream()
                .mapToDouble(Sales::getTotalAmount)
                .sum();
    }

    public double getCustomerPending(String name){
        return salesRepo.findByCustomerName(name)
                .stream()
                .mapToDouble(Sales::getPendingAmount)
                .sum();
    }
    public double getTotalSalesAmount() {
        return 0;
    }

    public double getTotalPendingAmount() {
        return 0;
    }

    public long getTotalCustomers() {
        return 0;
    }
    public void addSale(String customerName,
                        Product product,
                        int qty,
                        double paidAmount){

       /* if(product.getQuantity() < qty){
            throw new RuntimeException("Not enough stock!");
        }*/

        product.setQuantity(
                product.getQuantity() - qty);

        Sales sale = new Sales();
              sale.setCustomerName(customerName);
              sale.setProduct(product);
              sale.setQuantity(qty);
              sale.setPaidAmount(paidAmount);
              sale.setPendingAmount(paidAmount);

        salesRepo.save(sale);
    }

    public double getMonthlySales(){
        return 0;
    }
    public double getYearlySales(){
        return 0;
    }
    public double getReceivedAmount(){
        return 0;
    }
    public double getPendingAmount(){
        return 0;
    }
}
