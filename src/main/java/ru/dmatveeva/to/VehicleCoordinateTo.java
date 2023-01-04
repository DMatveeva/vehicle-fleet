package ru.dmatveeva.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.dmatveeva.model.Track;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

public class VehicleCoordinateTo {

    private Integer id;
    private Integer track_id;

    @JsonProperty
    private Double lat;

    @JsonProperty
    private Double lon;

    @JsonProperty
    private Date visited;

    public VehicleCoordinateTo(Integer id, Integer track_id, Double lat, Double lon, Date visited) {
        this.id = id;
        this.track_id = track_id;
        this.lat = lat;
        this.lon = lon;
        this.visited = visited;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrack_id() {
        return track_id;
    }

    public void setTrack_id(Integer track_id) {
        this.track_id = track_id;
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
}
