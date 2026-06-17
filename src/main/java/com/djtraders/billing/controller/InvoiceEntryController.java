package com.djtraders.billing.controller;

import com.djtraders.billing.CssStyle.InvoicePDF;
import com.djtraders.billing.UI.ItemUI;
import com.djtraders.billing.model.InvoiceEntity;
import com.djtraders.billing.model.ItemEntity;
import com.djtraders.billing.model.ProductEntity;
import com.djtraders.billing.service.ProductService;
import jakarta.annotation.PostConstruct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.djtraders.billing.CssStyle.AppStyle.showMessage;
@Controller
public class InvoiceEntryController {

    private static ApplicationContext context;
    public static void setContext(ApplicationContext ctx){
        context = ctx;
    }
    @Autowired
    private ProductService productService;
    /**
     *  grid
     */
    @FXML
    private ComboBox<ProductEntity> productComboBox;

    @FXML
    private TextField hsnField;

    @FXML
    private TextField mrpField;

    @FXML
    private TextField qtyField;

    @FXML
    private TextField rateField;

    @FXML
    private TextField discountField;

    /**
     * table
     */
    @FXML
    private TextField invoiceNoField;

    @FXML
    private DatePicker invoiceDatePicker;

    @FXML
    private TextArea sellerDetailsField;

    @FXML
    private TextArea buyerDetailsField;

    @FXML
    private TableView<ItemUI> productTable;

    @FXML
    private TableColumn<ItemUI, Integer> snCol;

    @FXML
    private TableColumn<ItemUI, String> nameCol;

    @FXML
    private TableColumn<ItemUI, String> hsnCol;

    @FXML
    private TableColumn<ItemUI, Double> mrpCol;

    @FXML
    private TableColumn<ItemUI, Double> qtyCol;

    @FXML
    private TableColumn<ItemUI, Double> rateCol;

    @FXML
    private TableColumn<ItemUI, Double> discountCol;

    @FXML
    private TableColumn<ItemUI, Double> amountCol;

    private final ObservableList<ItemUI> items =
            FXCollections.observableArrayList();
    @FXML
    private TableColumn<ItemUI, Void> actionCol;
    @FXML
    private Label grandTotalLabel;
    List<ProductEntity> listProducts;

    public void initializeProduct() {

         listProducts =  productService.findAllProduct();

        productComboBox.getItems().addAll(listProducts);
        productComboBox.setConverter(
                new StringConverter<>() {

                    @Override
                    public String toString(ProductEntity product) {
                        return product == null ? "" : product.getName();
                    }

                    @Override
                    public ProductEntity fromString(String string) {
                        return null;
                    }
                });

        productComboBox.setOnAction(event -> {

            ProductEntity product =
                    productComboBox.getValue();

            if(product != null){

                hsnField.setText(product.getHsn());

                mrpField.setText(
                        String.valueOf(product.getMrp()));

                rateField.setText(
                        String.valueOf(product.getRate()));

                discountField.setText(
                        String.valueOf(product.getDiscount()));
            }
        });
    }
    public InvoiceEntryController(){
        System.out.println("product service"+productService);

    }
    @PostConstruct
    public void setup(){
      //  long id=productService.findNoOfRows()+1;
        //this.invoiceNoField.setText("CH" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))+ String.format("%03d", id));
       // this.invoiceDatePicker.setValue(LocalDate.now());
    }

    private void initializeColumns() {

        snCol.setCellValueFactory(
                data -> data.getValue().snProperty().asObject());

        nameCol.setCellValueFactory(
                data -> data.getValue().nameProperty());

        hsnCol.setCellValueFactory(
                data -> data.getValue().hsnProperty());

        mrpCol.setCellValueFactory(
                data -> data.getValue().mrpProperty().asObject());

        qtyCol.setCellValueFactory(
                data -> data.getValue().qtyProperty().asObject());

        rateCol.setCellValueFactory(
                data -> data.getValue().rateProperty().asObject());

        discountCol.setCellValueFactory(
                data -> data.getValue().discountProperty().asObject());

        amountCol.setCellValueFactory(
                data -> data.getValue().amountProperty().asObject());
    }
    @FXML
    public void initialize() {
        initializeProduct();
        initializeColumns();

        productTable.setItems(items);

        addDeleteButtonColumn();
    }

    @FXML
    private void addProduct() {

        try {

            ItemUI product = new ItemUI();

            product.setSn(items.size() + 1);

           product.setName(productComboBox.getValue().getName());

            product.setHsn(hsnField.getText().trim());

            product.setMrp(
                    Double.parseDouble(mrpField.getText()));

            product.setQty(
                    Double.parseDouble(qtyField.getText()));

            product.setRate(
                    Double.parseDouble(rateField.getText()));

            product.setDiscount(
                    Double.parseDouble(discountField.getText()));

            double gross =
                    product.getQty() * product.getRate();

            double discountAmt =
                    gross * product.getDiscount() / 100;

            product.setAmount(gross - discountAmt);

            // TABLE ME ADD
            items.add(product);

            calculateGrandTotal();

            clearProductFields();

        } catch (Exception e) {

            Alert alert = new Alert(
                    Alert.AlertType.ERROR);

            alert.setHeaderText(null);

            alert.setContentText(
                    "Please enter valid product details.");

            alert.showAndWait();
        }
    }
    @FXML
    private void clearProductFields() {

        productComboBox.getItems().addAll(listProducts);
        hsnField.clear();
        mrpField.clear();
        qtyField.clear();
        rateField.clear();
        discountField.clear();

        productComboBox.requestFocus();
        productTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY);
    }
    @FXML
    private void closeInvoice() {
        Stage stage =
                (Stage) discountField.getScene().getWindow();

        stage.close();
    }


   private void addDeleteButtonColumn() {

        actionCol.setCellFactory(param -> new TableCell<>() {

            private final Button deleteBtn =
                    new Button("Delete");

            {
                deleteBtn.getStyleClass()
                        .add("delete-btn");


                deleteBtn.setOnAction(event -> {

                    ItemUI product =
                            getTableView()
                                    .getItems()
                                    .get(getIndex());

                    items.remove(product);

                    refreshSerialNumbers();

                    calculateGrandTotal();
                });
            }

            @Override
            protected void updateItem(Void item,
                                      boolean empty) {

                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteBtn);
                }
            }
        });
    }

    private void calculateAmount(ItemUI product) {

        double gross =
                product.getQty() * product.getRate();

        double discountAmount =
                gross * product.getDiscount() / 100;

        product.setAmount(
                gross - discountAmount);

        calculateGrandTotal();
    }

    private void calculateGrandTotal() {

        double total = items.stream()
                .mapToDouble(ItemUI::getAmount)
                .sum();

        grandTotalLabel.setText(
                String.format("₹ %.2f", total));
    }

    private void refreshSerialNumbers() {

        for (int i = 0; i < items.size(); i++) {
            items.get(i).setSn(i + 1);
        }
    }

    @FXML
    private void saveInvoice() {
        productService.saveInvoice(prepareInvoice());
        System.out.println("Invoice Saved in DB");
    }

    @FXML
    private void generatePdf() {
        InvoiceEntity invoice = prepareInvoice();
        InvoicePDF inoicePrint=new InvoicePDF();
        inoicePrint.generate(invoice);
        showMessage(
                "Success",
                "Invoice Saved Successfully.",
                Alert.AlertType.INFORMATION);
        System.out.println("Generate PDF");
    }
    private InvoiceEntity prepareInvoice() {

        InvoiceEntity invoice = new InvoiceEntity();

        invoice.setInvoiceNo(invoiceNoField.getText());

        invoice.setInvoiceDate(invoiceDatePicker.getValue());

        invoice.setSellerAddress(sellerDetailsField.getText());

        invoice.setBuyerAddress(buyerDetailsField.getText());

        List<ItemEntity> itemMapperEntities =
                items.stream()
                        .map(ItemMapper::toEntity)
                        .toList();
        invoice.setListOfItems(itemMapperEntities);

        double total = items.stream()
                .mapToDouble(ItemUI::getAmount)
                .sum();

        invoice.setGrandTotal(total);

        return invoice;
    }

}
final class ItemMapper {

    public static ItemEntity toEntity(ItemUI ui) {
        return new ItemEntity(ui.getSn(),ui.getName(),ui.getHsn(),ui.getMrp(), ui.getQty(), ui.getRate(), ui.getDiscount(),ui.getRate()*ui.getQty());
    }
}