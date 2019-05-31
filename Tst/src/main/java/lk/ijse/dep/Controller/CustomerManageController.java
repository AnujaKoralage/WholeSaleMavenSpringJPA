package lk.ijse.dep.Controller;

import lk.ijse.dep.AppInitializer;
import lk.ijse.dep.Business.custom.CustomerBO;
import lk.ijse.dep.Business.impl.CustomerBOImpl;
import lk.ijse.dep.DTO.CustomerDTO;
import lk.ijse.dep.UtilityClasses.Customer;
import lk.ijse.dep.UtilityClasses.DBConnection;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CustomerManageController {
    public TextField txt_cusID;
    public TextField txt_cusName;
    public TextField cus_address;
    public Button btn_save;
    public TableView<Customer> tbl_cus;
    public Button btn_newCus;
    public static ObservableList items;
    public Button btn_update;


    CustomerBO cusBo = AppInitializer.ctx.getBean(CustomerBO.class);
    public void initialize() throws Exception {

        tbl_cus.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tbl_cus.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tbl_cus.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Customer, Customer> unfriendCol = new TableColumn<>("Delete");
        unfriendCol.setMinWidth(40);
        unfriendCol.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        unfriendCol.setCellFactory(param -> new TableCell<Customer, Customer>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Customer customer, boolean empty) {

                super.updateItem(customer, empty);

                if (customer == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(
                        event -> {
                            try {

                                if (cusBo.customerExistsinOrder(customer.getId())){
                                    Alert alt = new Alert(Alert.AlertType.ERROR,"Can't delete customer");
                                    alt.show();
                                }
                                else {
                                    cusBo.deleteCustomer(customer.getId());
                                    tbl_cus.getItems().remove(customer);
                                }

                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                );
            }
        });
        tbl_cus.getColumns().add(unfriendCol);


        ObservableList items = tbl_cus.getItems();

//        CustomerBOImpl businesscus = new CustomerBOImpl();
        for (CustomerDTO customer:cusBo.allCustomers()) {
            items.add(new Customer(customer.getId(),customer.getName(),customer.getAddress()));
        }

        btn_update.setVisible(false);
        btn_save.setVisible(true);

        tbl_cus.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Customer selectedItem = (Customer) tbl_cus.getSelectionModel().getSelectedItem();
                if (selectedItem!=null){
                    txt_cusID.setText(selectedItem.getId());
                    txt_cusName.setText(selectedItem.getName());
                    cus_address.setText(selectedItem.getAddress());
                    btn_save.setVisible(false);
                    btn_update.setVisible(true);

                }
            }
        });
    }

    public void btnSave(ActionEvent actionEvent) throws Exception {
        if (txt_cusID.getText().length() == 0 || txt_cusName.getText().length() == 0 || cus_address.getText().length() == 0){
            Alert akt = new Alert(Alert.AlertType.ERROR,"Enter all feilds");
            akt.show();
        }else {
            CustomerBOImpl cusBO = new CustomerBOImpl();
            cusBO.saveCustomer(new CustomerDTO(txt_cusID.getText(),txt_cusName.getText(),cus_address.getText()));

            tbl_cus.getItems().add(new Customer(txt_cusID.getText(),txt_cusName.getText(),cus_address.getText()));
            tbl_cus.refresh();
        }
    }

    public void newCus(ActionEvent actionEvent) {
        txt_cusID.setText(null);
        txt_cusName.setText(null);
        cus_address.setText(null);

        btn_update.setVisible(false);
        btn_save.setVisible(true);

    }

    public void toBack(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(this.getClass().getResource("/VIew/Dashboard.fxml"));
        Scene mainScreen = new Scene(root);

        Stage stage = (Stage) btn_newCus.getScene().getWindow();
        stage.setScene(mainScreen);
    }

    public void update(ActionEvent actionEvent) throws Exception {
        items = tbl_cus.getItems();
        items.clear();

        String cusid = txt_cusID.getText();
        String cusname = txt_cusName.getText();
        String cusaddress = cus_address.getText();

        CustomerBOImpl customerBOImpl = new CustomerBOImpl();
        try {
            customerBOImpl.updateCustomer(new CustomerDTO(cusid, cusname, cusaddress));
            Alert alt = new Alert(Alert.AlertType.CONFIRMATION,"Customer updated");
            alt.show();

            for (CustomerDTO customer: customerBOImpl.allCustomers()) {
                items.add(new Customer(customer.getId(),customer.getName(),customer.getAddress()));
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Customer not updated");
            alert.show();
        }

    }

    public void generateCustomerReport(ActionEvent actionEvent) throws JRException {

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/Reports/CustomerSearch.jasper"));

        Map<String, Object> pram = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, pram, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }
}
