package com.djtraders.billing.controller;

import com.djtraders.billing.UI.InvoiceUI;
import com.djtraders.billing.UI.ItemUI;
import com.djtraders.billing.UI.ProductUI;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditInvoiceController implements Initializable {
    @FXML
    private TextField txtInvoiceNo;
    @FXML
    private TextArea txtSeller;
    @FXML
    private TextArea txtBuyer;
    @FXML
    private TextField txtDate;

    @FXML
    private TableView<ItemUI>  itemTable;

    @FXML
    private TableColumn<ItemUI, Integer> snCol;

    @FXML
    private TableColumn<ItemUI, String> nameCol;

    @FXML
    private TableColumn<ItemUI, String> hsnCol;

    @FXML
    private TableColumn<ItemUI, Double> mrpCol;

    @FXML
    private TableColumn<ItemUI, Double> qtyCol;

    @FXML
    private TableColumn<ItemUI, Double> rateCol;

    @FXML
    private TableColumn<ItemUI, Double> discountCol;

    @FXML
    private TableColumn<ItemUI, Double> amountCol;


    private InvoiceUI invoice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        snCol.setCellValueFactory(
                new PropertyValueFactory<>("sn"));

        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        hsnCol.setCellValueFactory(
                new PropertyValueFactory<>("hsn"));

        qtyCol.setCellValueFactory(
                new PropertyValueFactory<>("qty"));

        rateCol.setCellValueFactory(
                new PropertyValueFactory<>("rate"));

        mrpCol.setCellValueFactory(
                new PropertyValueFactory<>("mrp"));

        discountCol.setCellValueFactory(
                new PropertyValueFactory<>("discount"));

        amountCol.setCellValueFactory(
                new PropertyValueFactory<>("amount"));
    }

    public void setInvoice(InvoiceUI invoice) {
        this.invoice = invoice;
        txtInvoiceNo.setText(invoice.getInvoiceNo());
        txtSeller.setText(invoice.getSellerAddress());
        txtBuyer.setText(invoice.getBuyerAddress());
        txtDate.setText(invoice.getInvoiceDate().toString());
        itemTable.setItems(FXCollections.observableArrayList(invoice.getListOfItems()));
    }

    @FXML
    private void saveInvoice() {
        invoice.setSellerAddress(txtSeller.getText());
        invoice.setBuyerAddress(txtBuyer.getText());
        invoice.setInvoiceDate(LocalDate.parse(txtDate.getText()));

        Stage stage =
                (Stage) txtDate.getScene().getWindow();

        stage.close();
        System.out.println("Updated Successfully");
    }
    @FXML
    private void closeWindow() {
        Stage stage =
                (Stage) txtDate.getScene().getWindow();

        stage.close();
    }
}