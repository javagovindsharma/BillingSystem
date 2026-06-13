package com.djtraders.billing.UI.create_invoice;

import com.djtraders.billing.CssStyle.AppStyle;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class SellerPanel {

    public static VBox create() {
        VBox pane = new VBox(8);
        TextArea seller = new TextArea(
                "R.J. TRADERS\n"
                        + " DILDAR NAGAR BAZAR\n"
                        + " NEAR KRISHI MANDI GHAZIPUR\n"
                        + " Mob.  9161490408\n"
                        + "GST: 09AYMPJ1555D2ZE");
        seller.setPrefHeight(120);
        seller.setDisable(true);

        pane.getChildren().addAll(
                new Label("Seller Details"),
                seller
        );
        pane.setStyle(AppStyle.getCardStyle());
        return pane;
    }
}