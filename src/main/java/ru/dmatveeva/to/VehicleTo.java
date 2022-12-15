package ru.dmatveeva.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.dmatveeva.model.Driver;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.VehicleModel;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class VehicleTo {

    @JsonProperty
    private Integer id;

    private Integer model_id;

    @Column(name = "vin", nullable = false, unique = true)
    private String vin;

    @Column(name = "cost_usd", nullable = false)
    private BigDecimal costUsd;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "mileage", nullable = false)
    private int mileage;

    @Column(name = "production_year", nullable = false)
    private int productionYear;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle")
    @JsonIgnore
    private List<Driver> drivers;

    @ManyToOne
    private Enterprise enterprise;
}
