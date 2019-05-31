package lk.ijse.dep.Controller;

import lk.ijse.dep.UtilityClasses.DBConnection;
import lk.ijse.dep.UtilityClasses.Order;
import lk.ijse.dep.UtilityClasses.SearchTableHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VIewOrderController {
    public Button btn_back;
    public TextField txt_oid;
    public TextField txt_odate;
    public TextField txt_cusname;
    public TextField txt_cusid;
    public TextField txt_total;
    public TableView <Order>tbl_order;

    Connection con = DBConnection.getInstance().getConnection();

    public void initialize() throws IOException {
        txt_cusname.setDisable(true);
        txt_odate.setDisable(true);
        txt_oid.setDisable(true);
        txt_cusid.setDisable(true);
        txt_total.setDisable(true);

        tbl_order.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tbl_order.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tbl_order.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tbl_order.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("price"));
        tbl_order.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));


        SearchTableHelper sth = SearchOrderController.obj;
        txt_oid.setText(sth.getOrderid());
        txt_odate.setText(sth.getOrderdate());
        txt_cusname.setText(sth.getCusname());
        txt_cusid.setText(sth.getCusid());

        String subtotal="0";


        try {
            String sql1 = "SELECT * FROM orderitems WHERE orderid=?";
            PreparedStatement pst1 = con.prepareStatement(sql1);
            pst1.setString(1,sth.getOrderid());
            ResultSet rst1 = pst1.executeQuery();

            while (rst1.next()){
                String qty = rst1.getString("qty");
                String itemcode = rst1.getString("itemCode");
                String sql2 = "SELECT * FROM item WHERE code=?";
                PreparedStatement pst2 = con.prepareStatement(sql2);
                pst2.setString(1,itemcode);
                ResultSet rst2 = pst2.executeQuery();

                if (rst2.next()){
                    String description = rst2.getString("description");
                    String unitprice = rst2.getString("price");

                    String total = String.valueOf(Integer.parseInt(unitprice)*Integer.parseInt(qty));

                    ObservableList<Order> items = tbl_order.getItems();
                    subtotal = String.valueOf(Integer.parseInt(subtotal) + Integer.parseInt(total));
                    items.add(new Order(itemcode,description,qty,unitprice,total));
                    txt_total.setText(subtotal);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void backToDashboard(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(this.getClass().getResource("/VIew/SearchOrder.fxml"));
        Scene mainScreen = new Scene(root);

        Stage stage = (Stage) btn_back.getScene().getWindow();
        stage.setScene(mainScreen);
        stage.setTitle("Search Order");

    }

}
