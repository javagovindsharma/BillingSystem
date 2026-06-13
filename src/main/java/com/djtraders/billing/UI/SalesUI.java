package com.djtraders.billing.UI;

import com.djtraders.billing.model.Product;
import com.djtraders.billing.model.Sales;
import com.djtraders.billing.service.ProductService;
import com.djtraders.billing.service.SalesService;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SalesUI {

    private final ProductService productService;
    private final SalesService salesService;
    private final MainLayout mainLayout;

    public SalesUI(ProductService p,
                   SalesService s,
                   MainLayout mainLayout) {

        this.productService = p;
        this.salesService = s;
        this.mainLayout = mainLayout;
    }

    public BorderPane getView(){

        // ===== INPUT =====

        TextField customerField =
                new TextField();
        customerField.setPromptText("Customer Name");

        ComboBox<Product> productBox =
                new ComboBox<>();
        productBox.getItems()
                .addAll(productService.getAll());
        productBox.setPromptText("Select Product");

        TextField qtyField =
                new TextField();
        qtyField.setPromptText("Quantity");

        TextField paidField =
                new TextField();
        paidField.setPromptText("Paid Amount");

        Button saleBtn =
                new Button("Send Product + Generate Invoice");

        Button backBtn =
                new Button("Back");

        // ===== TABLE =====

        TableView<Sales> table =
                new TableView<>();

        TableColumn<Sales,String> productCol =
                new TableColumn<>("Product");
        productCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue()
                                .getProduct()
                                .getName()));

        TableColumn<Sales,Integer> qtyCol =
                new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));

        TableColumn<Sales,Double> totalCol =
                new TableColumn<>("Total");
        totalCol.setCellValueFactory(
                new PropertyValueFactory<>("totalAmount"));

        TableColumn<Sales,Double> paidCol =
                new TableColumn<>("Paid");
        paidCol.setCellValueFactory(
                new PropertyValueFactory<>("paidAmount"));

        TableColumn<Sales,Double> pendingCol =
                new TableColumn<>("Pending");
        pendingCol.setCellValueFactory(
                new PropertyValueFactory<>("pendingAmount"));

        table.getColumns().addAll(
                productCol, qtyCol,
                totalCol, paidCol,
                pendingCol);

        // ===== SALE ACTION =====

        saleBtn.setOnAction(e -> {

            String customer =
                    customerField.getText();

            Product product =
                    productBox.getValue();

            int qty =
                    Integer.parseInt(qtyField.getText());

            double paid =
                    Double.parseDouble(paidField.getText());

            salesService.addSale(
                    customer,
                    product,
                    qty,
                    paid);

            table.getItems().setAll(
                    salesService.getByCustomer(customer));

            double total =
                    product.getSellingPrice() * qty;

            double pending =
                    total - paid;

            Alert alert =
                    new Alert(Alert.AlertType.INFORMATION);

            alert.setHeaderText("Invoice Generated");
            alert.setContentText(
                    "Customer: " + customer +
                    "\nProduct: " + product.getName() +
                    "\nQty: " + qty +
                    "\nTotal: " + total +
                    "\nPaid: " + paid +
                    "\nPending: " + pending
            );

            alert.show();
        });

        // ===== BACK =====

        backBtn.setOnAction(e ->
                mainLayout.showDashboard());

        VBox left = new VBox(10,
                customerField,
                productBox,
                qtyField,
                paidField,
                saleBtn,
                backBtn);

        left.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setLeft(left);
        root.setCenter(table);

        return root;
    }
}
