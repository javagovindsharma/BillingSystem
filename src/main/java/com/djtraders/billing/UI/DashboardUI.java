package com.djtraders.billing.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;

public class DashboardUI extends Application {
    private static ApplicationContext context;
    public static void setContext(ApplicationContext ctx){
        context = ctx;
    }
    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = new Scene(
            FXMLLoader.load(
                getClass().getResource("/fxml/dashboard.fxml")
            )
        );

        scene.getStylesheets().add(
            getClass().getResource("/css/dashboard.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.setTitle("Billing Dashboard");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}