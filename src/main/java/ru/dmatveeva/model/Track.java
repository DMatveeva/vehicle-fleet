package ru.dmatveeva.model;

import jdk.jfr.Enabled;
import ru.dmatveeva.model.vehicle.Vehicle;
import ru.dmatveeva.model.vehicle.VehicleCoordinate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@NamedQueries({
        @NamedQuery(name = Track.BY_VEHICLE_AND_PERIOD,
                query = "SELECT t FROM Track t WHERE t.vehicle=?1 and t.started between ?2 and ?3"),
        @NamedQuery(name = Track.BY_VEHICLE,
                query = "SELECT t FROM Track t WHERE t.vehicle=?1")
})

@Entity
@Table(name = "tracks")
public class Track extends AbstractBaseEntity implements Serializable {

    public static final String BY_VEHICLE_AND_PERIOD = "Track.getByVehicleAndPeriod";
    public static final String BY_VEHICLE = "Track.getByVehicle";

    @OneToOne
    private Vehicle vehicle;

    @Column(name = "started")
    private LocalDateTime started;

    @Column(name = "finished")
    private LocalDateTime finished;

    public Track() {
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getStarted() {
        return started;
    }

    public void setStarted(LocalDateTime started) {
        this.started = started;
    }

    public LocalDateTime getFinished() {
        return finished;
    }

    public void setFinished(LocalDateTime finished) {
        this.finished = finished;
    }
}
