package ru.dmatveeva.model;

import ru.dmatveeva.model.vehicle.Vehicle;

public class VehicleReport extends Report{

    public Vehicle vehicle;

    public VehicleReport() {
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


}
