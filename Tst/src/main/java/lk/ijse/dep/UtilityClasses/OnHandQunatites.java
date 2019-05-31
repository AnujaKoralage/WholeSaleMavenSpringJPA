package lk.ijse.dep.UtilityClasses;

public class OnHandQunatites {

    private String itemCode;
    private String qunatityOnHand;

    public OnHandQunatites() {
    }

    public OnHandQunatites(String itemCode, String qunatityOnHand) {
        this.itemCode = itemCode;
        this.qunatityOnHand = qunatityOnHand;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getQunatityOnHand() {
        return qunatityOnHand;
    }

    public void setQunatityOnHand(String qunatityOnHand) {
        this.qunatityOnHand = qunatityOnHand;
    }
}
