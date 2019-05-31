package lk.ijse.dep.Business.custom;

import lk.ijse.dep.DTO.ItemDTO;
import lk.ijse.dep.DTO.OrderDetailsDTO;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO{

    public String qtyGetByCode(String itemcode);
    public void insertOrderDetails(OrderDetailsDTO orderDetailsDTO) throws Exception;
    public void insertOrderItems(OrderDetailsDTO orderDetailsDTO) throws Exception;
    public void updateItemQty(String qtyOnHand, String itemcode);
    public List<ItemDTO> allItems() throws Exception;
    public ObservableList getAllCustomerId(ObservableList list) throws SQLException;

}
