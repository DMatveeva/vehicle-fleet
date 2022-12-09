package ru.dmatveeva.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NamedQueries({
        @NamedQuery(name = Vehicle.ALL, query = "SELECT v FROM Vehicle v"),
        @NamedQuery(name = Vehicle.DELETE, query = "DELETE FROM Vehicle v WHERE v.id=:id"),

})

@Entity
@Table(name = "vehicles")
public class Vehicle extends AbstractBaseEntity{

    public static final String ALL = "Vehicle.getAll";
    public static final String DELETE = "Vehicle.delete";

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private VehicleModel vehicleModel;

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

    public Vehicle(Integer id, VehicleModel vehicleModel, String vin, BigDecimal costUsd, String color, int mileage, int productionYear) {
        super(id);
        this.vehicleModel = vehicleModel;
        this.vin = vin;
        this.costUsd = costUsd;
        this.color = color;
        this.mileage = mileage;
        this.productionYear = productionYear;
    }

    public Vehicle(VehicleModel vehicleModel, String vin, BigDecimal costUsd, String color, int mileage, int productionYear) {
        this.vehicleModel = vehicleModel;
        this.vin = vin;
        this.costUsd = costUsd;
        this.color = color;
        this.mileage = mileage;
        this.productionYear = productionYear;
    }

    public Vehicle() {
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

    public VehicleModel getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(VehicleModel vehicleModel) {
        this.vehicleModel = vehicleModel;
    }
}
