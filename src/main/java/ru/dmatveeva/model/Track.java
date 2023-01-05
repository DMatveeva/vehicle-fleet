package ru.dmatveeva.model;

import ru.dmatveeva.model.vehicle.Vehicle;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name = "tracks")
public class Track extends AbstractBaseEntity{

    public Track() {
    }

    @OneToOne
    private Vehicle vehicle;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
