package com.djtraders.billing.UI;

import com.djtraders.billing.model.Product;
import com.djtraders.billing.service.ProductService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ProductUI {

    private final ProductService productService;
    private final MainLayout mainLayout;

    public ProductUI(ProductService productService,
                     MainLayout mainLayout) {
        this.productService = productService;
        this.mainLayout = mainLayout;
    }

    public BorderPane getView() {

        // ===== INPUT FIELDS =====

        TextField nameField = new TextField();
        nameField.setPromptText("Product Name");

        TextField purchaseField = new TextField();
        purchaseField.setPromptText("Purchase Price");

        TextField sellingField = new TextField();
        sellingField.setPromptText("Selling Price");

        TextField qtyField = new TextField();
        qtyField.setPromptText("Quantity Received");

        Button addBtn = new Button("Add To Store");
        Button backBtn = new Button("Back");

        // ===== TABLE =====

        TableView<Product> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Product,String> nameCol =
                new TableColumn<>("Product");
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        TableColumn<Product,String> companyCol =
                new TableColumn<>("Company");
        companyCol.setCellValueFactory(
                new PropertyValueFactory<>("companyName"));

        TableColumn<Product,Integer> qtyCol =
                new TableColumn<>("Stock");
        qtyCol.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));

        table.getColumns().addAll(
                nameCol, companyCol, qtyCol);

        refreshTable(table);

        // ===== ADD PRODUCT ACTION =====

        addBtn.setOnAction(e -> {

            Product p = new Product();
            p.setName(nameField.getText());
            p.setPurchasePrice(
                    Double.parseDouble(purchaseField.getText()));
            p.setSellingPrice(
                    Double.parseDouble(sellingField.getText()));
            p.setQuantity(
                    Integer.parseInt(qtyField.getText()));

            productService.addProduct(p);

            refreshTable(table);

            nameField.clear();
            purchaseField.clear();
            sellingField.clear();
            qtyField.clear();
        });

        // ===== BACK BUTTON =====

        backBtn.setOnAction(e ->
                mainLayout.showDashboard());

        VBox form = new VBox(10,
                nameField,
                purchaseField,
                sellingField,
                qtyField,
                addBtn,
                backBtn);

        form.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setLeft(form);
        root.setCenter(table);

        return root;
    }

    private void refreshTable(TableView<Product> table) {
        table.getItems().setAll(
                productService.getAll());
    }
}
