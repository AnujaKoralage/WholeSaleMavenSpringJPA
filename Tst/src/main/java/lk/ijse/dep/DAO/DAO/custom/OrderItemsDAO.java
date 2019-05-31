package lk.ijse.dep.DAO.DAO.custom;

import lk.ijse.dep.DAO.CRUDDAO;
import lk.ijse.dep.Entities.OrderItems;
import lk.ijse.dep.Entities.OrderItemsPK;

public interface OrderItemsDAO extends CRUDDAO<OrderItems,OrderItemsPK> {
    void updateQtyOnHand( String itemcode,String qtyOnHand);

}
