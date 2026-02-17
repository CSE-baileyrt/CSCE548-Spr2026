package com.example.pbj_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Pbj_Sandwich")
public class PbjSandwich {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String customer;

    @ManyToOne
    @JoinColumn(name = "bread1_id")
    private Bread bread1;

    @ManyToOne
    @JoinColumn(name = "bread2_id")
    private Bread bread2;

    @ManyToOne
    @JoinColumn(name = "pb_id")
    private PeanutButter pb;

    @ManyToOne
    @JoinColumn(name = "jelly_id")
    private Jelly jelly;

    @Column(name = "totalCost")
    private double totalCost;

    public PbjSandwich() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }

    public Bread getBread1() { return bread1; }
    public void setBread1(Bread bread1) { this.bread1 = bread1; }

    public Bread getBread2() { return bread2; }
    public void setBread2(Bread bread2) { this.bread2 = bread2; }

    public PeanutButter getPb() { return pb; }
    public void setPb(PeanutButter pb) { this.pb = pb; }

    public Jelly getJelly() { return jelly; }
    public void setJelly(Jelly jelly) { this.jelly = jelly; }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
}
