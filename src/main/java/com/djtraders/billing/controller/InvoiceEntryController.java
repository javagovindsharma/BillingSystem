package com.djtraders.billing.controller;

import com.djtraders.billing.CssStyle.InvoicePDF;
import com.djtraders.billing.UI.ItemUI;
import com.djtraders.billing.model.Invoice;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

import java.util.ArrayList;

import static com.djtraders.billing.CssStyle.AppStyle.showMessage;

public class InvoiceEntryController {


    /**
     *  grid
     */
    @FXML
    private TextField productNameField;

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

    private final ObservableList<ItemUI> products =
            FXCollections.observableArrayList();
    @FXML
    private TableColumn<ItemUI, Void> actionCol;
    @FXML
    private Label grandTotalLabel;


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

        initializeColumns();

        productTable.setItems(products);

        addDeleteButtonColumn();
    }

    @FXML
    private void addProduct() {

        try {

            ItemUI product = new ItemUI();

            product.setSn(products.size() + 1);

            product.setName(productNameField.getText().trim());

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
            products.add(product);

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

        productNameField.clear();
        hsnField.clear();
        mrpField.clear();
        qtyField.clear();
        rateField.clear();
        discountField.clear();

        productNameField.requestFocus();
        productTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY);
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

                    products.remove(product);

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

        double total = products.stream()
                .mapToDouble(ItemUI::getAmount)
                .sum();

        grandTotalLabel.setText(
                String.format("₹ %.2f", total));
    }

    private void refreshSerialNumbers() {

        for (int i = 0; i < products.size(); i++) {
            products.get(i).setSn(i + 1);
        }
    }

    @FXML
    private void saveInvoice() {

        Invoice invoice = prepareInvoice();

        System.out.println("Invoice No : "
                + invoice.getInvoiceNo());

        System.out.println("Date : "
                + invoice.getInvoiceDate());

        System.out.println("Seller : "
                + invoice.getSellerDetails());

        System.out.println("Buyer : "
                + invoice.getBuyerDetails());

        System.out.println("Total Products : "
                + invoice.getProducts().size());

        System.out.println("Grand Total : "
                + invoice.getGrandTotal());
    }

    @FXML
    private void generatePdf() {
        Invoice invoice = prepareInvoice();
        InvoicePDF inoicePrint=new InvoicePDF();
        inoicePrint.generate(invoice);
        showMessage(
                "Success",
                "Invoice Saved Successfully.",
                Alert.AlertType.INFORMATION);
        System.out.println("Generate PDF");
    }
    private Invoice prepareInvoice() {

        Invoice invoice = new Invoice();

        invoice.setInvoiceNo(
                invoiceNoField.getText());

        invoice.setInvoiceDate(
                invoiceDatePicker.getValue());

        invoice.setSellerDetails(
                sellerDetailsField.getText());

        invoice.setBuyerDetails(
                buyerDetailsField.getText());

        invoice.setProducts(
                new ArrayList<>(products));

        double total = products.stream()
                .mapToDouble(ItemUI::getAmount)
                .sum();

        invoice.setGrandTotal(total);

        return invoice;
    }

}