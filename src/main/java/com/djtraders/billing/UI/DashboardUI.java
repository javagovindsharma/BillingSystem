package com.djtraders.billing.UI;

import com.djtraders.billing.UI.create_invoice.InvoiceEntryForm;
import com.djtraders.billing.model.Product;
import com.djtraders.billing.model.Sales;
import com.djtraders.billing.service.ProductService;
import com.djtraders.billing.service.SalesService;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.djtraders.billing.CssStyle.AppStyle.*;

public class DashboardUI {

    private final ProductService productService;
    private final SalesService salesService;
    private final MainLayout mainLayout;

    public DashboardUI(ProductService productService,
                       SalesService salesService,
                       MainLayout mainLayout) {

        this.productService = productService;
        this.salesService = salesService;
        this.mainLayout = mainLayout;
    }

    public VBox getView() {

        Label heading = new Label("R.J TRADERS - INVOICE DASHBOARD");
        heading.setStyle("""
                        -fx-font-size: 30px;
                        -fx-font-weight: bold;
                        -fx-text-fill: white;
                        """);

        VBox totalProductCard = createCard(
                "Total Products",
                String.valueOf(productService.totalProducts()));

        VBox monthlySalesCard = createCard(
                "Monthly Sales",
                "₹ " + salesService.getMonthlySales());

        VBox yearlySalesCard = createCard(
                "Yearly Sales",
                "₹ " + salesService.getYearlySales());

        VBox receivedCard = createCard(
                "Received Amount",
                "₹ " + salesService.getReceivedAmount());

        VBox pendingCard = createCard(
                "Pending Amount",
                "₹ " + salesService.getPendingAmount());

        GridPane cardGrid = new GridPane();
        cardGrid.setHgap(20);
        cardGrid.setVgap(20);

        cardGrid.add(totalProductCard, 0, 0);
        cardGrid.add(monthlySalesCard, 1, 0);
        cardGrid.add(yearlySalesCard, 2, 0);

        cardGrid.add(receivedCard, 0, 1);
        cardGrid.add(pendingCard, 1, 1);



        Button createInvoiceBtn = new Button("Create Invoice");
        Button viewInvoiceBtn = new Button("View Invoices");

        createInvoiceBtn.setStyle(buttonStyle());
        viewInvoiceBtn.setStyle(buttonStyle());

        createInvoiceBtn.setPrefWidth(180);
        viewInvoiceBtn.setPrefWidth(180);

        createInvoiceBtn.setOnMouseEntered(e ->
                createInvoiceBtn.setStyle(getAddButtonHoverStyle()));

        createInvoiceBtn.setOnMouseExited(e ->
                createInvoiceBtn.setStyle(buttonStyle()));
        createInvoiceBtn.setOnAction(e -> {
            try {
                new InvoiceEntryForm().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        viewInvoiceBtn.setOnMouseEntered(e ->
                viewInvoiceBtn.setStyle(getAddButtonHoverStyle()));

        viewInvoiceBtn.setOnMouseExited(e ->
                viewInvoiceBtn.setStyle(buttonStyle()));

        HBox buttonBox = new HBox(20,
                createInvoiceBtn,
                viewInvoiceBtn);

        buttonBox.setAlignment(Pos.BOTTOM_LEFT);

        VBox root = new VBox(30,
                heading,
                cardGrid,
                buttonBox);

        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));

        root.setStyle("""
                    -fx-background-image: url('/dashboard.png');
                    -fx-background-size: cover;
                    -fx-background-position: center;
                """);

        return root;
    }

    private VBox createCard(String title, String value) {

        Label titleLabel = new Label(title);
        titleLabel.setStyle("""
                -fx-font-size: 14px;
                -fx-text-fill: #555555;
                """);

        Label valueLabel = new Label(value);
        valueLabel.setStyle("""
                -fx-font-size: 22px;
                -fx-font-weight: bold;
                -fx-text-fill: #1b1852;
                """);

        VBox card = new VBox(10,
                titleLabel,
                valueLabel);

        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));

        card.setStyle(getCardStyle());
        card.setOnMouseEntered(e -> {
            card.setStyle(getCardStyleOnMouseEntered());

            valueLabel.setStyle("""
            -fx-font-size: 22px;
            -fx-font-weight: bold;
            -fx-text-fill: #2563eb;
            """);
        });

        card.setOnMouseExited(e -> {
            card.setStyle(getCardStyleOnMouseExits());

            valueLabel.setStyle("""
            -fx-font-size: 22px;
            -fx-font-weight: bold;
            -fx-text-fill: #1b1852;
            """);
        });

        return card;
    }


    public static class ProductUI {

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

    public static class SalesUI {

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
}