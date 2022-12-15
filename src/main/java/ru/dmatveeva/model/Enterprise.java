package ru.dmatveeva.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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

}
