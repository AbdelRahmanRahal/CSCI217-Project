public class Pharmacy {
    private List<Drug> drugs;
    private int capacity;

    public Pharmacy(int capacity) {
        this.capacity = capacity;
        this.drugs = new ArrayList<>();
    }

    public void addDrug(Drug drug) {
        if (isValidDrug(drug)) {
            if (drugs.size() < capacity) {
                drugs.add(drug);
                System.out.println("Drug added successfully.");
            } else {
                System.out.println("Pharmacy is full. Cannot add more drugs.");
            }
        } else {
            System.out.println("Invalid drug details.");
        }
    }

    public void removeDrug(String id) {
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
    }

    public void placeOrder(String id, int quantity) {
        try {
            Drug drug = findDrugById(id);
            if (drug!= null && drug.getAvailableQuantity() >= quantity) {
                double totalPrice = drug.getPrice() * quantity;
                System.out.println("Total price: " + totalPrice);
                drug.setAvailableQuantity(drug.getAvailableQuantity() - quantity);
                System.out.println("Order placed successfully.");
            } else {
                System.out.println("Drug not available or insufficient quantity.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public double getTotalSales() {
        double totalSales = 0;
        for (Drug drug : drugs) {
            totalSales += drug.getPrice() * drug.getAvailableQuantity();
        }
        return totalSales;
    }

    private boolean isValidDrug(Drug drug) {
        return drug.getId()!= null &&!drug.getName().isEmpty() &&
                drug.getPrice() >= 0 && drug.getCategory()!= null &&
                drug.getQuantity() > 0;
    }

    private Drug findDrugById(String id) throws Exception {
        return drugs.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Drug not found."));
    }
}
