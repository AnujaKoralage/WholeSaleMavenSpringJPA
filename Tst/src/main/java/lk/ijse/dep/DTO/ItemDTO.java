package lk.ijse.dep.DTO;

public class ItemDTO {

    private String code;
    private String description;
    private String qty;
    private String price;

    public ItemDTO() {
    }

    public ItemDTO(String code, String description, String qty, String price) {
        this.code = code;
        this.description = description;
        this.qty = qty;
        this.price = price;
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
}
