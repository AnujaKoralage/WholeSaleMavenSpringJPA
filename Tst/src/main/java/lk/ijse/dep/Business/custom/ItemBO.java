package lk.ijse.dep.Business.custom;

import lk.ijse.dep.DTO.ItemDTO;

import java.util.List;

public interface ItemBO extends SuperBO{

    public List<ItemDTO> getAllItems() throws Exception;
    public void saveItem(ItemDTO itemDTO) throws Exception;
    public void deleteItem(String id) throws Exception;
    public boolean itemExistsinOrder(String id) throws Exception;
    public void updateItem(ItemDTO item) throws Exception;

}
