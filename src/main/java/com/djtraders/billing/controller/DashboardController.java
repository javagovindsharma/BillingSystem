package com.djtraders.billing.controller;

import com.djtraders.billing.UI.InvoiceUI;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DashboardController {

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
    ObservableList<InvoiceUI> masterData =
            FXCollections.observableArrayList();
    FilteredList<InvoiceUI> filteredData;



    @FXML
    public void initialize() {

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

}