package com.example.pbj_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "jelly")
public class Jelly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String brand;
    private String flavor;
    private double price;

    public Jelly() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getFlavor() { return flavor; }
    public void setFlavor(String flavor) { this.flavor = flavor; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
