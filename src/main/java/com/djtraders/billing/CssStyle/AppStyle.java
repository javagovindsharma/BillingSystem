package com.djtraders.billing.CssStyle;

import javafx.scene.control.Alert;

public class AppStyle {
    public static String buttonStyle() {
        return """
                -fx-background-color: #1b1852;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-padding: 10 20 10 20;
                -fx-background-radius: 8;
                """;
    }
    public static String getRootStyle() {
        return """
            -fx-background-color: linear-gradient(
                to bottom,
                #0F172A,
                #1E3A5F,
                #1E40AF
            );
            """;
    }

    public static String getCardStyle() {
        return """
                -fx-background-color: white;
                -fx-border-color: #d9d9d9;
                -fx-border-width: 1;
                -fx-background-radius: 10;
                -fx-border-radius: 10;
                -fx-min-width: 220;
                -fx-min-height: 120;
            """;
    }
    public static String getCardStyleOnMouseEntered() {
        return """
            -fx-background-color: #dbeafe;
            -fx-border-color: #2563eb;
            -fx-border-width: 2;
            -fx-background-radius: 15;
            -fx-border-radius: 15;
            -fx-min-width: 220;
            -fx-min-height: 120;
            """;
     }
    public static String getCardStyleOnMouseExits() {
        return """
                -fx-background-color: white;
                -fx-border-color: #d9d9d9;
                -fx-border-width: 1;
                -fx-background-radius: 15;
                -fx-border-radius: 15;
                -fx-min-width: 220;
                -fx-min-height: 120;
                """;
    }

    public static String getTitleStyle() {
        return """
            -fx-font-size:30px;
            -fx-font-weight:bold;
            -fx-text-fill:white;
            """;
    }

    public static String getHeaderStyle() {
        return """
            -fx-background-color:#2563EB;
            -fx-background-radius:8;
            -fx-padding:10;
            """;
    }

    public static String getHeaderLabelStyle() {
        return """
            -fx-text-fill:white;
            -fx-font-weight:bold;
            -fx-font-size:14px;
            """;
    }

    public static String getAddButtonStyle() {
        return """
            -fx-background-color:#3B82F6;
            -fx-background-radius:8;
            -fx-text-fill:white;
            -fx-font-weight:bold;
            -fx-cursor:hand;
            """;
    }

    public static String getAddButtonHoverStyle() {
        return """
            -fx-background-color: #2563eb;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-padding: 10 20 10 20;
                -fx-background-radius: 8;
            """;
    }



    public static String getGrandTotalStyle() {
        return """
            -fx-font-size:24px;
            -fx-font-weight:bold;
            -fx-text-fill:#FBBF24;
            """;
    }

    public static String getScrollPaneStyle() {
        return """
            -fx-background-color:transparent;
            -fx-background-insets:0;
            -fx-padding:0;
            -fx-border-width:0;
            """;
    }

    public static String getProductRowStyle() {
        return """
            -fx-background-color:white;
            -fx-background-radius:6;
            -fx-border-radius:6;
            -fx-border-color:#E5E7EB;
            -fx-padding:5;
            """;
    }

    public  static void showMessage(String title,
                             String message,
                             Alert.AlertType type) {

        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}