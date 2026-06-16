package com.djtraders.billing.UI;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProductUI {


    private final SimpleLongProperty productId;
    private final SimpleStringProperty productName;
    private final SimpleStringProperty hsn;

    private final SimpleIntegerProperty rate;
    private final SimpleDoubleProperty mrp;
    private final SimpleIntegerProperty discount;

    public ProductUI() {
        this.productId=new SimpleLongProperty();
        this.productName = new SimpleStringProperty();
        this.hsn = new SimpleStringProperty();
        this.rate = new SimpleIntegerProperty();
        this.mrp = new SimpleDoubleProperty();
        this.discount = new SimpleIntegerProperty();
    }

    public ProductUI(Long productId,String productName,
                     String hsn,
                     Integer rate,
                     double mrp,
                     Integer discount) {
        this.productId =
                new SimpleLongProperty(productId);
        this.productName =
                new SimpleStringProperty(productName);

        this.hsn = new SimpleStringProperty(hsn);

        this.rate = new SimpleIntegerProperty(rate);

        this.mrp = new SimpleDoubleProperty(mrp);

        this.discount = new SimpleIntegerProperty(discount);
    }

    public String getProductName() {
        return productName.get();
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public String getHsn() {
        return hsn.get();
    }

    public void setHsn(String hsn) {
        this.hsn.set(hsn);
    }

    public double getRate() {
        return rate.get();
    }

    public void setRate(Integer rate) {
        this.rate.set(rate);
    }

    public double getMrp() {
        return mrp.get();
    }

    public void setMrp(double mrp) {
        this.mrp.set(mrp);
    }

    public double getDiscount() {
        return discount.get();
    }

    public void setDiscount(Integer discount) {
        this.discount.set(discount);
    }

    public Long getProductId() {
        return productId.get();
    }

    public void setProductId(Long productId) {
        this.productId.set(productId);
    }
}