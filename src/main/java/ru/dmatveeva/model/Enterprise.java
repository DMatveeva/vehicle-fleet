package ru.dmatveeva.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = Enterprise.ALL, query = "SELECT e FROM Enterprise e"),
})
@Entity
@Table(name = "enterprises")
public class Enterprise extends AbstractBaseEntity{

    public static final String ALL = "Enterprise.getAll";

    public Enterprise(Integer id, String name, String city, List<Driver> drivers, List<Vehicle> vehicles) {
        super(id);
        this.name = name;
        this.city = city;
        this.drivers = drivers;
        this.vehicles = vehicles;
    }

    public Enterprise() {
    }
    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "enterprise")
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private List<Driver> drivers;

    @OneToMany(mappedBy = "enterprise")
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private List<Vehicle> vehicles;

    @ManyToMany
    @JsonIgnore
    private List<Manager> manager;

    @Column(name = "time_zone")
    private String localTimeZone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Manager> getManager() {
        return manager;
    }

    public void setManager(List<Manager> manager) {
        this.manager = manager;
    }

    public String getLocalTimeZone() {
        return localTimeZone;
    }

    public void setLocalTimeZone(String localTimeZone) {
        this.localTimeZone = localTimeZone;
    }
}
