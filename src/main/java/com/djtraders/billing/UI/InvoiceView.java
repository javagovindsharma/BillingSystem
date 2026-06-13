package com.djtraders.billing.UI;

import com.djtraders.billing.model.Item;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class InvoiceView extends Application {

    @Override
    public void start(Stage stage) {

        Label title = new Label("TAX INVOICE");
        title.setStyle("-fx-font-size:22px;-fx-font-weight:bold;");

        TextArea seller = new TextArea(
                "CHHOTELAL KIRANA STORE\n"
                        + "JAUNPUR\n"
                        + "GST: XXXXXXX");
        seller.setPrefHeight(80);

        TextArea buyer = new TextArea(
                "RJ TRADERS\n"
                        + "DILDARNAGAR\n"
                        + "GST: XXXXXXX");
        buyer.setPrefHeight(80);

        HBox parties = new HBox(10, seller, buyer);

        TextField txtInvoiceNo = new TextField();
        txtInvoiceNo.setPromptText("Invoice No");

        DatePicker datePicker = new DatePicker();

        HBox invoiceInfo = new HBox(10,
                new Label("Invoice No"),
                txtInvoiceNo,
                new Label("Date"),
                datePicker);

        TableView<Item> table = new TableView<>();

        TableColumn<Item, String> particular =
                new TableColumn<>("Particular");

        TableColumn<Item, String> hsn =
                new TableColumn<>("HSN");

        TableColumn<Item, Integer> qty =
                new TableColumn<>("Qty");

        TableColumn<Item, Double> rate =
                new TableColumn<>("Rate");

        TableColumn<Item, Double> amount =
                new TableColumn<>("Amount");

        particular.setCellValueFactory(
                new PropertyValueFactory<>("particular"));

        hsn.setCellValueFactory(
                new PropertyValueFactory<>("hsn"));

        qty.setCellValueFactory(
                new PropertyValueFactory<>("qty"));

        rate.setCellValueFactory(
                new PropertyValueFactory<>("rate"));

        amount.setCellValueFactory(
                new PropertyValueFactory<>("amount"));

        table.getColumns().addAll(
                particular, hsn, qty, rate, amount);

        ObservableList<Item> items =
                FXCollections.observableArrayList(
                        new Item("Rice", "10063020", 10, 50),
                        new Item("Sugar", "10064000", 5, 45)
                );

        table.setItems(items);

        Label totalLabel = new Label("Grand Total : 725");

        Button saveBtn = new Button("Save");
        saveBtn.setStyle(
                "-fx-background-color:#4CAF50;"
                        + "-fx-text-fill:white;");

        Button printBtn = new Button("Print");
        printBtn.setStyle(
                "-fx-background-color:#2196F3;"
                        + "-fx-text-fill:white;");

        saveBtn.setOnAction(e ->
                new Alert(Alert.AlertType.INFORMATION,
                        "Invoice Saved")
                        .showAndWait());

        printBtn.setOnAction(e -> {

            PrinterJob job =
                    PrinterJob.createPrinterJob();

            if (job != null) {
                job.printPage(table.getScene().getRoot());
                job.endJob();
            }
        });

        HBox buttons = new HBox(10,
                saveBtn, printBtn);

        VBox root = new VBox(
                10,
                title,
                parties,
                invoiceInfo,
                table,
                totalLabel,
                buttons
        );

        root.setStyle(
                "-fx-padding:15;" +
                        "-fx-background-color:white;");

        Scene scene =
                new Scene(root, 1000, 700);

        stage.setScene(scene);
        stage.setTitle("Invoice");
        stage.show();
    }
}