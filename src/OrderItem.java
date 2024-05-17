public class OrderItem {
    private String drugId;
    private int quantity;

    public OrderItem(String drugId, int quantity) {
        this.drugId = drugId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getDrugId() { return drugId; }
    public int getQuantity() { return quantity; }
}
