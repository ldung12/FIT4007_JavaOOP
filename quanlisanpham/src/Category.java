public class Category {
    private String id;
    private String name;

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;  // Phương thức này phải có
    }
    public void displayInfo() {
        System.out.println("ID: " + id + ", Name: " + name);
    }
}