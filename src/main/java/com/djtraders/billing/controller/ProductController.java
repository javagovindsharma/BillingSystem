package com.djtraders.billing.controller;

import com.djtraders.billing.UI.ProductUI;
import com.djtraders.billing.model.ProductEntity;
import com.djtraders.billing.repository.ProductRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import static com.djtraders.billing.CssStyle.AppStyle.showMessage;
@Controller
public class ProductController {
    private static ApplicationContext context;
    public static void setContext(ApplicationContext ctx){
        context = ctx;
    }
    @Autowired
    private ProductRepository productRepository;
    @FXML
    private TextField productNameField;

    @FXML
    private TextField hsnField;

    @FXML
    private TextField rateField;

    @FXML
    private TextField mrpField;

    @FXML
    private TextField discountField;

    private ProductUI productUI;
    public ProductController() {
        System.out.println("Controller Created");
        System.out.println(context+"ProductController  =====productRepository"+productRepository);
    }
    public void setProduct(ProductUI productUI) {

        this.productUI = productUI;
        productNameField.setText(productUI.getProductName());
        hsnField.setText(productUI.getHsn());
        rateField.setText(productUI.getRate()+"");
        mrpField.setText(productUI.getMrp()+"");
        discountField.setText(productUI.getDiscount()+"");
    }
    @FXML
    private void saveProduct() {
        System.out.println("repor ==>>"+productRepository);
        ProductEntity product = new ProductEntity();

        product.setName(productNameField.getText());
        product.setHsn(hsnField.getText());

        product.setRate(
                Integer.parseInt(rateField.getText()));

        product.setMrp(
                Double.parseDouble(mrpField.getText()));

        product.setDiscount(
                Integer.parseInt(discountField.getText()));
        productRepository.save(product);
        showMessage(
                "Success",
                "Product Saved Successfully",
                Alert.AlertType.INFORMATION);
        Stage stage =
                (Stage) discountField.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void clearForm() {

        Stage stage =
                (Stage) discountField.getScene().getWindow();

        stage.close();
    }
}
