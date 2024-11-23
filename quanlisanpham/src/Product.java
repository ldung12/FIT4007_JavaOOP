public class Product {
    private String id;
    private String name;
    private double price;
    private String category;
    private String origin;

    public Product(String id, String name, double price, String category, String origin) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
       this.origin = origin;
   }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }
    public String getOrigin() {
        return origin;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void displayInfo() {
        System.out.println("ID: " + id + ", Name: " + name + ", Price: " + price + ", Category: " + category + ", Origin: " + origin);
    }
}