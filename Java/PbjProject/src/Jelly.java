public class Jelly {
    private int id;
    private String brand;
    private String flavor;
    private double price;

    public Jelly() {}

    public Jelly(int id, String brand, String flavor, double price) {
        this.id = id;
        this.brand = brand;
        this.flavor = flavor;
        this.price = price;
    }
    
    @Override
    public String toString() {
        return "Jelly{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", flavor='" + flavor + '\'' +
                ", price=" + price +
                '}';
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getFlavor() { return flavor; }
    public void setFlavor(String flavor) { this.flavor = flavor; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
