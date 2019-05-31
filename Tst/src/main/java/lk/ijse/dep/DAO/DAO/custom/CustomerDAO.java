package lk.ijse.dep.DAO.DAO.custom;

import lk.ijse.dep.DAO.CRUDDAO;
import lk.ijse.dep.Entities.Customer;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import java.sql.SQLException;

public interface CustomerDAO extends CRUDDAO<Customer,String> {
    public ObservableList getId(ObservableList list) throws SQLException;
    public void setEntityManager(EntityManager entityManager);


}
