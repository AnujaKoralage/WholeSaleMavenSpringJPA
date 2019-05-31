package lk.ijse.dep.UtilityClasses;

public class SearchTableHelper {

    private String orderid;
    private String orderdate;
    private String cusid;
    private String cusname;
    private String total;

    public SearchTableHelper() {
    }

    public SearchTableHelper(String orderid, String orderdate, String cusid, String cusname, String total) {
        this.orderid = orderid;
        this.orderdate = orderdate;
        this.cusid = cusid;
        this.cusname = cusname;
        this.total = total;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getCusid() {
        return cusid;
    }

    public void setCusid(String cusid) {
        this.cusid = cusid;
    }

    public String getCusname() {
        return cusname;
    }

    public void setCusname(String cusname) {
        this.cusname = cusname;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
