package lk.ijse.dep.Controller;

import lk.ijse.dep.UtilityClasses.DBConnection;
import lk.ijse.dep.UtilityClasses.SearchTableHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchOrderController {
    public TextField txt_search;
    public TableView <SearchTableHelper>tbl_allOrder;
    Connection con = DBConnection.getInstance().getConnection();
    public Scene mainscreen;
    public  Parent root = null;
    public static SearchTableHelper obj=null;


    public void initialize(){
        tbl_allOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderid"));
        tbl_allOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("orderdate"));
        tbl_allOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("cusid"));
        tbl_allOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("cusname"));
        tbl_allOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));

        ObservableList listi = tbl_allOrder.getItems();
        txt_search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tbl_allOrder.getItems().clear();
                String sql = "SELECT * FROM orderdetails WHERE cusid LIKE ? ";
                try {
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1,"%"+newValue);
                    //pst.setString(2,"%"+newValue);
                    ResultSet rst = pst.executeQuery();

                    while (rst.next()){

                        String sql1 = "SELECT name FROM customer WHERE id=?";
                        PreparedStatement pst1 = con.prepareStatement(sql1);
                        pst1.setString(1,rst.getString("cusid"));
                        ResultSet rst1 = pst1.executeQuery();


                        if (rst1.next()){

                            String cusname =rst1.getString("name");
                            String orderid = rst.getString("orderid");
                            int tot=0;

                            String sql2 = "SELECT itemcode,qty FROM orderitems WHERE orderid=?";
                            PreparedStatement pst2 = con.prepareStatement(sql2);
                            pst2.setString(1,orderid);
                            ResultSet rst2 = pst2.executeQuery();
                            while (rst2.next()){
                                String qty = rst2.getString("qty");
                                String itemCode = rst2.getString("itemcode");
                                String price = "0";

                                String sql3 = "SELECT price FROM item WHERE code=?";
                                PreparedStatement pst3 = con.prepareStatement(sql3);
                                pst3.setString(1,itemCode);
                                ResultSet rst3 = pst3.executeQuery();
                                if (rst3.next()){
                                    price = rst3.getString("price");
                                }
                                tot = tot + Integer.parseInt(price)*Integer.parseInt(qty);
                            }

                            listi.add(new SearchTableHelper(orderid,rst.getString("orderdate"),rst.getString("cusid"),cusname,Integer.toString(tot)));

                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        tbl_allOrder.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                try {
                    SearchTableHelper selectedItem = tbl_allOrder.getSelectionModel().getSelectedItem();

                    obj = selectedItem;

                    root = FXMLLoader.load(this.getClass().getResource("/VIew/ViewOrder.fxml"));
                    root.setUserData("lol");
                    mainscreen = new Scene(root);

                    Stage stage = (Stage) txt_search.getScene().getWindow();
                    stage.setScene(mainscreen);
                    stage.setTitle("Item Manage");



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void searchInstance(ActionEvent actionEvent) {


    }
}
