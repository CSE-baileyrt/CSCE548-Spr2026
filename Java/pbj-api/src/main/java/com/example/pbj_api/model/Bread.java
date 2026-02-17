package com.example.pbj_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bread")
public class Bread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String brand;
    
    @Column(name = "wheatLevel")
    private int wheatLevel;
    private double price;

    public Bread() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public int getWheatLevel() { return wheatLevel; }
    public void setWheatLevel(int wheatLevel) { this.wheatLevel = wheatLevel; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
