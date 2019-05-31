package lk.ijse.dep.DAO.custom.Impl;

import lk.ijse.dep.DAO.DAO.custom.OrderItemsDAO;
import lk.ijse.dep.Entities.OrderItems;
import lk.ijse.dep.Entities.OrderItemsPK;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

@Component
public class OrderItemsDAOImpl implements OrderItemsDAO {

    private EntityManager session;
    public void save(OrderItems orderitems) throws SQLException {
        session.persist(orderitems);
    }

    public void update(OrderItems orderitems) throws SQLException {
        session.merge(orderitems);
    }

    public  void delete(OrderItemsPK orderItemsPK) throws SQLException {
        OrderItemsPK load = session.getReference(OrderItemsPK.class, orderItemsPK);
    }

    public List<OrderItems> findAll() throws SQLException {
        List<OrderItems> list = session.createQuery("from Entities.OrderItems", OrderItems.class).getResultList();
        return list;
    }

    public OrderItems find(OrderItemsPK orderItemsPK) throws SQLException {
        OrderItems orderItems = (OrderItems) session.createNativeQuery("SELECT * FROM orderitems WHERE orderid=? AND itemCode=?", OrderItems.class)
                .setParameter(1, orderItemsPK.getOrderid()).setParameter(2, orderItemsPK.getItemcode()).getSingleResult();
        return orderItems;

    }

    @Override
    public void updateQtyOnHand(String itemcode,String qtyOnHand) {
        session.createNativeQuery("UPDATE item SET qty=? WHERE code=?").setParameter(1,qtyOnHand)
                .setParameter(2,itemcode).executeUpdate();
    }

    public EntityManager getSession() {
        return session;
    }

    public void setSession(EntityManager session) {
        this.session = session;
    }
}
