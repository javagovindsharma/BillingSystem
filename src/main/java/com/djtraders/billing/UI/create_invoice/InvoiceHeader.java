package com.djtraders.billing.UI.create_invoice;

import com.djtraders.billing.CssStyle.AppStyle;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class InvoiceHeader {

    public static HBox create() {

        Label title =
                new Label("RJ TRADERS - TAX INVOICE");

        title.setStyle(AppStyle.getHeaderStyle());

        HBox box = new HBox(title);
        box.setAlignment(Pos.CENTER);

        return box;
    }
}