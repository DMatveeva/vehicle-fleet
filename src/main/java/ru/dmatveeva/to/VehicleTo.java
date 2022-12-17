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

    private String vin;

    private BigDecimal costUsd;

    private String color;

    private int mileage;

    private int productionYear;

    private Enterprise enterprise;
}
