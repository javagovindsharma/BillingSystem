package com.djtraders.billing.UI.create_invoice;

import com.djtraders.billing.CssStyle.InvoicePDF;
import com.djtraders.billing.model.Item;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.djtraders.billing.CssStyle.AppStyle.*;

public class InvoiceEntryForm extends Application {

    @Override
    public void start(Stage stage) {
        ProductSection productSection =
                new ProductSection();
        VBox root = new VBox(
                20,
                InvoiceHeader.create(),

                new HBox(
                        20,
                        SellerPanel.create(),
                        BuyerPanel.create()
                ),

                createInvoiceInfoSection(),

                productSection.getView(),

                ActionButtons.create(
                        createSaveButton(),
                        createPrintButton(stage)
                )
        );

        root.setPadding(new Insets(20));
        root.setStyle(getRootStyle());
        Scene scene =
                new Scene(root, 600, 800);

        stage.setTitle("RJ Traders Billing Software");
        stage.setScene(scene);
        stage.show();
    }

    private HBox createInvoiceInfoSection() {

        TextField invoiceNo =
                new TextField(
                        "INV-" + System.currentTimeMillis()
                );

        invoiceNo.setDisable(true);

        DatePicker datePicker =
                new DatePicker(LocalDate.now());

        HBox box = new HBox(
                15,
                new Label("Invoice No"),
                invoiceNo,
                new Label("Date"),
                datePicker
        );

        return box;
    }

    private Button createSaveButton() {

        Button btn =
                new Button("Save Invoice");
        btn.setStyle(buttonStyle());
        btn.setOnMouseEntered(e ->
                btn.setStyle(getAddButtonHoverStyle()));
        btn.setOnMouseExited(e ->
                btn.setStyle(buttonStyle()));

        btn.setOnAction(e -> {

            Alert alert =
                    new Alert(
                            Alert.AlertType.INFORMATION
                    );

            alert.setContentText(
                    "Invoice Saved Successfully"
            );

            alert.showAndWait();
        });

        return btn;
    }

    private Button createPrintButton(
            Stage stage) {

        Button btn =
                new Button("Print Invoice");
        btn.setStyle(buttonStyle());
        btn.setOnMouseEntered(e ->
                btn.setStyle(getAddButtonHoverStyle()));
        btn.setOnMouseExited(e ->
                btn.setStyle(buttonStyle()));


        btn.setOnAction(e -> {

            printPdf();
        });

        return btn;
    }

    public void printPdf(){
        List<Item> products = new ArrayList<>();

        products.add(new Item(
                1,
                "ZEEBA SUPER BASMATI RICE",
                "10063092",
                0,
                810,
                99,
                0
        ));

        products.add(new Item(
                2,
                "ZEEBA XXXL BIRYANI BASMATI RICE",
                "10063092",
                0,
                300,
                103,
                0
        ));

        products.add(new Item(
                3,
                "ZEEBA TIBAR BASMATI RICE",
                "10064000",
                0,
                450,
                81,
                0
        ));

        // CALL PDF GENERATOR
        String sel= "R.J. TRADERS\nDILDAR NAGAR BAZAR\nMob: 9161490408\nGST: 09AYMPJ1555D2ZE";
        String buy= "LAXMAN RAM AND COMPANY\nMAHAJAN TOLI\nMob: 7355682305\nGST: 09CBXPK8650G2ZV";
        InvoicePDF.generate(sel,buy,products);

        System.out.println("Invoice Generated Successfully!");
    }
}