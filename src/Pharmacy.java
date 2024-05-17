import java.util.ArrayList;
import java.util.List;

public class Pharmacy {
    private List<Drug> drugs;
    private int capacity;
    private List<Transaction> transactions;

    public Pharmacy(int capacity) {
        this.drugs = new ArrayList<>();
        this.capacity = capacity;
        this.transactions = new ArrayList<>();
    }

    public List<Drug> getDrugs() { return drugs; }

    public List<Transaction> getTransactions() { return transactions; }

    public int addDrug(Drug drug) {
        // If a drug with the given ID already exists
        if (findDrugById(drug.getId()) != null)
            return -2;
        // If invalid details for the drug are passed
        if (!isValidDrug(drug))
            return -1;
        // If the pharmacy capacity is full
        if (drugs.size() + drug.getAvailableQuantity() >= capacity )
            return -3;

        drugs.add(drug);
        return 0;
    }

    public boolean removeDrug(String id) {
        try {
            Drug drugToRemove = findDrugById(id);
            if (drugToRemove!= null) {
                drugs.remove(drugToRemove);
                System.out.println("Drug removed successfully.");
            } else {
                System.out.println("Drug not found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return false;
    }

    public void placeOrder(String id, int quantity) {
        Drug drug = findDrugById(id);

        double price = drug.getPrice();
        if ("cosmetics".equalsIgnoreCase(drug.getCategory()))
            price *= 1.2; // Apply 20% surcharge

        double totalPrice = price * quantity;
        drug.setAvailableQuantity(drug.getAvailableQuantity() - quantity);
        transactions.add(new Transaction(id, drug.getName(), quantity, totalPrice));
    }


    public double getTotalSales() {
        return transactions.stream().mapToDouble(Transaction::getTotalPrice).sum();
    }


    private boolean isValidDrug(Drug drug) {
        return !drug.getId().isEmpty() && !drug.getName().isEmpty() &&
                drug.getPrice() >= 0 && !drug.getCategory().isEmpty() &&
                drug.getAvailableQuantity() > 0;
    }

    public Drug findDrugById(String id) {
        return drugs.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null); // Return null if no matching drug is found
    }

    public int getCurrentCapacity() {
        return drugs.size(); // Return the current number of drugs in the pharmacy
    }

    public int getCapacity() {
        return capacity; // Return the maximum capacity of the pharmacy
    }
}
