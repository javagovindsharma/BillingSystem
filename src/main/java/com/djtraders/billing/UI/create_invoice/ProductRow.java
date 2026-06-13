package com.djtraders.billing.UI.create_invoice;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public class ProductRow {

    public static HBox create(
            Map<String, Double> productRates,
            VBox rows, Runnable totalUpdater
    ) {
        Button removeBtn = new Button("X");

        removeBtn.setStyle("""
        -fx-background-color:#d32f2f;
        -fx-text-fill:white;
        -fx-font-weight:bold;
        """);
        ComboBox<String> product = new ComboBox<>();
        product.getItems().addAll(productRates.keySet());
        product.setPrefWidth(300);

        TextField rate = new TextField();
        rate.setEditable(false);
        rate.setPrefWidth(120);

        TextField qty = new TextField();
        qty.setPrefWidth(120);

        TextField amount = new TextField();
        amount.setEditable(false);
        amount.setPrefWidth(150);

        product.setOnAction(e -> {

            String selected = product.getValue();

            if (selected != null) {

                rate.setText(
                        String.valueOf(
                                productRates.get(selected)
                        )
                );

                calculateAmount(rate, qty, amount);
                totalUpdater.run();
            }
        });

        qty.textProperty().addListener(
                (obs, oldVal, newVal) -> {
                    calculateAmount(rate, qty, amount);
                    totalUpdater.run();
                }
        );

        HBox row = new HBox(
                10,
                product,
                rate,
                qty,
                amount,removeBtn
        );
        removeBtn.setOnAction(e -> {

            if (rows.getChildren().size() > 1) {

                rows.getChildren().remove(row);
                totalUpdater.run();

            } else {

                Alert alert = new Alert(
                        Alert.AlertType.WARNING
                );

                alert.setContentText(
                        "At least one product row is required."
                );

                alert.showAndWait();
            }
        });
        row.setPadding(new Insets(5));

        return row;
    }

    private static void calculateAmount(
            TextField rate,
            TextField qty,
            TextField amount
    ) {

        try {

            double r =
                    Double.parseDouble(rate.getText());

            double q =
                    Double.parseDouble(qty.getText());

            amount.setText(
                    String.format("%.2f", r * q)
            );

        } catch (Exception ex) {

            amount.setText("0.00");
        }
    }
}