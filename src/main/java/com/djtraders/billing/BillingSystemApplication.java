package com.djtraders.billing;

import com.djtraders.billing.UI.DashboardUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BillingSystemApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(BillingSystemApplication.class, args);

        DashboardUI.setContext(context);
        javafx.application.Application.launch(DashboardUI.class, args);
    }

}
