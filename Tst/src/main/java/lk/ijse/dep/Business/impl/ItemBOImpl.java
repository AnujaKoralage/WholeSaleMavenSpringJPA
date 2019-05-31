package lk.ijse.dep.Business.impl;

import lk.ijse.dep.Business.custom.ItemBO;
import lk.ijse.dep.DAO.DAO.custom.OrderItemsDAO;
import lk.ijse.dep.DAO.custom.Impl.ItemDAOImpl;
import lk.ijse.dep.DAO.custom.Impl.OrderItemsDAOImpl;
import lk.ijse.dep.DTO.ItemDTO;
import lk.ijse.dep.Entities.Item;
import lk.ijse.dep.Entities.OrderItems;
import lk.ijse.dep.UtilityClasses.EntityManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
@Component
public class ItemBOImpl implements ItemBO {
    @Autowired
    private ItemDAOImpl itemDAO;
    @Autowired
    private OrderItemsDAO orderDetailsDAO;

//    @Autowired
private EntityManager session = EntityManagerUtil.getInstance().getManager();

    public List<ItemDTO> getAllItems() throws Exception {

        itemDAO.setSession(session);
        return itemDAO.findAll().stream().map(new Function<Item, ItemDTO>() {
            @Override
            public ItemDTO apply(Item item) {
                return new ItemDTO(item.getCode(),item.getDescription(),item.getQty(),item.getPrice());
            }
        }).collect(Collectors.toList());

        /*List<Item> items = itemDAO.findAll();
        ArrayList<ItemDTO> list = new ArrayList<>();

        for (Item item:items) {
            list.add(new ItemDTO(item.getCode(),item.getDescription(),item.getQty(),item.getPrice()));
        }

        return list;*/
    }

    public void saveItem(ItemDTO itemDTO) throws Exception {
        itemDAO.setSession(session);
        itemDAO.save(new Item(itemDTO.getCode(), itemDTO.getDescription(), itemDTO.getQty(), itemDTO.getPrice()));

    }

    public void deleteItem(String id) throws Exception {
        itemDAO.setSession(session);
        itemDAO.delete(id);

    }

    public boolean itemExistsinOrder(String id) throws Exception {
        itemDAO.setSession(session);
        List<OrderItems> orderItems = orderDetailsDAO.findAll();

        for (OrderItems orderitem:orderItems) {
            if (orderitem.getOrderItemsPK().getItemcode().equals(id)){
                return true;
            }
        }
        return false;
    }

    public void updateItem(ItemDTO item) throws Exception {
        itemDAO.setSession(session);
        itemDAO.update(new Item(item.getCode(), item.getDescription(), item.getQty(), item.getPrice()));
    }

}
