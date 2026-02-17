public class PeanutButter {
    private int id;
    private String brand;
    private boolean isCrunchy;
    private double price;

    public PeanutButter() {}

    public PeanutButter(int id, String brand, boolean isCrunchy, double price) {
        this.id = id;
        this.brand = brand;
        this.isCrunchy = isCrunchy;
        this.price = price;
    }
    
    @Override
    public String toString() {
        return "PeanutButter{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", isCrunchy=" + isCrunchy +
                ", price=" + price +
                '}';
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public boolean isCrunchy() { return isCrunchy; }
    public void setCrunchy(boolean crunchy) { isCrunchy = crunchy; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
