package com.djtraders.billing.UI;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class InvoiceUI {

    private final SimpleStringProperty invoiceId;
    private final SimpleStringProperty companyName;
    private final SimpleStringProperty email;
    private final SimpleStringProperty status;
    private final SimpleDoubleProperty amount;

    public InvoiceUI(String invoiceId, String companyName,
                   String email, String status, double amount) {
        this.invoiceId = new SimpleStringProperty(invoiceId);
        this.companyName = new SimpleStringProperty(companyName);
        this.email = new SimpleStringProperty(email);
        this.status = new SimpleStringProperty(status);
        this.amount = new SimpleDoubleProperty(amount);
    }

    public String getInvoiceId() { return invoiceId.get(); }
    public String getCompanyName() { return companyName.get(); }
    public String getEmail() { return email.get(); }
    public String getStatus() { return status.get(); }
    public double getAmount() { return amount.get(); }

    // Setters
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }
}