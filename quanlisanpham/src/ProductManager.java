import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductManager {
    private List<Product> products;
    private Map<String, Double> sales; // Lưu trữ doanh thu theo ID sản phẩm

    public ProductManager() {
        products = new ArrayList<>();
        sales = new HashMap<>(); // Khởi tạo bản đồ doanh thu
    }

    public void addProduct(Product product) {
        products.add(product);
        sales.put(product.getId(), 0.0); // Khởi tạo doanh thu cho sản phẩm
    }
    public void removeProduct(String id) {
        products.removeIf(product -> product.getId().equals(id));
    }
    public void updateProduct(String id, String newName, double newPrice, String newCategory, String newOrigin) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                product.setName(newName);
                product.setPrice(newPrice);
                product.setCategory(newCategory);
                product.setOrigin(newOrigin);
                break; // Dừng lại sau khi cập nhật

            }
        }
    }

    public void recordSale(String productId, int quantity) {
        if (sales.containsKey(productId)) {
            Product product = findProductById(productId);
            if (product != null) {
                double revenue = product.getPrice() * quantity; // Tính doanh thu
                sales.put(productId, sales.get(productId) + revenue); // Cập nhật doanh thu
                System.out.println("Đã ghi nhận doanh thu cho sản phẩm ID: " + productId + ", Doanh thu: " + revenue);
            } else {
                System.out.println("Sản phẩm không tồn tại.");
            }
        } else {
            System.out.println("Sản phẩm không có doanh thu ghi nhận.");
        }
    }

    public double getTotalRevenue() {
        return sales.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    public void displaySalesReport() {
        System.out.println("Báo cáo doanh thu:");
        for (Product product : products) {
            double revenue = sales.get(product.getId());
            System.out.println("Sản phẩm ID: " + product.getId() + ", Doanh thu: " + revenue);
        }
        System.out.println("Tổng doanh thu: " + getTotalRevenue());
    }

    public Product findProductById(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }
        public void listProducts() {
            if (products.isEmpty()) {
                System.out.println("Không có sản phẩm nào.");
                return;
            }
            for (Product product : products) {
                product.displayInfo();
            }
        }

        public List<Product> searchProductByName(String name) {
            return products.stream()
                    .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }
        }
