package com.djtraders.billing.UI;

import com.djtraders.billing.service.ProductService;
import com.djtraders.billing.service.SalesService;
import javafx.scene.layout.BorderPane;

public class MainLayout {

    private BorderPane root = new BorderPane();

    private final ProductService productService;
    private final SalesService salesService;

    public MainLayout(ProductService p, SalesService s){
        this.productService = p;
        this.salesService = s;

        showDashboard();
    }

    public BorderPane getView(){
        return root;
    }

    void showDashboard(){
        DashboardUI dashboard =
                new DashboardUI(productService, salesService, this);
        root.setCenter(dashboard.getView());
    }

    public void showProductPage(){
        ProductUI productUI =
                new ProductUI(productService, this);
        root.setCenter(productUI.getView());
    }

    public void showSalesPage(){
        SalesUI salesUI =
                new SalesUI(productService,salesService, this);
        root.setCenter(salesUI.getView());
    }
}
