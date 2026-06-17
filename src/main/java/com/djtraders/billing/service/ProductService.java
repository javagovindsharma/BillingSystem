package com.djtraders.billing.service;


import com.djtraders.billing.UI.InvoiceUI;
import com.djtraders.billing.UI.ItemUI;
import com.djtraders.billing.UI.ProductUI;
import com.djtraders.billing.model.InvoiceEntity;
import com.djtraders.billing.model.ItemEntity;
import com.djtraders.billing.model.ProductEntity;
import com.djtraders.billing.repository.InvoiceRepository;
import com.djtraders.billing.repository.ItemRepository;
import com.djtraders.billing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ItemRepository itemRepository;

    public Long findNoOfRows(){
       return  invoiceRepository.count();
    }
    public void addProduct(ProductEntity p){
        productRepository.save(p);
    }
    public void saveInvoice(InvoiceEntity invoice){
        invoiceRepository.save(invoice);
    }

    public List<ProductUI> getAllProduct(){
        List<ProductEntity> list=productRepository.findAll();
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

    public List<InvoiceUI> getAllInvoice(){
       List<InvoiceEntity> listOfInvoiceEntity= invoiceRepository.findAll();
        listOfInvoiceEntity.forEach(System.out::println);
       List<InvoiceUI> listInvoiceui=new ArrayList<>();
       for(InvoiceEntity invoiceEntity:listOfInvoiceEntity){
           InvoiceUI invUI=new InvoiceUI();
           invUI.setInvoiceNo(invoiceEntity.getInvoiceNo());
           invUI.setInvoiceDate(invoiceEntity.getInvoiceDate());
           invUI.setBuyerAddress(invoiceEntity.getBuyerAddress());
           invUI.setSellerAddress(invoiceEntity.getSellerAddress());
           invUI.setListOfItems(setupItemEntityToUI(invoiceEntity.getListOfItems()));
           listInvoiceui.add(invUI);
       }
       return listInvoiceui;
    }

    private List<ItemUI> setupItemEntityToUI(List<ItemEntity> itemEntityList){
        List<ItemUI> itemUIList=new LinkedList<>();
        int count=1;
        for(ItemEntity entity:itemEntityList){
            ItemUI ui=new ItemUI();
            ui.setSn(count++);
            ui.setDiscount(entity.getDiscount());
            ui.setHsn(entity.getHsn());
            ui.setMrp(entity.getMrp());
            ui.setRate(entity.getRate());
            ui.setAmount(entity.getAmount());
            ui.setName(entity.getName());
            ui.setQty(entity.getQty());
           // ui.setAmount(entity.getAmount());
            itemUIList.add(ui);
        }
        return itemUIList;
    }
}
