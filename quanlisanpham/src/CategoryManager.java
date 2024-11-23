import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    private List<Category> categories;

    public CategoryManager() {
        categories = new ArrayList<>();
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void listCategories() {
        if (categories.isEmpty()) {
            System.out.println("Danh sách danh mục đang trống.");
            return;
        }
        for (Category category : categories) {
            category.displayInfo();
        }
    }
    public void removeCategory(String id) {
        boolean removed = categories.removeIf(category -> category.getId().equals(id));
        if (removed) {
            System.out.println("Danh mục đã được xóa.");
        } else {
            System.out.println("Không tìm thấy danh mục với ID: " + id);
        }
    }
    public void updateCategory(String id, String newName) {
        for (Category category : categories) {
            if (category.getId().equals(id)) {
                category.setName(newName);
                System.out.println("Danh mục đã được cập nhật.");
                return; // Dừng lại sau khi cập nhật
            }
        }
        System.out.println("Không tìm thấy danh mục với ID: " + id);
    }
}