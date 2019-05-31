package lk.ijse.dep.Business.custom;

import lk.ijse.dep.DTO.CustomerDTO;

import java.util.List;

public interface CustomerBO extends SuperBO {

    public List<CustomerDTO> allCustomers() throws Exception;
    public void saveCustomer(CustomerDTO customer) throws Exception;
    public void deleteCustomer(String id) throws Exception;
    public boolean customerExistsinOrder(String id) throws Exception;
    public void updateCustomer(CustomerDTO customer) throws Exception;

}
