package lk.ijse.dep.Business.impl;

import lk.ijse.dep.Business.custom.OrderBO;
import lk.ijse.dep.DAO.custom.Impl.CustomerDAOImpl;
import lk.ijse.dep.DAO.custom.Impl.ItemDAOImpl;
import lk.ijse.dep.DAO.custom.Impl.OrderDetailsDAOImpl;
import lk.ijse.dep.DAO.custom.Impl.OrderItemsDAOImpl;
import lk.ijse.dep.DTO.ItemDTO;
import lk.ijse.dep.DTO.OrderDetailsDTO;
import lk.ijse.dep.Entities.Item;
import lk.ijse.dep.Entities.OrderItems;
import javafx.collections.ObservableList;
import lk.ijse.dep.UtilityClasses.EntityManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OrderBOImpl implements OrderBO {

    private EntityManager entityManager = EntityManagerUtil.getInstance().getManager();
    @Autowired
    OrderDetailsDAOImpl orderDetailsDAO;
    @Autowired
    CustomerDAOImpl customerDAO;
    @Autowired
    OrderItemsDAOImpl dao;
    @Autowired
    ItemDAOImpl itemDAO;

public String qtyGetByCode(String itemcode){


    orderDetailsDAO.setSession(entityManager);
    return orderDetailsDAO.getQtyByCode(itemcode);
}

    public void insertOrderDetails(OrderDetailsDTO orderDetailsDTO) throws Exception {


        entityManager.getTransaction().begin();
        orderDetailsDAO.setSession(entityManager);
        //dao.save(new OrderDetails(orderDetailsDTO.getOrderid(),orderDetailsDTO.getCusid(),orderDetailsDTO.getOrderdate()));
        entityManager.getTransaction().commit();

    }

    public void insertOrderItems(OrderDetailsDTO orderDetailsDTO) throws Exception {
        entityManager.getTransaction().begin();
        dao.setSession(entityManager);
        dao.save(new OrderItems(orderDetailsDTO.getOrderid(),orderDetailsDTO.getCusid(),orderDetailsDTO.getOrderdate()));
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    public void updateItemQty(String qtyOnHand, String itemcode){

        entityManager.getTransaction().begin();
            dao.setSession(entityManager);
            dao.updateQtyOnHand(qtyOnHand,itemcode);
        entityManager.getTransaction().commit();

    }

    public List<ItemDTO> allItems() throws Exception {

        dao.setSession(entityManager);

        return itemDAO.findAll().stream().map(new Function<Item, ItemDTO>() {
            @Override
            public ItemDTO apply(Item item) {
                return new ItemDTO(item.getCode(),item.getDescription(),item.getQty(),item.getPrice());
            }
        }).collect(Collectors.toList());

        /*List<Item> all = dao.findAll();

        List<ItemDTO> list  = new ArrayList<>();
        for (Item item:all) {
            list.add(new ItemDTO(item.getCode(),item.getDescription(),item.getQty(),item.getPrice()));
        }

        return list;*/
    }

    public ObservableList getAllCustomerId(ObservableList list) throws SQLException {


        customerDAO.setEntityManager(entityManager);
        ObservableList id = customerDAO.getId(list);

        return id;
    }


}
