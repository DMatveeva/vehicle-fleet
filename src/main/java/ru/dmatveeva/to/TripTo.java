package ru.dmatveeva.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class TripTo {

    private Integer vehicle_id;

    @JsonProperty
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private ZonedDateTime started;

    @JsonProperty
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private ZonedDateTime finished;

    @JsonProperty
    private String zone;

    @JsonProperty
    private VehicleCoordinateWithAddressTo start;

    @JsonProperty
    private VehicleCoordinateWithAddressTo finish;

    public TripTo(Integer vehicle_id, ZonedDateTime started, ZonedDateTime finished, String zone, VehicleCoordinateWithAddressTo start, VehicleCoordinateWithAddressTo finish) {
        this.vehicle_id = vehicle_id;
        this.started = started;
        this.finished = finished;
        this.zone = zone;
        this.start = start;
        this.finish = finish;
    }

    public TripTo() {
    }

    public Integer getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(Integer vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public ZonedDateTime getStarted() {
        return started;
    }

    public void setStarted(ZonedDateTime started) {
        this.started = started;
    }

    public ZonedDateTime getFinished() {
        return finished;
    }

    public void setFinished(ZonedDateTime finished) {
        this.finished = finished;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public VehicleCoordinateWithAddressTo getStart() {
        return start;
    }

    public void setStart(VehicleCoordinateWithAddressTo start) {
        this.start = start;
    }

    public VehicleCoordinateWithAddressTo getFinish() {
        return finish;
    }

    public void setFinish(VehicleCoordinateWithAddressTo finish) {
        this.finish = finish;
    }
}
