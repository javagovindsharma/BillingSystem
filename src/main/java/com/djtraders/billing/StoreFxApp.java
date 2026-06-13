package com.djtraders.billing;

import com.djtraders.billing.UI.DashboardUI;
import com.djtraders.billing.UI.MainLayout;
import com.djtraders.billing.service.ProductService;
import com.djtraders.billing.service.SalesService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;

public class StoreFxApp extends Application {

    private static ApplicationContext context;

    public static void setContext(ApplicationContext ctx){
        context = ctx;
    }

    @Override
    public void start(Stage stage) {

        ProductService productService =
                context.getBean(ProductService.class);

        SalesService salesService =
                context.getBean(SalesService.class);

        MainLayout mainLayout =
                new MainLayout(productService, salesService);

        Scene scene =
                new Scene(mainLayout.getView(), 900, 600);

        stage.setScene(scene);
        stage.setTitle("Store Management System");
        stage.show();
    }
}
