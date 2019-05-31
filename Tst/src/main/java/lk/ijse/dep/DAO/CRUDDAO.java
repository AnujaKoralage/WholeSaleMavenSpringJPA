package lk.ijse.dep.DAO;

import lk.ijse.dep.Entities.SuperEntity;

import java.util.List;

public interface CRUDDAO<T extends SuperEntity,ID> extends SuperDAO {

    void save(T entity) throws Exception;

    void update(T entity) throws Exception;

    void delete(ID entityID) throws Exception;

    List<T> findAll() throws Exception;

    T find(ID entityID) throws Exception;

}
