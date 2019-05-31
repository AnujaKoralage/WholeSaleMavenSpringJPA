package lk.ijse.dep.Entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderItemsPK implements Serializable {

    @Column(name = "orderid")
    private String orderid;
    @Column(name = "itemcode")
    private String itemcode;

    public OrderItemsPK() {
    }

    public OrderItemsPK(String orderid, String itemcode) {
        this.orderid = orderid;
        this.itemcode = itemcode;
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
}
