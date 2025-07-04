import java.time.LocalDate;

public class Product {
    private String name;
    private double price;
    private int quantity;
    private boolean isExpirable;
    private boolean requiresShipping;
    private LocalDate expiryDate;
    private double weight;

    public Product(String name, double price, int quantity, boolean isExpirable, boolean requiresShipping, LocalDate expiryDate, double weight) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.isExpirable = isExpirable;
        this.requiresShipping = requiresShipping;
        this.expiryDate = expiryDate;
        this.weight = weight;
    }

    public boolean isExpired() {
        if (!isExpirable) return false;
        return LocalDate.now().isAfter(expiryDate);
    }

    public boolean requiresShipping() {
        return requiresShipping;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void decreaseStock(int qty) {
        quantity -= qty;
    }
}
