package lk.ijse.dep.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class OrderDetails extends SuperEntity{
    @Id
    private String orderid;
    @ManyToOne
    @JoinColumn(name = "cusid",referencedColumnName = "id")
    private Customer cusid;
    private String orderdate;

    @OneToMany(mappedBy = "orderDetails")
    private List<OrderItems> orderItems;

    public OrderDetails() {
    }

    public OrderDetails(String orderid, Customer cusid, String orderdate) {
        this.orderid = orderid;
        this.cusid = cusid;
        this.orderdate = orderdate;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Customer getCusid() {
        return cusid;
    }

    public void setCusid(Customer cusid) {
        this.cusid = cusid;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderid='" + orderid + '\'' +
                ", cusid='" + cusid + '\'' +
                ", orderdate='" + orderdate + '\'' +
                '}';
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

}
