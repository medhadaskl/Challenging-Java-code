package org.example;

import java.util.Scanner;
// Abstract class Product
abstract class Product {
    private String productId;
    private String name;
    private double basePrice;

    public Product(String productId, String name, double basePrice) {
        this.productId = productId;
        this.name = name;
        this.basePrice = basePrice;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public abstract double getFinalPrice();
}

// Subclass Electronics
class Electronics extends Product {
    private double warrantyCost;

    public Electronics(String productId, String name, double basePrice, double warrantyCost) {
        super(productId, name, basePrice);
        this.warrantyCost = warrantyCost;
    }

    @Override
    public double getFinalPrice() {
        return getBasePrice() + warrantyCost;
    }
}

// Subclass Clothing
class Clothing extends Product {
    private double discount;

    public Clothing(String productId, String name, double basePrice, double discount) {
        super(productId, name, basePrice);
        this.discount = discount;
    }

    @Override
    public double getFinalPrice() {
        return getBasePrice() - (getBasePrice() * discount / 100);
    }
}

// Subclass Groceries
class Groceries extends Product {
    private double taxRate;

    public Groceries(String productId, String name, double basePrice, double taxRate) {
        super(productId, name, basePrice);
        this.taxRate = taxRate;
    }

    @Override
    public double getFinalPrice() {
        return getBasePrice() + (getBasePrice() * taxRate / 100);
    }
}

// Main class
public class ProductCatalog {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter product type (electronics, clothing, groceries): ");
        String type = scanner.nextLine();

        System.out.print("Enter product ID: ");
        String productId = scanner.nextLine();

        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter base price: ");
        double basePrice = scanner.nextDouble();

        Product product = null;
        switch (type.toLowerCase()) {
            case "electronics":
                System.out.print("Enter warranty cost: ");
                double warrantyCost = scanner.nextDouble();
                product = new Electronics(productId, name, basePrice, warrantyCost);
                break;
            case "clothing":
                System.out.print("Enter discount percentage: ");
                double discount = scanner.nextDouble();
                product = new Clothing(productId, name, basePrice, discount);
                break;
            case "groceries":
                System.out.print("Enter tax rate: ");
                double taxRate = scanner.nextDouble();
                product = new Groceries(productId, name, basePrice, taxRate);
                break;
            default:
                System.out.println("Invalid product type");
                return;
        }

        System.out.println("Final price of " + product.getName() + ": " + product.getFinalPrice());
    }
}
