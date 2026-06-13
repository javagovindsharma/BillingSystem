package com.djtraders.billing.UI.create_invoice;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ActionButtons {

    public static HBox create(
            Button saveBtn,
            Button printBtn) {

        HBox box =
                new HBox(15,
                        saveBtn,
                        printBtn);

        box.setAlignment(Pos.CENTER_RIGHT);

        return box;
    }
}