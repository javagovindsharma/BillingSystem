package com.djtraders.billing.UI;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceUI {

    private final SimpleStringProperty invoiceNo = new SimpleStringProperty();
    private final SimpleStringProperty sellerAddress = new SimpleStringProperty();
    private final SimpleStringProperty buyerAddress = new SimpleStringProperty();
    private final SimpleStringProperty grandTotal = new SimpleStringProperty();
    private final SimpleObjectProperty<LocalDate> invoiceDate =
            new SimpleObjectProperty<>();

    private List<ItemUI> listOfItems = new ArrayList<>();

    // Invoice No
    public String getInvoiceNo() {
        return invoiceNo.get();
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo.set(invoiceNo);
    }

    // Seller Address
    public String getSellerAddress() {
        return sellerAddress.get();
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress.set(sellerAddress);
    }

    // Buyer Address
    public String getBuyerAddress() {
        return buyerAddress.get();
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress.set(buyerAddress);
    }

    // Invoice Date
    public LocalDate getInvoiceDate() {
        return invoiceDate.get();
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate.set(invoiceDate);
    }

    // Item List
    public List<ItemUI> getListOfItems() {
        return listOfItems;
    }

    public void setListOfItems(List<ItemUI> listOfItems) {
        this.listOfItems = listOfItems;
    }

    // Buyer Address
    public String getGrandTotal() {
        return grandTotal.get();
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal.set(grandTotal);
    }
}