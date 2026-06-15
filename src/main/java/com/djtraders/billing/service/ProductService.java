package com.djtraders.billing.service;


import com.djtraders.billing.model.ProductEntity;
import com.djtraders.billing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public void addProduct(ProductEntity p){
        repo.save(p);
    }

    public List<ProductEntity> getAll(){
        return repo.findAll();
    }

    public long totalProducts(){
        return repo.count();
    }

    public int totalStock() {
        /*return repo.findAll()
                .stream()
                .mapToInt(Product::getQuantity)
                .sum();*/
        return 0;
    }
    public long getLowStockCount() {
        return 0;
    }
}
