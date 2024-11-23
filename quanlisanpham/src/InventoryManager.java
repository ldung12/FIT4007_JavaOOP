import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private Map<String, Inventory> inventoryMap;

    public InventoryManager() {
        inventoryMap = new HashMap<>();
    }

    public void addInventory(String productId, int quantity) {
        inventoryMap.put(productId, new Inventory(productId, quantity));
    }

    public void updateInventory(String productId, int quantity) {
        if (inventoryMap.containsKey(productId)) {
            inventoryMap.get(productId).setQuantity(quantity);
        }
    }

    public void listInventory() {
        if (inventoryMap.isEmpty()) {
            System.out.println("Kho hàng đang trống.");
            return;
        }
        for (Inventory inventory : inventoryMap.values()) {
            System.out.println("Product ID: " + inventory.getProductId() + ", Quantity: " + inventory.getQuantity());
        }
    }
        public void displayInventoryReport() {
            System.out.println("Báo cáo tồn kho:");
            listInventory(); // Có thể sử dụng lại phương thức listInventory
        }
    }

