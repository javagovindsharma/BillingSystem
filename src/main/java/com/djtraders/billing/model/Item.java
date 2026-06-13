package com.djtraders.billing.model;

public class Item {

    private String particular;
    private String hsn;
    private int qty;
    private double rate;
    private double amount;

    public Item(String particular,
                String hsn,
                int qty,
                double rate) {

        this.particular = particular;
        this.hsn = hsn;
        this.qty = qty;
        this.rate = rate;
        this.amount = qty * rate;
    }

    public String getParticular() {
        return particular;
    }

    public String getHsn() {
        return hsn;
    }

    public int getQty() {
        return qty;
    }

    public double getRate() {
        return rate;
    }

    public double getAmount() {
        return amount;
    }
}