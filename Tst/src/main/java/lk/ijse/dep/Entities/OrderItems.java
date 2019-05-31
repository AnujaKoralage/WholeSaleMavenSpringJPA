package lk.ijse.dep.Entities;

import javax.persistence.*;

@Entity
public class OrderItems extends SuperEntity{

    @EmbeddedId
    private OrderItemsPK orderItemsPK;
    private String qty;

    @ManyToOne
    @JoinColumn (name = "orderid" ,referencedColumnName = "orderid", insertable = false, updatable = false)
    private
    OrderDetails orderDetails;

    @ManyToOne
    @JoinColumn (name = "itemcode",referencedColumnName = "code", insertable = false, updatable = false)
    private
    Item item;

    public OrderItems() {
    }

    public OrderItems(OrderItemsPK orderItemsPK, String qty) {
        this.orderItemsPK = orderItemsPK;
        this.qty = qty;
    }

    public OrderItems(String orderid, String itemcode,String qty) {
        this.orderItemsPK = new OrderItemsPK(orderid,itemcode);
        this.qty = qty;
    }

    public OrderItemsPK getOrderItemsPK() {
        return orderItemsPK;
    }

    public void setOrderItemsPK(OrderItemsPK orderItemsPK) {
        this.orderItemsPK = orderItemsPK;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "OrderItems{" +
                "orderItemsPK=" + orderItemsPK +
                ", qty='" + qty + '\'' +
                '}';
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
