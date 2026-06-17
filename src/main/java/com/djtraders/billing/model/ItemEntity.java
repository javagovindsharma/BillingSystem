package com.djtraders.billing.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Items")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int sn;

    @Column(nullable = false)
    private String name;

    private String hsn;

    private double mrp;

    private double qty;

    private double rate;

    private double discount;

    private double amount;
    public ItemEntity() {
        // Hibernate ke liye
    }
    public ItemEntity(int sn,
                         String name,
                         String hsn,
                         double mrp,
                         double qty,
                         double rate,
                         double discount,double amount) {
        this.sn = sn;
        this.name = name;
        this.hsn = hsn;
        this.mrp = mrp;
        this.qty = qty;
        this.rate = rate;
        this.discount = discount;
        this.amount= amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}