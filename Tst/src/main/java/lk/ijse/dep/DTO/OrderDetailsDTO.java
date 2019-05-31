package lk.ijse.dep.DTO;

public class OrderDetailsDTO {

    private String orderid;
    private String cusid;
    private String orderdate;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(String orderid, String cusid, String orderdate) {
        this.setOrderid(orderid);
        this.setCusid(cusid);
        this.setOrderdate(orderdate);
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getCusid() {
        return cusid;
    }

    public void setCusid(String cusid) {
        this.cusid = cusid;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }
}
