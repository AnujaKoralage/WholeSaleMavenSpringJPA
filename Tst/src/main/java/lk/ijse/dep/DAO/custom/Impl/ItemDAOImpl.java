package lk.ijse.dep.DAO.custom.Impl;

import lk.ijse.dep.DAO.DAO.custom.ItemDao;
import lk.ijse.dep.Entities.Item;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class ItemDAOImpl implements ItemDao {

    private EntityManager session;

    public void save(Item item) throws Exception {
        session.persist(item);
    }

    public void update(Item item) throws Exception {
        session.merge(item);
    }

    public void delete(String code) throws Exception {
        Item item = session.getReference(Item.class,code);
        session.remove(item);
    }

    public List<Item> findAll() throws Exception {
        List<Item> list = session.createNativeQuery("SELECT * FROM Item", Item.class).getResultList();

        return list;
    }

    public Item find(String code) throws Exception {
        Item item = (Item) session.createNativeQuery("SELECT * FROM Item WHERE code=?", Item.class).setParameter(1,code).getSingleResult();
        return item;
    }

    public EntityManager getSession() {
        return session;
    }

    public void setSession(EntityManager session) {
        this.session = session;
    }
}
