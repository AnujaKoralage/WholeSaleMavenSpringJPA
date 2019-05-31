package lk.ijse.dep.DAO.custom.Impl;

import lk.ijse.dep.DAO.DAO.custom.OrderDetailsDAO;
import lk.ijse.dep.Entities.OrderDetails;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

@Component
public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    private EntityManager session;

    public void save(OrderDetails orderdetails) throws SQLException {
        session.persist(orderdetails);
    }

    public void update(OrderDetails orderdetails) throws SQLException {
        session.merge(orderdetails);
    }

    public void delete(String orderid) throws SQLException {
        OrderDetails orderDetails = session.getReference(OrderDetails.class,orderid);
        session.remove(orderDetails);
    }

    public List<OrderDetails> findAll() throws SQLException {
        List<OrderDetails> list = session.createQuery("from Entities.OrderDetails", OrderDetails.class).getResultList();
        return list;
    }

    public OrderDetails find(String orderid) throws SQLException {
        OrderDetails orderDetails = (OrderDetails) session.createNativeQuery("SELECT * FROM orderdetails WHERE orderid=?", OrderDetails.class).setParameter(1,orderid).getSingleResult();
        return orderDetails;

    }

    @Override
    public String getQtyByCode(String itemcode) {
        String s = (String) session.createNativeQuery("SELECT qty FROM item WHERE code=?", String.class).setParameter(1, itemcode).getSingleResult();
        return s;
    }

    public EntityManager getSession() {
        return session;
    }

    public void setSession(EntityManager session) {
        this.session = session;
    }
}
