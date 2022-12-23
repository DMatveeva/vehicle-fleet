package ru.dmatveeva.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.math.BigDecimal;

@NamedQueries({
        @NamedQuery(name = Driver.ALL, query = "SELECT d FROM Driver d"),
        @NamedQuery(name = Driver.BY_ENTERPRISE_ID, query = "SELECT d FROM Driver d WHERE d.enterprise=?1")
})

@Entity
@Table(name = "drivers")
public class Driver extends AbstractBaseEntity{

    public static final String ALL = "Driver.getAll";
    public static final String BY_ENTERPRISE_ID = "Driver.getByEnterpriseId";



    public Driver() {
    }

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "salary_usd")
    private BigDecimal salaryUSD;

    @Column(name = "experience")
    private int drivingExperienceYears;

    @ManyToOne
    private Enterprise enterprise;

    @ManyToOne
    private Vehicle vehicle;

    @Column(name = "is_active")
    private boolean isActive;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public BigDecimal getSalaryUSD() {
        return salaryUSD;
    }

    public void setSalaryUSD(BigDecimal salaryUSD) {
        this.salaryUSD = salaryUSD;
    }

    public int getDrivingExperienceYears() {
        return drivingExperienceYears;
    }

    public void setDrivingExperienceYears(int drivingExperienceYears) {
        this.drivingExperienceYears = drivingExperienceYears;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
