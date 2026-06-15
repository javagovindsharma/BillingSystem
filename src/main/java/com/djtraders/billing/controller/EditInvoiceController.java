package com.djtraders.billing.controller;

import com.djtraders.billing.UI.InvoiceUI;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditInvoiceController {

    @FXML
    private TextField txtCompany;

    @FXML
    private TextField txtEmail;

    private InvoiceUI invoice;

    public void setInvoice(InvoiceUI invoice) {

        this.invoice = invoice;

        txtCompany.setText(invoice.getCompanyName());
        txtEmail.setText(invoice.getEmail());
    }

    @FXML
    private void saveInvoice() {
        invoice.setCompanyName(txtCompany.getText());
        invoice.setEmail(txtEmail.getText());
        Stage stage =
                (Stage) txtCompany.getScene().getWindow();

        stage.close();
        System.out.println("Updated Successfully");
    }
    @FXML
    private void closeWindow() {
        Stage stage =
                (Stage) txtCompany.getScene().getWindow();

        stage.close();
    }
}