package ru.dmatveeva.model;


import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@NamedQuery(name = VehicleCoordinate.BY_TRACK, query = "SELECT c FROM VehicleCoordinate c WHERE c.track=?1")

@Entity
@Table(name = "vehicle_coordinates")
public class VehicleCoordinate extends AbstractBaseEntity implements Serializable {

    public static final String BY_TRACK = "VehicleCoordinate.getByTrack";

    private static int SRID = 4326;
    //GeometryFactory factory = new GeometryFactory(new PrecisionModel(), SRID);

    @OneToOne
    private Track track;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "visited")
    private Date visited;

    /*@Column(columnDefinition = "geometry", name="position")
    private Point position;*/

    @Column(columnDefinition = "geometry(Point,4326)", name = "position")
    private Point position;


    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
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

    public Date getVisited() {
        return visited;
    }

    public void setVisited(Date visited) {
        this.visited = visited;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
