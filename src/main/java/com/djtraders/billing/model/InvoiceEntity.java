package com.djtraders.billing.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoice")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String invoiceNo;

    @Override
    public String toString() {
        return "InvoiceEntity{" +
                "id=" + id +
                ", invoiceNo='" + invoiceNo + '\'' +
                ", sellerAddress='" + sellerAddress + '\'' +
                ", buyerAddress='" + buyerAddress + '\'' +
                ", invoiceDate=" + invoiceDate +
                ", grandTotal=" + grandTotal +
                ", listOfItems=" + listOfItems +
                '}';
    }

    @Column(length = 2000)
    private String sellerAddress;

    @Column(length = 2000)
    private String buyerAddress;

    private LocalDate invoiceDate;

    private Double grandTotal;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "invoice_id")
    private List<ItemEntity> listOfItems = new ArrayList<>();

    // ==========================
    // Auto Generate Invoice No
    // ==========================
    @PostPersist
    public void generateInvoiceNo() {
        if (invoiceNo == null) {
            String datePart = invoiceDate.toString().replace("-", "");
            this.invoiceNo = "CH" + datePart + id;
        }
    }

    // ==========================
    // Getter Setter
    // ==========================

    public Long getId() {
        return id;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public List<ItemEntity> getListOfItems() {
        return listOfItems;
    }

    public void setListOfItems(List<ItemEntity> listOfItems) {
        this.listOfItems = listOfItems;
    }
    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }
}