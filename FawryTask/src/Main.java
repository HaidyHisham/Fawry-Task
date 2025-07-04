import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Create products
        Product cheese = new Product("Cheese", 100, 10, true, true, LocalDate.of(2025, 8, 1), 0.2);
        Product biscuits = new Product("Biscuits", 150, 5, true, true, LocalDate.of(2025, 8, 15), 0.7);
        Product scratchCard = new Product("ScratchCard", 50, 100, false, false, null, 0);

        // Create customer
        double balance = 1000;
        Map<Product, Integer> cart = new LinkedHashMap<>();

        // Add to cart
        addToCart(cart, cheese, 2);
        addToCart(cart, biscuits, 1);
        addToCart(cart, scratchCard, 1);

        // Check for errors
        for (Product p : cart.keySet()) {
            if (p.isExpired()) {
                System.out.println("Product expired: " + p.getName());
                return;
            }
        }

        // Calculate totals
        double subtotal = 0;
        boolean hasShipping = false;
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            subtotal += entry.getKey().getPrice() * entry.getValue();
            if (entry.getKey().requiresShipping()) {
                hasShipping = true;
            }
        }

        double shipping = hasShipping ? 30 : 0;
        double total = subtotal + shipping;

        if (balance < total) {
            System.out.println("Not enough balance.");
            return;
        }

        // Print shipment notice
        if (hasShipping) {
            System.out.println("\n- * Shipment notice **");
            double totalWeight = 0;
            for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
                Product p = entry.getKey();
                int qty = entry.getValue();
                if (p.requiresShipping()) {
                    double itemWeight = p.getWeight() * qty;
                    System.out.printf("%dx %-12s %.0fg\n", qty, p.getName(), itemWeight * 1000);
                    totalWeight += itemWeight;
                }
            }
            System.out.printf("\nTotal package weight %.1fkg\n", totalWeight);
        }

        // Print receipt
        balance -= total;
        System.out.println("\n- * Checkout receipt **");
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            System.out.printf("%dx %-12s %.0f\n", qty, p.getName(), qty * p.getPrice());
        }

        System.out.println("- ---------------------");
        System.out.printf("Subtotal         %.0f\n", subtotal);
        System.out.printf("Shipping         %.0f\n", shipping);
        System.out.printf("Amount           %.0f\n", total);
        System.out.printf("Remaining Balance %.0f\n", balance);
    }

    public static void addToCart(Map<Product, Integer> cart, Product product, int qty) {
        if (qty > product.getQuantity()) {
            System.out.println("Not enough stock for " + product.getName());
            return;
        }
        product.decreaseStock(qty);
        cart.put(product, qty);
    }
}
