package com.djtraders.billing.UI;

import com.djtraders.billing.service.ProductService;
import com.djtraders.billing.service.SalesService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
                createInvoiceBtn.setStyle("""
                -fx-background-color: #2563eb;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-padding: 10 20 10 20;
                -fx-background-radius: 8;
                """));

        createInvoiceBtn.setOnMouseExited(e ->
                createInvoiceBtn.setStyle(buttonStyle()));
        createInvoiceBtn.setOnAction(e -> {
            try {
                new InvoiceView().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        viewInvoiceBtn.setOnMouseEntered(e ->
                viewInvoiceBtn.setStyle("""
                -fx-background-color: #2563eb;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-padding: 10 20 10 20;
                -fx-background-radius: 8;
                """));

        viewInvoiceBtn.setOnMouseExited(e ->
                viewInvoiceBtn.setStyle(buttonStyle()));

        HBox buttonBox = new HBox(20,
                createInvoiceBtn,
                viewInvoiceBtn);

        buttonBox.setAlignment(Pos.CENTER);

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

        card.setStyle("""
                -fx-background-color: white;
                -fx-border-color: #d9d9d9;
                -fx-border-width: 1;
                -fx-background-radius: 10;
                -fx-border-radius: 10;
                -fx-min-width: 220;
                -fx-min-height: 120;
                """);
        card.setOnMouseEntered(e -> {
            card.setStyle("""
            -fx-background-color: #dbeafe;
            -fx-border-color: #2563eb;
            -fx-border-width: 2;
            -fx-background-radius: 15;
            -fx-border-radius: 15;
            -fx-min-width: 220;
            -fx-min-height: 120;
            """);

            valueLabel.setStyle("""
            -fx-font-size: 22px;
            -fx-font-weight: bold;
            -fx-text-fill: #2563eb;
            """);
        });

        card.setOnMouseExited(e -> {
            card.setStyle("""
            -fx-background-color: white;
            -fx-border-color: #d9d9d9;
            -fx-border-width: 1;
            -fx-background-radius: 15;
            -fx-border-radius: 15;
            -fx-min-width: 220;
            -fx-min-height: 120;
            """);

            valueLabel.setStyle("""
            -fx-font-size: 22px;
            -fx-font-weight: bold;
            -fx-text-fill: #1b1852;
            """);
        });

        return card;
    }

    private String buttonStyle() {
        return """
                -fx-background-color: #1b1852;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-padding: 10 20 10 20;
                -fx-background-radius: 8;
                """;
    }
}