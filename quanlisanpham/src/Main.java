import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductManager productManager = new ProductManager();
        CategoryManager categoryManager = new CategoryManager();
        int mainChoice;
        InventoryManager inventoryManager = new InventoryManager();
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("1. Quản lý sản phẩm");
            System.out.println("2. Quản lý danh mục sản phẩm");
            System.out.println("3. Quản lý kho");
            System.out.println("4. Báo cáo");
            System.out.println("5. Bán hàng");
            System.out.println("0. Thoát");
            System.out.print("Chọn tùy chọn: ");
            mainChoice = scanner.nextInt();
            scanner.nextLine(); // Đọc ký tự newline

            switch (mainChoice) {
                case 1:
                    manageProducts(productManager, scanner);
                    break;
                case 2:
                    manageCategories(categoryManager, scanner);
                    break;
                case 3:
                    manageInventory(inventoryManager, scanner);
                    break;
                case 4:
                    manageReports(productManager, inventoryManager, scanner); // Thêm vào chức năng báo cáo
                    break;
                case 5:
                    manageSales(productManager, scanner);
                    break;
                case 0:
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    System.out.println("Tùy chọn không hợp lệ.");
            }

        } while (mainChoice != 0);

        scanner.close();
    }

    private static void manageProducts(ProductManager productManager, Scanner scanner) {
        // Manage products implementation with category and origin
        int choice;
        do {
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Xóa sản phẩm");
            System.out.println("3. Cập nhật sản phẩm");
            System.out.println("4. Liệt kê sản phẩm");
            System.out.println("5. Tìm kiếm sản phẩm");
            System.out.println("0. Quay lại");
            System.out.print("Chọn tùy chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Đọc ký tự newline

            switch (choice) {
                case 1:
                    System.out.print("Nhập ID sản phẩm: ");
                    String id = scanner.nextLine();
                    System.out.print("Nhập tên sản phẩm: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhập giá sản phẩm: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine(); // Đọc ký tự newline
                    System.out.print("Nhập danh mục sản phẩm: ");
                    String category = scanner.nextLine();
                    System.out.print("Nhập nguồn gốc sản phẩm: ");
                    String origin = scanner.nextLine();
                    productManager.addProduct(new Product(id, name, price, category, origin));
                    System.out.println("Sản phẩm đã được thêm thành công.");
                    break;
                case 2:
                    System.out.print("Nhập ID sản phẩm cần xóa: ");
                    String removeId = scanner.nextLine();
                    productManager.removeProduct(removeId);
                    System.out.println("Sản phẩm đã được xóa (nếu tồn tại).");
                    break;


                case 3:
                    System.out.print("Nhập ID sản phẩm cần cập nhật: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Nhập tên mới: ");
                    String newName = scanner.nextLine();
                    double newPrice = scanner.nextDouble();
                    scanner.nextLine(); // Đọc ký tự newline
                    System.out.print("Nhập danh mục mới: ");
                    String newCategory = scanner.nextLine();
                    System.out.print("Nhập nguồn gốc mới: ");
                    String newOrigin = scanner.nextLine();
                    productManager.updateProduct(updateId, newName, newPrice, newCategory, newOrigin);
                    System.out.println("Sản phẩm đã được cập nhật (nếu tồn tại).");
                    break;

                case 4:
                    System.out.println("Danh sách sản phẩm:");
                    productManager.listProducts();
                    System.out.println("Liệt kê sản phẩm thành công.");
                    break;

                case 5:
                    System.out.print("Nhập tên sản phẩm cần tìm: ");
                    String searchName = scanner.nextLine();
                    productManager.searchProductByName(searchName).forEach(Product::displayInfo);
                    System.out.println("Tìm kiếm sản phẩm thành công.");

                case 0:
                    break;

                default:
                    System.out.println("Tùy chọn không hợp lệ.");
                    break;
            }
        } while (choice != 0);
    }

    private static void manageCategories(CategoryManager categoryManager, Scanner scanner) {
        int choice;
        do {
            System.out.println("1. Thêm danh mục");
            System.out.println("2. Sửa danh mục");
            System.out.println("3. Xóa danh mục");
            System.out.println("4. Liệt kê danh mục");
            System.out.println("0. Quay lại");
            System.out.print("Chọn tùy chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Đọc ký tự newline

            switch (choice) {
                case 1:
                    System.out.print("Nhập ID danh mục: ");
                    String categoryId = scanner.nextLine();
                    System.out.print("Nhập tên danh mục: ");
                    String categoryName = scanner.nextLine();
                    categoryManager.addCategory(new Category(categoryId, categoryName));
                    break;

                case 2: // Sửa danh mục
                    System.out.print("Nhập ID danh mục cần sửa: ");
                    String updateCategoryId = scanner.nextLine();
                    System.out.print("Nhập tên mới: ");
                    String updateCategoryName = scanner.nextLine();
                    categoryManager.updateCategory(updateCategoryId, updateCategoryName);
                    System.out.println("Danh mục đã được cập nhật (nếu tồn tại).");

                case 3: // Xóa danh mục
                    System.out.print("Nhập ID danh mục cần xóa: ");
                    String removeCategoryId = scanner.nextLine();
                    categoryManager.removeCategory(removeCategoryId);
                    System.out.println("Danh mục đã được xóa (nếu tồn tại).");
                    break;

                case 4:
                    System.out.println("Danh sách danh mục:");
                    categoryManager.listCategories();
                    System.out.println("Liệt kê danh mục thành công.");
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Tùy chọn không hợp lệ.");
            }

        } while (choice != 0);
    }

    private static void manageInventory(InventoryManager inventoryManager, Scanner scanner) {
        int choice;
        do {
            System.out.println("1. Thêm kho");
            System.out.println("2. Cập nhật kho");
            System.out.println("3. Liệt kê kho");
            System.out.println("0. Quay lại");
            System.out.print("Chọn tùy chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Nhập ID sản phẩm: ");
                    String productId = scanner.nextLine();
                    System.out.print("Nhập số lượng: ");
                    int inventoryQuantity = scanner.nextInt();
                    inventoryManager.addInventory(productId, inventoryQuantity);
                    System.out.println("Kho đã được thêm thành công.");
                    break;

                case 2:
                    System.out.print("Nhập ID sản phẩm cần cập nhật: ");
                    String updateProductId = scanner.nextLine();
                    System.out.print("Nhập số lượng mới: ");
                    int newInventoryQuantity = scanner.nextInt();
                    scanner.nextLine(); // Handle the newline character
                    inventoryManager.updateInventory(updateProductId, newInventoryQuantity);
                    System.out.println("Kho đã được cập nhật (nếu tồn tại).");
                    break;
                case 3:
                    System.out.println("Danh sách kho:");
                    inventoryManager.listInventory();
                    System.out.println("Liệt kê kho thành công.");
                    break;
                case 0:
                    break;

                default:
                    System.out.println("Tùy chọn không hợp lệ.");
            }

        } while (choice != 0);
    }

    private static void manageSales(ProductManager productManager, Scanner scanner) {
        int choice;
        do {
            System.out.println("1. Bán sản phẩm");
            System.out.println("0. Quay lại");
            System.out.print("Chọn tùy chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    sellProduct(productManager, scanner);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Tùy chọn không hợp lệ.");
            }
        } while (choice != 0);
    }

    private static void sellProduct(ProductManager productManager, Scanner scanner) {
        System.out.print("Nhập ID sản phẩm cần bán: ");
        String saleProductId = scanner.nextLine();
        System.out.print("Nhập số lượng: ");
        int saleQuantity = scanner.nextInt();
        scanner.nextLine(); // Handle the newline character
        productManager.recordSale(saleProductId, saleQuantity);
        System.out.println("Bán sản phẩm thành công.");
    }

    private static void manageReports(ProductManager productManager, InventoryManager inventoryManager, Scanner scanner) {
        int choice;
        do {
            System.out.println("1. Báo cáo doanh thu");
            System.out.println("2. Báo cáo tồn kho");
            System.out.println("0. Quay lại");
            System.out.print("Chọn tùy chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Đọc ký tự newline

            switch (choice) {
                case 1:
                    productManager.displaySalesReport();
                    System.out.println("Hiển thị báo cáo doanh thu thành công.");
                    break;

                case 2:
                    inventoryManager.displayInventoryReport();
                    System.out.println("Hiển thị báo cáo tồn kho thành công.");
                    break;

                default:
                    System.out.println("Tùy chọn không hợp lệ.");
            }

        } while (choice != 0);
    }
}