package lk.ijse.dep.DTO;

public class OrderItemDTO {

    private String orderid;
    private String itemcode;
    private String qty;

    public OrderItemDTO() {
    }

    public OrderItemDTO(String orderid, String itemcode, String qty) {
        this.orderid = orderid;
        this.itemcode = itemcode;
        this.qty = qty;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
