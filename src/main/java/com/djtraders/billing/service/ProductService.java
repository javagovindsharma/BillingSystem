package com.djtraders.billing.service;


import com.djtraders.billing.UI.ProductUI;
import com.djtraders.billing.model.ProductEntity;
import com.djtraders.billing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public void addProduct(ProductEntity p){
        repo.save(p);
    }

    public List<ProductUI> getAll(){
        List<ProductEntity> list=repo.findAll();
        List<ProductUI> listProduct=new LinkedList<>();
        for(ProductEntity p:list){
            ProductUI ui=new ProductUI();
            ui.setProductName(p.getName());
            ui.setRate(p.getRate());
            ui.setDiscount(p.getDiscount());
            ui.setHsn(p.getHsn());
            ui.setMrp(p.getMrp());
            ui.setProductId(p.getId());
            listProduct.add(ui);
        }
        return listProduct;
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
