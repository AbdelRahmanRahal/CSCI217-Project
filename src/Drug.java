

public class Drug {
    private String id;
    private String name;
    private double price;
    private String category;
    private double totalPrice;
    private int availableQuantity;

    public Drug(String id, String name, double price, String category, int availableQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.availableQuantity = availableQuantity;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public double getTotalPrice() { return totalPrice; }

    public int getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }
}

