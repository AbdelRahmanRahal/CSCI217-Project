public class Transaction {
    private String drugId;
    private String drugName;
    private int quantitySold;
    private double totalPrice;

    public Transaction(String drugId, String drugName, int quantitySold, double totalPrice) {
        this.drugId = drugId;
        this.drugName = drugName;
        this.quantitySold = quantitySold;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public String getDrugId() { return drugId; }
    public String getDrugName() { return drugName; }
    public int getQuantitySold() { return quantitySold; }
    public double getTotalPrice() { return totalPrice; }
}
