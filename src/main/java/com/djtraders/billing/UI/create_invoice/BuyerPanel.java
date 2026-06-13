package com.djtraders.billing.UI.create_invoice;

import com.djtraders.billing.CssStyle.AppStyle;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class BuyerPanel {

    public static VBox create() {
        VBox pane = new VBox(8);
        TextArea buyer = new TextArea(
                "");
        buyer.setPrefHeight(120);

        pane.getChildren().addAll(
                new Label("Buyer Details"),
                buyer
        );
        pane.setStyle(AppStyle.getCardStyle());
        return pane;
    }
}