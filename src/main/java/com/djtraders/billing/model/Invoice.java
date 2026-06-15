package com.djtraders.billing.model;

import com.djtraders.billing.UI.ProductUI;

import java.time.LocalDate;
import java.util.List;

public class Invoice {

    private String invoiceNo;
    private LocalDate invoiceDate;

    private String sellerDetails;
    private String buyerDetails;

    private List<ProductUI> products;

    private double grandTotal;

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getSellerDetails() {
        return sellerDetails;
    }

    public void setSellerDetails(String sellerDetails) {
        this.sellerDetails = sellerDetails;
    }

    public String getBuyerDetails() {
        return buyerDetails;
    }

    public void setBuyerDetails(String buyerDetails) {
        this.buyerDetails = buyerDetails;
    }

    public List<ProductUI> getProducts() {
        return products;
    }

    public void setProducts(List<ProductUI> products) {
        this.products = products;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }
}