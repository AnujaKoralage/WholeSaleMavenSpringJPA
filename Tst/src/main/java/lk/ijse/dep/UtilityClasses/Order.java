package lk.ijse.dep.UtilityClasses;

public class Order {

    private String code;
    private String description;
    private String qty;
    private String price;
    private String total;

    public Order() {
    }

    public Order(String code, String description, String qty, String price, String total) {
        this.code = code;
        this.description = description;
        this.qty = qty;
        this.price = price;
        this.total = total;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
