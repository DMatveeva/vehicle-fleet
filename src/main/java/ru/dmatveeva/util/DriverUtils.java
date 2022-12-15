package ru.dmatveeva.util;

import ru.dmatveeva.model.Driver;
import ru.dmatveeva.to.DriverTo;

import java.util.List;
import java.util.stream.Collectors;

public class DriverUtils {

    public static List<DriverTo> getDriverTos(List<Driver> drivers) {
        return drivers.stream()
                .map(DriverUtils::getDriverTo)
                .collect(Collectors.toList());
    }

    private static DriverTo getDriverTo(Driver driver) {
        return new DriverTo(driver.getId(),
                driver.getFirstName(),
                driver.getSecondName(),
                driver.getSalaryUSD(),
                driver.getDrivingExperienceYears(),
                driver.getEnterprise().getId(),
                driver.getVehicle().getId(),
                driver.isActive());
    }
}
