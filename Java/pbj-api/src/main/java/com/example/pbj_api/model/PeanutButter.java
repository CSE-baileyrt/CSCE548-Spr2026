package com.example.pbj_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Peanut_Butter")
public class PeanutButter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String brand;
    
    @Column(name = "isCrunchy")
    private boolean isCrunchy;
    
    private double price;

    public PeanutButter() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public boolean isCrunchy() { return isCrunchy; }
    public void setCrunchy(boolean crunchy) { isCrunchy = crunchy; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
