package ru.dmatveeva.model.vehicle;

import ru.dmatveeva.model.AbstractBaseEntity;
import ru.dmatveeva.model.Brand;
import ru.dmatveeva.model.EngineType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
        @NamedQuery(name = VehicleModel.ALL, query = "SELECT vm FROM VehicleModel vm")
})
@Entity
@Table(name = "vehicle_models")
public class VehicleModel extends AbstractBaseEntity {

    public static final String ALL = "VehicleModel.getAll";

    @Column(name = "brand", nullable = false)
    @Enumerated(EnumType.STRING)
    private Brand brand;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "vehicle_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(name = "num_seats", nullable = false)
    private Integer numSeats;

    @Column(name = "engine_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EngineType engineType;

    @Column(name = "load_capacity", nullable = false)
    private Integer loadCapacity;

    public VehicleModel() {
    }

    public VehicleModel(Integer id, Brand brand, String name, VehicleType vehicleType, Integer numSeats, EngineType engineType, Integer loadCapacity) {
        super(id);
        this.brand = brand;
        this.name = name;
        this.vehicleType = vehicleType;
        this.numSeats = numSeats;
        this.engineType = engineType;
        this.loadCapacity = loadCapacity;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(Integer numSeats) {
        this.numSeats = numSeats;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }

    public Integer getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(Integer loadCapacity) {
        this.loadCapacity = loadCapacity;
    }
}
