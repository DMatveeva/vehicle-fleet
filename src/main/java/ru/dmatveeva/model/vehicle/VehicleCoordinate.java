package ru.dmatveeva.model.vehicle;


import com.vividsolutions.jts.geom.Point;
import net.bytebuddy.asm.Advice;
import ru.dmatveeva.model.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;


@NamedQueries({
        @NamedQuery(name = VehicleCoordinate.BY_VEHICLE_AND_PERIOD,
                query = "SELECT c FROM VehicleCoordinate c WHERE c.vehicle=?1 and c.visited between ?2 and ?3")
})

@Entity
@Table(name = "vehicle_coordinates")
public class VehicleCoordinate extends AbstractBaseEntity implements Serializable {

    public static final String BY_TRACK = "VehicleCoordinate.getByTrack";
    public static final String BY_VEHICLE_AND_PERIOD = "VehicleCoordinate.getByVehicleAndPeriod";

    private static int SRID = 4326;

    @Column(name = "track_id")
    private Integer trackId;

    @ManyToOne
    private Vehicle vehicle;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "visited")
    private LocalDateTime visited;

    /*@Column(columnDefinition = "geometry", name="position")
    private Point position;*/

    @Column(columnDefinition = "geometry(Point,4326)", name = "position")
    private Point position;

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public LocalDateTime getVisited() {
        return visited;
    }

    public void setVisited(LocalDateTime visited) {
        this.visited = visited;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
