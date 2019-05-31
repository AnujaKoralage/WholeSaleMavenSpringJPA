package lk.ijse.dep.DAO.DAO.custom;

import lk.ijse.dep.DAO.CRUDDAO;
import lk.ijse.dep.Entities.OrderDetails;

public interface OrderDetailsDAO extends CRUDDAO<OrderDetails,String> {
    public String getQtyByCode(String itemcode);

}
