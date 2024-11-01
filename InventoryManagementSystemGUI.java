import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InventoryManagementSystemGUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField, quantityField, priceField;
    private Map<String, Item> inventory;

    public InventoryManagementSystemGUI() {
        inventory = new HashMap<>();
        initialize();
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

        // Get item details as an array for table model
        Object[] toTableRow() {
            return new Object[]{name, quantity, price};
        }
    }

    // Initialize the GUI
    private void initialize() {
        frame = new JFrame("Inventory Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Table model for inventory items
        String[] columnNames = {"Item Name", "Quantity", "Price ($)"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panel for input fields and buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        // Input fields
        panel.add(new JLabel("Item Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        panel.add(quantityField);

        panel.add(new JLabel("Price:"));
        priceField = new JTextField();
        panel.add(priceField);

        // Buttons
        JButton addButton = new JButton("Add Item");
        JButton removeButton = new JButton("Remove Item");
        JButton editButton = new JButton("Edit Item");
        JButton exportButton = new JButton("Export to File");

        panel.add(addButton);
        panel.add(removeButton);
        panel.add(editButton);
        panel.add(exportButton);

        frame.add(panel, BorderLayout.SOUTH);

        // Action listeners for buttons
        addButton.addActionListener(e -> addItem());
        removeButton.addActionListener(e -> removeItem());
        editButton.addActionListener(e -> editItem());
        exportButton.addActionListener(e -> exportInventoryToFile());

        // Modernizing the GUI
        UIManager.put("Button.background", new Color(34, 139, 34));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 12));
        UIManager.put("Table.background", Color.WHITE);
        UIManager.put("Table.gridColor", Color.LIGHT_GRAY);
        UIManager.put("TableHeader.background", new Color(240, 240, 240));
        UIManager.put("TableHeader.foreground", Color.BLACK);

        frame.setVisible(true);
    }

    // Method to add an item to the inventory
    private void addItem() {
        String name = nameField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());

        if (inventory.containsKey(name)) {
            Item item = inventory.get(name);
            item.quantity += quantity; // Update quantity if item already exists
            item.price = price; // Update price if changed
            showMessage("Updated " + name + " in the inventory.");
        } else {
            inventory.put(name, new Item(name, quantity, price));
            showMessage("Added " + name + " to the inventory.");
        }
        clearFields();
        refreshTable();
    }

    // Method to remove an item from the inventory
    private void removeItem() {
        String name = nameField.getText();
        if (inventory.containsKey(name)) {
            inventory.remove(name);
            showMessage("Removed " + name + " from the inventory.");
        } else {
            showMessage("Item not found in inventory.");
        }
        clearFields();
        refreshTable();
    }

    // Method to edit an existing item in the inventory
    private void editItem() {
        String name = nameField.getText();
        if (inventory.containsKey(name)) {
            int newQuantity = Integer.parseInt(quantityField.getText());
            double newPrice = Double.parseDouble(priceField.getText());
            Item item = inventory.get(name);
            item.quantity = newQuantity;
            item.price = newPrice;
            showMessage("Updated " + name + " with new quantity and price.");
        } else {
            showMessage("Item not found in inventory.");
        }
        clearFields();
        refreshTable();
    }

    // Method to refresh the table display
    private void refreshTable() {
        tableModel.setRowCount(0); // Clear existing rows
        for (Item item : inventory.values()) {
            tableModel.addRow(item.toTableRow());
        }
    }

    // Method to export inventory to a file
    private void exportInventoryToFile() {
        String filename = JOptionPane.showInputDialog(frame, "Enter filename to export to:");
        try (FileWriter writer = new FileWriter(filename)) {
            for (Item item : inventory.values()) {
                writer.write(item.toTableRow()[0] + "," + item.toTableRow()[1] + "," + item.toTableRow()[2] + "\n");
            }
            showMessage("Inventory exported to " + filename);
        } catch (IOException e) {
            showMessage("An error occurred while exporting the inventory.");
            e.printStackTrace();
        }
    }

    // Utility method to show message in dialog
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    // Utility method to clear input fields
    private void clearFields() {
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryManagementSystemGUI::new);
    }
}
