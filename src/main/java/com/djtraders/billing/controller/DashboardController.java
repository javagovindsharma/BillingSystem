package com.djtraders.billing.controller;

import com.djtraders.billing.UI.DashboardUI;
import com.djtraders.billing.UI.InvoiceUI;
import com.djtraders.billing.UI.ProductUI;
import com.djtraders.billing.model.ProductEntity;
import com.djtraders.billing.repository.ProductRepository;
import com.djtraders.billing.service.ProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class DashboardController {
    private static ApplicationContext context;
    public static void setContext(ApplicationContext ctx){
        context = ctx;
    }
    @Autowired
    private ProductService productService;

    @FXML
    private TableView<InvoiceUI> invoiceTable;

    @FXML
    private TableColumn<InvoiceUI, String> colInvoiceId;

    @FXML
    private TableColumn<InvoiceUI, String> colCompany;

    @FXML
    private TableColumn<InvoiceUI, String> colEmail;

    @FXML
    private TableColumn<InvoiceUI, String> colStatus;

    @FXML
    private TableColumn<InvoiceUI, Double> colAmount;
    @FXML
    private TableColumn<InvoiceUI, Void> colAction;

    @FXML
    private TextField txtSearch;
    ObservableList<InvoiceUI> masterData = FXCollections.observableArrayList();
    FilteredList<InvoiceUI> filteredData;

    /**
     * Product table
     */
    @FXML
    private TextField txtProdSearch;
    ObservableList<ProductUI> masterProductData = FXCollections.observableArrayList();
    FilteredList<ProductUI> filteredproductData;


    /**
    *   product
     **/
    @FXML
    private TableView<ProductUI> productTable;
    @FXML
    private TableColumn<ProductUI, String> colProdId;

    @FXML
    private TableColumn<ProductUI, String> colpropdName;

    @FXML
    private TableColumn<ProductUI, String> colProdHSN;

    @FXML
    private TableColumn<ProductUI, Integer> colProdRate;

    @FXML
    private TableColumn<ProductUI, Double> colProdMrp;
    @FXML
    private TableColumn<ProductUI, Double> colProdDiscount;
    @FXML
    private TableColumn<ProductUI, Void> colProdAction;


    @FXML
    public void initialize() {
        System.out.println("productRepository"+productService);
        invoiceInitialization();
        productInitialization();

    }

    private void addActionButtons() {

        colAction.setCellFactory(param -> new TableCell<>() {

            private Button editBtn = new Button("Edit");
            private Button deleteBtn = new Button("Delete");

            {
                editBtn.getStyleClass().add("edit-btn");
                deleteBtn.getStyleClass().add("delete-btn");
                editBtn.setOnAction(event -> {

                    InvoiceUI invoice =
                            getTableView().getItems().get(getIndex());

                    editInvoice(invoice);
                });

                deleteBtn.setOnAction(event -> {

                    InvoiceUI invoice =
                            getTableView().getItems().get(getIndex());

                    deleteInvoice(invoice);
                });
            }

            private final HBox pane =
                    new HBox(10, editBtn, deleteBtn);

            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });
    }
    private void editInvoice(InvoiceUI invoice)  {

        System.out.println("Editing : "
                + invoice.getInvoiceId());
        try {
            // Open Edit Dialog
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/editInvoice.fxml"));


            Parent root = loader.load();

            EditInvoiceController controller =
                    loader.getController();

            controller.setInvoice(invoice);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait(); // IMPORTANT

            invoiceTable.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteInvoice(InvoiceUI invoice) {

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION);

        alert.setTitle("Delete Invoice");
        alert.setHeaderText(null);
        alert.setContentText(
                "Delete invoice " +
                        invoice.getInvoiceId() + "?");

        Optional<ButtonType> result =
                alert.showAndWait();

        if(result.isPresent()
                && result.get() == ButtonType.OK) {

            invoiceTable.getItems().remove(invoice);

            // Database delete
        }
    }
    @FXML
    private void handleFilter() {
        String keyword = txtSearch.getText().toLowerCase();
        System.out.println("keyboard"+keyword);
        filteredData.setPredicate(invoice -> {

            if(keyword.isBlank()) {
                return true;
            }

            return invoice.getCompanyName().toLowerCase().contains(keyword)
                    || invoice.getInvoiceId().toLowerCase().contains(keyword)
                    || invoice.getEmail().toLowerCase().contains(keyword);
        });

        invoiceTable.refresh();
        SortedList<InvoiceUI> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(invoiceTable.comparatorProperty());

        invoiceTable.setItems(sortedData);
    }
    @FXML
    private void handleNewInvoice() {
        System.out.println("New Invoice : ");
        try {
            // Open Edit Dialog
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/invoiceEntry.fxml"));


            Parent root = loader.load();

            InvoiceEntryController controller =
                    loader.getController();


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNewProduct() {
        System.out.println("New Product : ");
        try {
            // Open Edit Dialog
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/product.fxml"));

            // ⭐ THIS IS THE MISSING LINE
            loader.setControllerFactory(DashboardUI.getContext()::getBean);
            Parent root = loader.load();
            ProductController controller =
                    loader.getController();



            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            productTable.refresh();
            List<ProductUI>  proL= productService.getAll();
            List<ProductUI> listProduct=new LinkedList<>();
            listProduct.addAll(proL);

            ObservableList<ProductUI> data =FXCollections.observableArrayList(listProduct);

            addActionProdButtons();
            productTable.setColumnResizePolicy(
                    TableView.CONSTRAINED_RESIZE_POLICY);
            productTable.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void invoiceInitialization(){
        colInvoiceId.setCellValueFactory(
                new PropertyValueFactory<>("invoiceId"));

        colCompany.setCellValueFactory(
                new PropertyValueFactory<>("companyName"));

        colEmail.setCellValueFactory(
                new PropertyValueFactory<>("email"));

        colStatus.setCellValueFactory(
                new PropertyValueFactory<>("status"));

        colAmount.setCellValueFactory(
                new PropertyValueFactory<>("amount"));
        List<InvoiceUI> listInvoice=new LinkedList<>();
        int i=1;
        while(i<50) {
            listInvoice.add(new InvoiceUI("INV00"+i++,
                    "Spotify",
                    "finance@spotify.com",
                    "Pending",
                    14000));

            listInvoice.add(new InvoiceUI("INV00"+i++,
                    "Slack",
                    "support@slack.com",
                    "Success",
                    25000));

            listInvoice.add(new InvoiceUI("INV00"+i++,
                    "Figma",
                    "support@figma.com",
                    "Failed",
                    12000));
        }
        ObservableList<InvoiceUI> data =
                FXCollections.observableArrayList(listInvoice);

        addActionButtons();
        invoiceTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY);
        invoiceTable.setItems(data);
        masterData.addAll(data);
        filteredData = new FilteredList<>(masterData, b -> true);

        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> {

            filteredData.setPredicate(invoice -> {

                if (newVal == null || newVal.isBlank()) {
                    return true;
                }

                String keyword = newVal.toLowerCase();

                return invoice.getCompanyName().toLowerCase().contains(keyword)
                        || invoice.getInvoiceId().toLowerCase().contains(keyword)
                        || invoice.getEmail().toLowerCase().contains(keyword);
            });
        });

        SortedList<InvoiceUI> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(invoiceTable.comparatorProperty());

        invoiceTable.setItems(sortedData);
    }
    private void productInitialization(){
        colProdId.setCellValueFactory(
                new PropertyValueFactory<>("productId"));

        colpropdName.setCellValueFactory(
                new PropertyValueFactory<>("productName"));

        colProdHSN.setCellValueFactory(
                new PropertyValueFactory<>("hsn"));

        colProdRate.setCellValueFactory(
                new PropertyValueFactory<>("rate"));

        colProdMrp.setCellValueFactory(
                new PropertyValueFactory<>("mrp"));
        colProdDiscount.setCellValueFactory(
                new PropertyValueFactory<>("discount"));
        List<ProductUI> listProduct=new LinkedList<>();
        int i=1;
        List<ProductUI>  proL= productService.getAll();

        listProduct.addAll(proL);

        ObservableList<ProductUI> data =FXCollections.observableArrayList(listProduct);

        addActionProdButtons();
        productTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY);
        productTable.setItems(data);
        masterProductData.addAll(data);
        filteredproductData = new FilteredList<>(masterProductData, b -> true);

        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> {

            filteredproductData.setPredicate(product -> {

                if (newVal == null || newVal.isBlank()) {
                    return true;
                }

                String keyword = newVal.toLowerCase();

                return product.getProductName().toLowerCase().contains(keyword)
                        || String.valueOf(product.getProductId()).toLowerCase().contains(keyword)
                        || String.valueOf(product.getRate()).toLowerCase().contains(keyword)
                        || product.getHsn().toLowerCase().contains(keyword);
            });
        });

        SortedList<ProductUI> sortedData = new SortedList<>(filteredproductData);
        sortedData.comparatorProperty().bind(productTable.comparatorProperty());

        productTable.setItems(sortedData);
    }

    private void addActionProdButtons() {

        colProdAction.setCellFactory(param -> new TableCell<>() {

            private Button editBtn = new Button("Edit");
            private Button deleteBtn = new Button("Delete");

            {
                editBtn.getStyleClass().add("edit-btn");
                deleteBtn.getStyleClass().add("delete-btn");
                editBtn.setOnAction(event -> {

                    ProductUI product =
                            getTableView().getItems().get(getIndex());

                    editProduct(product);
                });

                deleteBtn.setOnAction(event -> {

                    ProductUI product =
                            getTableView().getItems().get(getIndex());

                    deleteProduct(product);
                });
            }

            private final HBox pane =
                    new HBox(10, editBtn, deleteBtn);

            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });
    }

    private void deleteProduct(ProductUI productUI) {

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION);

        alert.setTitle("Delete Invoice");
        alert.setHeaderText(null);
        alert.setContentText(
                "Delete invoice " +
                        productUI.getProductId() + "?");

        Optional<ButtonType> result =
                alert.showAndWait();

        if(result.isPresent()
                && result.get() == ButtonType.OK) {

            productTable.getItems().remove(productUI);

            // Database delete
        }
    }

    private void editProduct(ProductUI productUI)  {

        System.out.println("Editing : "
                + productUI.getProductId());
        try {
            // Open Edit Dialog
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/product.fxml"));


            Parent root = loader.load();

            ProductController controller =
                    loader.getController();

            controller.setProduct(productUI);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait(); // IMPORTANT

            productTable.refresh();
            List<ProductUI>  proL= productService.getAll();
            List<ProductUI> listProduct=new LinkedList<>();
            listProduct.addAll(proL);

            ObservableList<ProductUI> data =FXCollections.observableArrayList(listProduct);

            addActionProdButtons();
            productTable.setColumnResizePolicy(
                    TableView.CONSTRAINED_RESIZE_POLICY);
            productTable.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}