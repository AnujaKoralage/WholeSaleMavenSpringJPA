package lk.ijse.dep.Controller;

import lk.ijse.dep.AppInitializer;
import lk.ijse.dep.Business.impl.ItemBOImpl;
import lk.ijse.dep.DTO.ItemDTO;
import lk.ijse.dep.UtilityClasses.DBConnection;
import lk.ijse.dep.UtilityClasses.Item;
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
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
public class ItemManageController {
    public TextField txt_itmDes;
    public TextField txt_itmQty;
    public TableView<Item> tbl_cus;
    public TextField txt_itmPrice;
    public Button btn_save;
    public Button btn_newCus;
    public Button btn_back;
    public TextField txt_itemCode;
    public static ObservableList item;
    public Button btn_update;

    public void initialize() throws Exception {

        btn_save.setVisible(true);
        btn_update.setVisible(false);

        tbl_cus.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tbl_cus.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tbl_cus.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tbl_cus.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Item, Item> delete = new TableColumn<>("Delete");
        delete.setMinWidth(40);
        delete.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        delete.setCellFactory(param -> new TableCell<Item, Item>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Item item, boolean empty) {

                super.updateItem(item, empty);

                if (item == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(
                                event -> {
                                    ItemBOImpl itemBOImpl = new ItemBOImpl();
                                    try {
                                        if (itemBOImpl.itemExistsinOrder(item.getCode())){
                                            Alert alt = new Alert(Alert.AlertType.ERROR,"Item can't delete");
                                            alt.show();
                                        }
                                        else {
                                            try {
                                                itemBOImpl.deleteItem(item.getCode());

                                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Item deleted");
                                                alert.show();
                                                tbl_cus.getItems().remove(item);
                                            }catch (Exception e){
                                                Alert alert = new Alert(Alert.AlertType.ERROR,"Item was not deleted");
                                                alert.show();
                                            }
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
        tbl_cus.getColumns().add(delete);

        ObservableList item = (ObservableList) tbl_cus.getItems();
        ItemBOImpl itemBOImpl = AppInitializer.ctx.getBean(ItemBOImpl.class);
        for (ItemDTO itemDTO: itemBOImpl.getAllItems()) {
            item.add(new Item(itemDTO.getCode(),itemDTO.getDescription(),itemDTO.getQty(),itemDTO.getPrice()));
        }

        tbl_cus.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Item selectedItem = tbl_cus.getSelectionModel().getSelectedItem();
                if (selectedItem!=null){
                    txt_itemCode.setText(selectedItem.getCode());
                    txt_itmDes.setText(selectedItem.getDescription());
                    txt_itmQty.setText(selectedItem.getQty());
                    txt_itmPrice.setText(selectedItem.getPrice());

                    btn_update.setVisible(true);
                    btn_save.setVisible(false);
                }
            }
        });
    }

    public void btnSave(ActionEvent actionEvent) throws Exception {
        if (txt_itemCode.getText().length() == 0 || txt_itmDes.getText().length() == 0 || txt_itmQty.getText().length() == 0 || txt_itmPrice.getText().length() == 0){
            return;
        }
        ObservableList item = (ObservableList) tbl_cus.getItems();
        ItemBOImpl itemBOImpl = new ItemBOImpl();
        try {
            itemBOImpl.saveItem(new ItemDTO(txt_itemCode.getText(), txt_itmDes.getText(), txt_itmQty.getText(), txt_itmPrice.getText()));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Item saved");
            alert.show();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Item not saved");
            alert.show();
        }

        item.add(new Item(txt_itemCode.getText(),txt_itmDes.getText(),txt_itmQty.getText(),txt_itmPrice.getText()));
    }

    public void newCus(ActionEvent actionEvent) {
        txt_itemCode.setText(null);
        txt_itmDes.setText(null);
        txt_itmQty.setText(null);
        txt_itmPrice.setText(null);

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
        ObservableList item = tbl_cus.getItems();
        item.clear();
        ItemBOImpl itemBOImpl = new ItemBOImpl();
        try {
            itemBOImpl.updateItem(new ItemDTO(txt_itemCode.getText(), txt_itmDes.getText(), txt_itmQty.getText(), txt_itmPrice.getText()));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Item saved");
            alert.show();

            for (ItemDTO itemDTO: itemBOImpl.getAllItems()) {
                item.add(new Item(itemDTO.getCode(),itemDTO.getDescription(),itemDTO.getQty(),itemDTO.getPrice()));
            }
        }catch (Exception e){

        }

    }

    public void generateItemReport(ActionEvent actionEvent) throws JRException {

        InputStream resourceAsStream = this.getClass().getResourceAsStream("/Reports/ItemReport.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        Map<String, Object> pram = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, pram, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }
}
