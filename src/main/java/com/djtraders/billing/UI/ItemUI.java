package com.djtraders.billing.UI;

import javafx.beans.property.*;

public class ItemUI {

    private final IntegerProperty sn =
            new SimpleIntegerProperty();

    private final StringProperty name =
            new SimpleStringProperty("");

    private final StringProperty hsn =
            new SimpleStringProperty("");

    private final DoubleProperty mrp =
            new SimpleDoubleProperty();

    private final DoubleProperty qty =
            new SimpleDoubleProperty();

    private final DoubleProperty rate =
            new SimpleDoubleProperty();

    private final DoubleProperty discount =
            new SimpleDoubleProperty();

    private final DoubleProperty amount =
            new SimpleDoubleProperty();

    // SN
    public int getSn() {
        return sn.get();
    }

    public void setSn(int sn) {
        this.sn.set(sn);
    }

    public IntegerProperty snProperty() {
        return sn;
    }

    // Name
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    // HSN
    public String getHsn() {
        return hsn.get();
    }

    public void setHsn(String hsn) {
        this.hsn.set(hsn);
    }

    public StringProperty hsnProperty() {
        return hsn;
    }

    // MRP
    public double getMrp() {
        return mrp.get();
    }

    public void setMrp(double mrp) {
        this.mrp.set(mrp);
    }

    public DoubleProperty mrpProperty() {
        return mrp;
    }

    // Quantity
    public double getQty() {
        return qty.get();
    }

    public void setQty(double qty) {
        this.qty.set(qty);
    }

    public DoubleProperty qtyProperty() {
        return qty;
    }

    // Rate
    public double getRate() {
        return rate.get();
    }

    public void setRate(double rate) {
        this.rate.set(rate);
    }

    public DoubleProperty rateProperty() {
        return rate;
    }

    // Discount
    public double getDiscount() {
        return discount.get();
    }

    public void setDiscount(double discount) {
        this.discount.set(discount);
    }

    public DoubleProperty discountProperty() {
        return discount;
    }

    // Amount
    public double getAmount() {
        return amount.get();
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public DoubleProperty amountProperty() {
        return amount;
    }
}