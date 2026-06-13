package com.djtraders.billing.UI.create_invoice;

import com.djtraders.billing.CssStyle.AppStyle;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.Map;

import static com.djtraders.billing.CssStyle.AppStyle.buttonStyle;
import static com.djtraders.billing.CssStyle.AppStyle.getAddButtonHoverStyle;

public class ProductSection {

    private final VBox root = new VBox(10);

    private final VBox rows = new VBox(5);


    private final Map<String, Double> productRates =
            new HashMap<>();
    private final Label grandTotalLabel =
            new Label("Grand Total : ₹0.00");
    public ProductSection() {

        loadProducts();
        ScrollPane scrollPane = new ScrollPane(rows);


        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER);

        scrollPane.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("""
        -fx-background:white;
        -fx-border-color:#dcdcdc;
        -fx-background-radius:5;
        -fx-border-radius:5;
        """);
        grandTotalLabel.setStyle(
                AppStyle.getGrandTotalStyle()
        );

        root.getChildren().addAll(
                createHeader(),
                scrollPane,
                createAddButton(),
                grandTotalLabel
        );

        addRow();
    }

    private void loadProducts() {

        productRates.put("Rice", 50.0);
        productRates.put("Sugar", 45.0);
        productRates.put("Oil", 120.0);
        productRates.put("Flour", 35.0);
    }

    private HBox createHeader() {

        Label product =
                new Label("Product");

        Label rate =
                new Label("Rate");

        Label qty =
                new Label("Qty");

        Label amount =
                new Label("Amount");

        product.setPrefWidth(300);
        rate.setPrefWidth(120);
        qty.setPrefWidth(120);
        amount.setPrefWidth(150);

        HBox header =
                new HBox(
                        10,
                        product,
                        rate,
                        qty,
                        amount
                );

        header.setPadding(new Insets(10));

        header.setStyle("""
                -fx-background-color:#1565c0;
                -fx-font-weight:bold;
                """);

        return header;
    }

    private Button createAddButton() {

        Button btn =
                new Button("+ Add Product");
        btn.setStyle(buttonStyle());
        btn.setOnMouseEntered(e ->
                btn.setStyle(getAddButtonHoverStyle()));
        btn.setOnMouseExited(e ->
                btn.setStyle(buttonStyle()));

        btn.setOnAction(e -> addRow());

        return btn;
    }

    private void addRow() {

        rows.getChildren().add(
                ProductRow.create(
                        productRates,
                        rows, this::updateGrandTotal
                )
        );
    }

    public VBox getView() {
        return root;
    }
    private void updateGrandTotal() {

        double total = 0;

        for (var node : rows.getChildren()) {

            if (node instanceof HBox row) {

                TextField amountField =
                        (TextField) row.getChildren().get(3);

                try {

                    total += Double.parseDouble(
                            amountField.getText()
                    );

                } catch (Exception ignored) {
                }
            }
        }

        grandTotalLabel.setText(
                String.format(
                        "Grand Total : ₹%.2f",
                        total
                )
        );

    }
}