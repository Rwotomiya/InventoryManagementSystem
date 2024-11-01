import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class InventoryManagementSystem {
    private Map<String, Item> inventory;

    // Constructor to initialize the inventory
    public InventoryManagementSystem() {
        inventory = new HashMap<>();
    }

    // Inner class to represent an item in the inventory
    class Item {
        String name;
        int quantity;
        double price;

        Item(String name, int quantity, double price) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }

        // Display item details
        void displayItem() {
            System.out.println("Item: " + name + ", Quantity: " + quantity + ", Price: $" + price);
        }
    }

    // Method to add an item to the inventory
    public void addItem(String name, int quantity, double price) {
        if (inventory.containsKey(name)) {
            Item item = inventory.get(name);
            item.quantity += quantity;  // Update quantity if item already exists
            item.price = price; // Update price if changed
            System.out.println("Updated " + name + " in the inventory.");
        } else {
            inventory.put(name, new Item(name, quantity, price));
            System.out.println("Added " + name + " to the inventory.");
        }
    }

    // Method to remove an item from the inventory
    public void removeItem(String name) {
        if (inventory.containsKey(name)) {
            inventory.remove(name);
            System.out.println("Removed " + name + " from the inventory.");
        } else {
            System.out.println("Item not found in inventory.");
        }
    }

    // Method to edit an existing item in the inventory
    public void editItem(String name, int newQuantity, double newPrice) {
        Item item = inventory.get(name);
        if (item != null) {
            item.quantity = newQuantity;
            item.price = newPrice;
            System.out.println("Updated " + name + " with new quantity and price.");
        } else {
            System.out.println("Item not found in inventory.");
        }
    }

    // Method to check item details
    public void checkItem(String name) {
        Item item = inventory.get(name);
        if (item != null) {
            item.displayItem();
        } else {
            System.out.println("Item not found in inventory.");
        }
    }

    // Method to display all items in the inventory
    public void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Inventory List:");
            for (Item item : inventory.values()) {
                item.displayItem();
            }
        }
    }

    // Method to search for items within a specific price range
    public void searchByPriceRange(double minPrice, double maxPrice) {
        System.out.println("Items within price range $" + minPrice + " - $" + maxPrice + ":");
        boolean found = false;
        for (Item item : inventory.values()) {
            if (item.price >= minPrice && item.price <= maxPrice) {
                item.displayItem();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No items found within this price range.");
        }
    }

    // Method to display items with low stock
    public void lowStockAlert(int threshold) {
        System.out.println("Items with stock below " + threshold + ":");
        boolean found = false;
        for (Item item : inventory.values()) {
            if (item.quantity < threshold) {
                item.displayItem();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No items are below the specified stock threshold.");
        }
    }

    // Method to calculate and display total inventory value
    public void calculateTotalInventoryValue() {
        double totalValue = 0;
        for (Item item : inventory.values()) {
            totalValue += item.price * item.quantity;
        }
        System.out.println("Total inventory value: $" + totalValue);
    }

    // Method to export inventory to a file
    public void exportInventoryToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Item item : inventory.values()) {
                writer.write("Item: " + item.name + ", Quantity: " + item.quantity + ", Price: $" + item.price + "\n");
            }
            System.out.println("Inventory exported to " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while exporting the inventory.");
            e.printStackTrace();
        }
    }

    // Main method for user interaction
    public static void main(String[] args) {
        InventoryManagementSystem ims = new InventoryManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Edit Item");
            System.out.println("4. Check Item");
            System.out.println("5. Display Inventory");
            System.out.println("6. Search by Price Range");
            System.out.println("7. Low Stock Alert");
            System.out.println("8. Total Inventory Value");
            System.out.println("9. Export Inventory to File");
            System.out.println("10. Exit");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    ims.addItem(name, quantity, price);
                    break;
                case 2:
                    System.out.print("Enter item name to remove: ");
                    name = scanner.nextLine();
                    ims.removeItem(name);
                    break;
                case 3:
                    System.out.print("Enter item name to edit: ");
                    name = scanner.nextLine();
                    System.out.print("Enter new quantity: ");
                    int newQuantity = scanner.nextInt();
                    System.out.print("Enter new price: ");
                    double newPrice = scanner.nextDouble();
                    ims.editItem(name, newQuantity, newPrice);
                    break;
                case 4:
                    System.out.print("Enter item name to check: ");
                    name = scanner.nextLine();
                    ims.checkItem(name);
                    break;
                case 5:
                    ims.displayInventory();
                    break;
                case 6:
                    System.out.print("Enter minimum price: ");
                    double minPrice = scanner.nextDouble();
                    System.out.print("Enter maximum price: ");
                    double maxPrice = scanner.nextDouble();
                    ims.searchByPriceRange(minPrice, maxPrice);
                    break;
                case 7:
                    System.out.print("Enter stock threshold: ");
                    int threshold = scanner.nextInt();
                    ims.lowStockAlert(threshold);
                    break;
                case 8:
                    ims.calculateTotalInventoryValue();
                    break;
                case 9:
                    System.out.print("Enter filename to export to: ");
                    String filename = scanner.nextLine();
                    ims.exportInventoryToFile(filename);
                    break;
                case 10:
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
