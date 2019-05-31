package lk.ijse.dep.Controller;

import lk.ijse.dep.AppInitializer;
import lk.ijse.dep.Business.impl.OrderBOImpl;
import lk.ijse.dep.DTO.ItemDTO;
import lk.ijse.dep.DTO.OrderDetailsDTO;
import lk.ijse.dep.UtilityClasses.DBConnection;
import lk.ijse.dep.UtilityClasses.OnHandQunatites;
import lk.ijse.dep.UtilityClasses.Order;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderItemController {
    public TextField txt_oid;
    public TextField txt_odate;
    public TextField txt_cusname;
    public TextField txt_qtyonhand;
    public TextField txt_qty;
    public TextField txt_description;
    public TextField txt_price;
    public TextField txt_total;
    public ComboBox cmb_cid;
    public ComboBox cmb_itemcode;
    public TableView<Order> tbl_order;
    public Button btn_update;
    public Button btn_add;
    private Connection con = DBConnection.getInstance().getConnection();
    private int orderId;

    private ArrayList <OnHandQunatites>quantityOnHandList = new ArrayList<OnHandQunatites>();

    public void initialize() throws Exception {

        btn_add.setVisible(true);
        btn_update.setVisible(false);

        txt_cusname.setEditable(false);
        txt_odate.setEditable(false);
        txt_oid.setEditable(false);

        txt_description.setEditable(false);
        txt_price.setEditable(false);
        txt_qtyonhand.setEditable(false);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        txt_odate.setText(dateFormat.format(date));

        tbl_order.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tbl_order.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tbl_order.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tbl_order.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("price"));
        tbl_order.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<Order, Order> unfriendCol = new TableColumn<>("Delete");
        unfriendCol.setMinWidth(40);
        unfriendCol.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        unfriendCol.setCellFactory(param -> new TableCell<Order, Order>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Order oder, boolean empty) {

                super.updateItem(oder, empty);

                if (oder == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(
                        event -> {

                            ObservableList<Order> items = tbl_order.getItems();
                            if (items.size()!=0){
                                btn_update.setVisible(false);
                                btn_add.setVisible(true);
                            }
                            if (tbl_order.getItems().size() !=1) {

                                tbl_order.getItems().remove(oder);

                                for (int a=0;a<quantityOnHandList.size();a++){
                                    OnHandQunatites qt = quantityOnHandList.get(a);
                                    if (qt.getItemCode().equals(oder.getCode())){
                                        qt.setQunatityOnHand(String.valueOf(Integer.parseInt(qt.getQunatityOnHand())+Integer.parseInt(oder.getQty())));
                                        if (cmb_itemcode.getSelectionModel().getSelectedItem().equals(oder.getCode())){
                                            txt_qtyonhand.setText(qt.getQunatityOnHand());
                                        }

                                    }
                                }
                            }
                            else {
                                for (int a=0;a<quantityOnHandList.size();a++){
                                    OnHandQunatites qt = quantityOnHandList.get(a);
                                    if (qt.getItemCode().equals(oder.getCode())){
                                        qt.setQunatityOnHand(String.valueOf(Integer.parseInt(qt.getQunatityOnHand())+Integer.parseInt(oder.getQty())));
                                        if (cmb_itemcode.getSelectionModel().getSelectedItem().equals(oder.getCode())){
                                            txt_qtyonhand.setText(qt.getQunatityOnHand());
                                        }

                                    }
                                }
                                tbl_order.getItems().remove(oder);
                            }
                        }
                );
            }
        });
        tbl_order.getColumns().add(unfriendCol);

        OrderBOImpl orderBOImpl = AppInitializer.ctx.getBean(OrderBOImpl.class);
        List<ItemDTO> itemDTOS = orderBOImpl.allItems();
        for (ItemDTO itemDTO:itemDTOS) {
            quantityOnHandList.add(new OnHandQunatites(itemDTO.getCode(),itemDTO.getQty()));
        }


        String sql2 = "SELECT code FROM item";


        ObservableList cids = cmb_cid.getItems();
//        cids = orderBOImpl.getAllCustomerId(cids);

        PreparedStatement pst2 = con.prepareStatement(sql2);
        ResultSet rst2 = pst2.executeQuery();
        ObservableList codes = cmb_itemcode.getItems();
        while (rst2.next()){
            codes.add(rst2.getString("code"));
        }

        cmb_cid.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                String sql3 = "SELECT * FROM customer WHERE id=?";
                try  {
                    PreparedStatement pst3 = con.prepareStatement(sql3);
                    pst3.setString(1,cmb_cid.getSelectionModel().getSelectedItem().toString());
                    ResultSet rst3 = pst3.executeQuery();
                    if (rst3.next()){
                        txt_cusname.setText(rst3.getString("name"));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        cmb_itemcode.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //btn_add.setVisible(true);
                //btn_update.setVisible(false);

                String sql3 = "SELECT * FROM item WHERE code=?";
                try  {
                    PreparedStatement pst3 = con.prepareStatement(sql3);
                    pst3.setString(1,cmb_itemcode.getSelectionModel().getSelectedItem().toString());
                    ResultSet rst3 = pst3.executeQuery();

                    if (rst3.next()){
                        txt_description.setText(rst3.getString("description"));
                        txt_price.setText(rst3.getString("price"));

                        for (int z=0;z<quantityOnHandList.size();z++){
                            OnHandQunatites ot = (OnHandQunatites) quantityOnHandList.get(z);
                            if (ot.getItemCode().equals(cmb_itemcode.getSelectionModel().getSelectedItem().toString())){
                                txt_qtyonhand.setText(ot.getQunatityOnHand());
                            }
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        String sql4 = "SELECT COUNT(orderid) AS count FROM orderdetails";
        try {
            PreparedStatement pst4 = con.prepareStatement(sql4);
            ResultSet rst4 = pst4.executeQuery();
            if (rst4.next()){
                orderId = Integer.parseInt(rst4.getString("count"));
                orderId = orderId+1;
                txt_oid.setText("OD-"+Integer.toString(orderId));
            }
            else{
                orderId = 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        txt_qty.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            public void handle( KeyEvent t ) {
                char ar[] = t.getCharacter().toCharArray();
                char ch = ar[t.getCharacter().toCharArray().length - 1];
                if (!(ch >= '0' && ch <= '9')) {
                    t.consume();
                }
            }
        });

        tbl_order.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Order selectedItem = tbl_order.getSelectionModel().getSelectedItem();

                if (selectedItem!=null){
                    btn_update.setVisible(true);
                    btn_add.setVisible(false);

                    cmb_itemcode.getSelectionModel().select(tbl_order.getSelectionModel().getSelectedItem().getCode());
                    txt_qty.setText(tbl_order.getSelectionModel().getSelectedItem().getQty());
                }

            }
        });


    }

    public void purchaseOrder(ActionEvent actionEvent) throws Exception {

        if (cmb_cid.getSelectionModel().getSelectedItem() !=null) {

            OrderBOImpl orderBOImpl = AppInitializer.ctx.getBean(OrderBOImpl.class);
            orderBOImpl.insertOrderDetails(new OrderDetailsDTO(txt_oid.getText(),cmb_cid.getSelectionModel().getSelectedItem().toString(),txt_odate.getText()));

                ObservableList<Order> items = tbl_order.getItems();
                for (int i = 0; i < items.size(); i++) {
                    Order or = items.get(i);
                    orderBOImpl.insertOrderItems(new OrderDetailsDTO(txt_oid.getText(),or.getCode(),or.getQty()));

                }


            for (int i = 0; i < quantityOnHandList.size(); i++) {
                OnHandQunatites onHandQunatites = quantityOnHandList.get(i);
                orderBOImpl.updateItemQty(onHandQunatites.getItemCode(),onHandQunatites.getQunatityOnHand());
            }


            Alert alt = new Alert(Alert.AlertType.INFORMATION, "Item purchased!!");
            alt.show();

            orderId = orderId + 1;
            txt_oid.setText("OD" + Integer.toString(orderId));
            cmb_cid.requestFocus();

        }
        else {
            Alert alt = new Alert(Alert.AlertType.ERROR,"Select customer first");
            alt.show();
        }

    }

    private void findTot(){
        ObservableList list = tbl_order.getItems();
        if (list != null){
            int tot=0;
            for (int i=0; i<list.size();i++){
                Order or = (Order) list.get(i);
                tot = tot + Integer.parseInt(or.getTotal());
            }
            txt_total.setText(String.valueOf(tot));
        }
    }

    /*private void updateQtyOnHand(String qty,String code){
        String newQuantity = String.valueOf(Integer.parseInt(txt_qtyonhand.getText()) - Integer.parseInt(qty));
        for (int i=0;i<quantityOnHandList.size();i++){
            OnHandQunatites qt = quantityOnHandList.get(i);
            if (qt.getItemCode().equals(code)){
                qt.setQunatityOnHand(newQuantity);
                txt_qtyonhand.setText(newQuantity);
            }
        }
    }*/

    public void addItem(ActionEvent actionEvent) throws JRException {

        if (txt_qty.getText().length() == 0 || cmb_itemcode.getSelectionModel().getSelectedItem() == null){
            Alert alt = new Alert(Alert.AlertType.ERROR,"Fill form correctly!!!");
            alt.show();
        }
        else{
            if (Integer.parseInt(txt_qtyonhand.getText())<Integer.parseInt(txt_qty.getText()) || Integer.parseInt(txt_qty.getText())<1){
                Alert alt = new Alert(Alert.AlertType.ERROR,"Quatity does not correct!!");
                alt.show();
            }
            else {
                ObservableList<Order> items = tbl_order.getItems();
                String code = cmb_itemcode.getSelectionModel().getSelectedItem().toString();
                String des = txt_description.getText();
                String qty = txt_qty.getText();
                String price = txt_price.getText();
                String total = Integer.toString(Integer.parseInt(qty)*Integer.parseInt(price));
                int j=0;

                if (items.size()!=0){
                    for (int i=0;i<items.size();i++){
                        Order order = items.get(i);
                        if (order.getCode() == cmb_itemcode.getSelectionModel().getSelectedItem().toString()){
                            int qtyt = Integer.parseInt(order.getQty()) + Integer.parseInt(qty);
                            items.remove(order);
                            int tot = qtyt*Integer.parseInt(price);
                            items.add(new Order(code,des,Integer.toString(qtyt),price,Integer.toString(tot)));
                            j++;

                            findTot();

                            String newQuantity = String.valueOf(Integer.parseInt(txt_qtyonhand.getText()) - Integer.parseInt(qty));
                            for (int a=0;a<quantityOnHandList.size();a++){
                                OnHandQunatites qt = quantityOnHandList.get(a);
                                if (qt.getItemCode().equals(code)){
                                    qt.setQunatityOnHand(newQuantity);
                                    txt_qtyonhand.setText(newQuantity);
                                }
                            }


                            break;
                        }
                    }
                    if (j==0){

                        items.add(new Order(code,des,qty,price,total));
                        findTot();

                        String newQuantity = String.valueOf(Integer.parseInt(txt_qtyonhand.getText()) - Integer.parseInt(qty));
                        for (int i=0;i<quantityOnHandList.size();i++){
                            OnHandQunatites qt = quantityOnHandList.get(i);
                            if (qt.getItemCode().equals(code)){
                                qt.setQunatityOnHand(newQuantity);
                                txt_qtyonhand.setText(newQuantity);
                            }
                        }
                    }
                }
                else {
                    items.add(new Order(code,des,qty,price,total));
                    findTot();
                    String newQuantity = String.valueOf(Integer.parseInt(txt_qtyonhand.getText()) - Integer.parseInt(qty));
                    for (int i=0;i<quantityOnHandList.size();i++){
                        OnHandQunatites qt = quantityOnHandList.get(i);
                        if (qt.getItemCode().equals(code)){
                            qt.setQunatityOnHand(newQuantity);
                            txt_qtyonhand.setText(newQuantity);
                        }
                    }
                }
            }
        }

}

    public void backToDashboard(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(this.getClass().getResource("/VIew/Dashboard.fxml"));
        Scene mainScreen = new Scene(root);

        Stage stage = (Stage) tbl_order.getScene().getWindow();
        stage.setScene(mainScreen);
        stage.setTitle("Dashboard");

    }

    public void updateRow(ActionEvent actionEvent) {

        if (txt_qty.getText().length() == 0){
            Alert alt = new Alert(Alert.AlertType.ERROR,"Fill form correctly!!!");
            alt.show();
        }
        else {
            if (Integer.parseInt(txt_qtyonhand.getText())<Integer.parseInt(txt_qty.getText()) || Integer.parseInt(txt_qty.getText())<1){
                Alert alt = new Alert(Alert.AlertType.ERROR,"Quatity does not correct!!");
                alt.show();
            }
            else {
                String itemcode = cmb_itemcode.getSelectionModel().getSelectedItem().toString();
                String description = txt_description.getText();
                String price = txt_price.getText();
                String qty = txt_qty.getText();
                String total = String.valueOf(Integer.parseInt(txt_qty.getText())*Integer.parseInt(txt_price.getText()));
                String originalQty = null;

                Order selectedItem = tbl_order.getSelectionModel().getSelectedItem();
                selectedItem.setCode(itemcode);
                selectedItem.setDescription(description);
                selectedItem.setPrice(price);
                selectedItem.setQty(qty);
                selectedItem.setTotal(total);
                tbl_order.refresh();

                for (int i=0; i<quantityOnHandList.size(); i++){
                    OnHandQunatites onHandQunatites = quantityOnHandList.get(i);
                    if (onHandQunatites.getItemCode().equals(itemcode)){
                        OrderBOImpl orderBOImpl = new OrderBOImpl();
                        originalQty = orderBOImpl.qtyGetByCode(itemcode);

                        onHandQunatites.setQunatityOnHand(String.valueOf(Integer.parseInt(originalQty)-Integer.parseInt(qty)));
                        txt_qtyonhand.setText(String.valueOf(Integer.parseInt(originalQty)-Integer.parseInt(qty)));
                    }
                }
            }
        }
    }

    public void addNeworder(ActionEvent actionEvent) {
        cmb_itemcode.setDisable(false);
        cmb_itemcode.requestFocus();
        txt_qty.setText("");

        btn_update.setVisible(false);
        btn_add.setVisible(true);
    }

    public void generateItemsReport(ActionEvent actionEvent) throws JRException {

        InputStream resourceAsStreammain = this.getClass().getResourceAsStream("/Reports/GenerateOrderReportMain.jrxml");
        JasperDesign loadmain = JRXmlLoader.load(resourceAsStreammain);
        JasperReport jasperReportmain = JasperCompileManager.compileReport(loadmain);

        InputStream resourceAsStreamsub = this.getClass().getResourceAsStream("/Reports/GenerateOrderReportMain.jrxml");
        JasperDesign loadsub = JRXmlLoader.load(resourceAsStreamsub);
        JasperReport jasperReportsub = JasperCompileManager.compileReport(loadsub);

        Map<String, Object> pram = new HashMap<>();
        pram.put("cusid",jasperReportsub);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReportmain, pram, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }
}
