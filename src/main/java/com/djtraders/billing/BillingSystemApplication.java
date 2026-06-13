package com.djtraders.billing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BillingSystemApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(BillingSystemApplication.class, args);

        StoreFxApp.setContext(context);
        javafx.application.Application.launch(StoreFxApp.class, args);
    }

}
