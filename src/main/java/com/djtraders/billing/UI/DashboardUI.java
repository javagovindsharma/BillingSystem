package com.djtraders.billing.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;

public class DashboardUI extends Application {
    private static ApplicationContext context;
    public static void setContext(ApplicationContext ctx){
        context = ctx;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("CONTEXT CHECK = " + context);

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/dashboard.fxml")
        );

        // ⭐ IMPORTANT LINE (Spring injection ON)
        loader.setControllerFactory(context::getBean);

        Parent root = loader.load();

        Scene scene = new Scene(root);

        scene.getStylesheets().add(
                getClass().getResource("/css/dashboard.css").toExternalForm()
        );

        scene.getStylesheets().add(
            getClass().getResource("/css/dashboard.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.setTitle("Billing Dashboard");
        stage.show();
    }
}