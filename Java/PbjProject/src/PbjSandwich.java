public class PbjSandwich {
    private int id;
    private String customer;
    private Bread bread1;
    private PeanutButter pb;
    private Jelly jelly;
    private Bread bread2;
    private double totalCost;

    public PbjSandwich() {}

    public PbjSandwich(int id, String customer, Bread bread1, PeanutButter pb,
                       Jelly jelly, Bread bread2, double totalCost) {
        this.id = id;
        this.customer = customer;
        this.bread1 = bread1;
        this.pb = pb;
        this.jelly = jelly;
        this.bread2 = bread2;
        this.totalCost = totalCost;
    }
    
    @Override
    public String toString() {
        return "PbjSandwich{" +
                "id=" + id +
                ", customer='" + customer + '\'' +
                ", bread1=" + (bread1 != null ? bread1.getBrand() + "(ID=" + bread1.getId() + ")" : "null") +
                ", pb=" + (pb != null ? pb.getBrand() + "(ID=" + pb.getId() + ")" : "null") +
                ", jelly=" + (jelly != null ? jelly.getBrand() + "(ID=" + jelly.getId() + ")" : "null") +
                ", bread2=" + (bread2 != null ? bread2.getBrand() + "(ID=" + bread2.getId() + ")" : "null") +
                ", totalCost=" + totalCost +
                '}';
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }

    public Bread getBread1() { return bread1; }
    public void setBread1(Bread bread1) { this.bread1 = bread1; }

    public PeanutButter getPb() { return pb; }
    public void setPb(PeanutButter pb) { this.pb = pb; }

    public Jelly getJelly() { return jelly; }
    public void setJelly(Jelly jelly) { this.jelly = jelly; }

    public Bread getBread2() { return bread2; }
    public void setBread2(Bread bread2) { this.bread2 = bread2; }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
}
