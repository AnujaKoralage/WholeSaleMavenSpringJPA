package lk.ijse.dep.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {
    public Button btn_managecus;

    public void toCustomerManage(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/VIew/CustomerManage.fxml"));
        Scene mainScreen = new Scene(root);

        Stage stage = (Stage) btn_managecus.getScene().getWindow();
        stage.setScene(mainScreen);
        stage.setTitle("Customer Manage");
    }

    public void toItemManage(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/VIew/ItemManage.fxml"));
        Scene mainScreen = new Scene(root);

        Stage stage = (Stage) btn_managecus.getScene().getWindow();
        stage.setScene(mainScreen);
        stage.setTitle("Item Manage");
    }

    public void toPlaseOrder(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/VIew/OrderItem.fxml"));
        Scene mainScreen = new Scene(root);

        Stage stage = (Stage) btn_managecus.getScene().getWindow();
        stage.setScene(mainScreen);
        stage.setTitle("Item Manage");
    }

    public void showOrder(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(this.getClass().getResource("/VIew/SearchOrder.fxml"));
        Scene mainScreen = new Scene(root);

        Stage stage = (Stage) btn_managecus.getScene().getWindow();
        stage.setScene(mainScreen);
        stage.setTitle("Search Order");

    }
}
