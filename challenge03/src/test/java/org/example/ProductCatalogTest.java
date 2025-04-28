package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
// test case of Product Catalog
public class ProductCatalogTest {
    @Test
    void testElectronicsFinalPrice() {
        Electronics electronics = new Electronics("ABC11", "Laptop", 50000.0, 3000.0);
        assertEquals(53000.0, electronics.getFinalPrice(), 0.001);
    }

    @Test
    void testClothingFinalPrice() {
        Clothing clothing = new Clothing("CDE11", "Shirt", 2000.0, 10.0);
        assertEquals(1800.0, clothing.getFinalPrice(), 0.001);
    }

    @Test
    void testGroceriesFinalPrice() {
        Groceries groceries = new Groceries("EFG11", "food", 1000.0, 5.0);
        assertEquals(1050.0, groceries.getFinalPrice(), 0.001);
    }

}
