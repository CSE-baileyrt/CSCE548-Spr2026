public class Bread {
    private int id;
    private String brand;
    private int wheatLevel;
    private double price;

    public Bread() {}

    public Bread(int id, String brand, int wheatLevel, double price) {
        this.id = id;
        this.brand = brand;
        this.wheatLevel = wheatLevel;
        this.price = price;
    }
    
    @Override
    public String toString() {
        return "Bread{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", wheatLevel=" + wheatLevel +
                ", price=" + price +
                '}';
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public int getWheatLevel() { return wheatLevel; }
    public void setWheatLevel(int wheatLevel) { this.wheatLevel = wheatLevel; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
