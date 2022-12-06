package ru.dmatveeva.model;

import java.math.BigDecimal;

public class Vehicle extends AbstractBaseEntity{

    private String vin;
    private BigDecimal costUsd;

    private int mileage;

    private int productionYear;

    public Vehicle(Integer id, String vin, BigDecimal costUsd, int mileage, int productionYear) {
        super(id);
        this.vin = vin;
        this.costUsd = costUsd;
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
