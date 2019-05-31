package lk.ijse.dep.DAO.custom.Impl;

import lk.ijse.dep.DAO.DAO.custom.CustomerDAO;
import lk.ijse.dep.Entities.Customer;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

@Component
public class CustomerDAOImpl implements CustomerDAO {

    private EntityManager entityManager;
    @Override
    public void save(Customer customer) throws Exception {
        entityManager.persist(customer);
    }

    public void update(Customer customer) throws Exception {
        entityManager.merge(customer);
    }

    public  void delete(String id) throws Exception {
        Customer customer = entityManager.getReference(Customer.class, id);
        entityManager.remove(customer);
    }

    public List<Customer> findAll() throws Exception {

        List<Customer> list = entityManager.createQuery("From lk.ijse.dep.Entities.Customer",Customer.class).getResultList();

        return list;
    }

    public Customer find(String id) throws Exception {
        Customer customer = (Customer) entityManager.createNativeQuery("SELECT * FROM customer WHERE id=?", Customer.class).setParameter(1,id).getSingleResult();
        return customer;
    }

    public ObservableList getId(ObservableList cids) throws SQLException {
        List<Customer> cus = entityManager.createNativeQuery("SELECT id FROM customer", Customer.class).getResultList();
        for (int i=0;i<cus.size();i++){
            cids.add(cus.get(i).getId());
        }
        return cids;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
