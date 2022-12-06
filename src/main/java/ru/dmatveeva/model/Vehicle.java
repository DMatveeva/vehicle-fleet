package ru.dmatveeva.model;

import javax.persistence.Column;
import java.math.BigDecimal;

public class Vehicle extends AbstractBaseEntity{

    @Column(name = "vin", nullable = false, unique = true)
    private String vin;

    @Column(name = "cost_usd", nullable = false)
    private BigDecimal costUsd;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "mileage", nullable = false)
    private int mileage;

    @Column(name = "production_year", nullable = false)
    private int productionYear;

    public Vehicle(Integer id, String vin, BigDecimal costUsd, String color, int mileage, int productionYear) {
        super(id);
        this.vin = vin;
        this.costUsd = costUsd;
        this.color = color;
        this.mileage = mileage;
        this.productionYear = productionYear;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public BigDecimal getCostUsd() {
        return costUsd;
    }

    public void setCostUsd(BigDecimal costUsd) {
        this.costUsd = costUsd;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }
}
