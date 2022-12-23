package ru.dmatveeva.service;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;
import ru.dmatveeva.model.Driver;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.Vehicle;
import ru.dmatveeva.model.VehicleModel;
import ru.dmatveeva.repository.DriverRepository;
import ru.dmatveeva.repository.EnterpriseRepository;
import ru.dmatveeva.repository.VehicleModelRepository;
import ru.dmatveeva.repository.VehicleRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

@Service
public class VehicleGeneratorService {

    private final VehicleRepository vehicleRepository;
    private final VehicleModelRepository vehicleModelRepository;
    private final DriverRepository driverRepository;

    private final EnterpriseRepository enterpriseRepository;

    public VehicleGeneratorService(VehicleRepository vehicleRepository, VehicleModelRepository vehicleModelRepository, DriverRepository driverRepository, EnterpriseRepository enterpriseRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleModelRepository = vehicleModelRepository;
        this.driverRepository = driverRepository;
        this.enterpriseRepository = enterpriseRepository;
    }

    private final int VIN_LENGTH = 17;
    private final int MAX_MILEAGE = 500_000;
    private final int OLDEST_YEAR_OF_PRODUCTION = 1980;
    private final int NEWEST_YEAR_OF_PRODUCTION = 2020;


    List<VehicleModel> vehicleModels;

    List<Driver> drivers;

    List<String> colors = List.of(
            "Black",
            "Blue",
            "White",
            "Green",
            "Purple",
            "Silver",
            "Dark blue",
            "Red",
            "Orange",
            "Pink",
            "Yellow",
            "Brown"
    );

    private void populateVehicleModels() {
        vehicleModels = vehicleModelRepository.getAll();
    }

    private void populateDrivers(Enterprise enterprise) {
        drivers = driverRepository.getByEnterprise(enterprise);
    }

    private void clearDrivers() {
        drivers.clear();
    }


    public void generateVehiclesForEnterprises(List<Integer> enterpriseIds, int numVehicles) {
        populateVehicleModels();

        List<Enterprise> enterprises = enterpriseIds.stream()
                .map(enterpriseRepository::get).toList();

        List<Vehicle> vehicles = enterprises.stream()
                .map(e -> generateVehiclesForEnterprise(e, numVehicles))
                .flatMap(Collection::stream).toList();

        vehicles.forEach(vehicleRepository::save);
    }

    private List<Vehicle> generateVehiclesForEnterprise(Enterprise enterprise, int numVehicles) {
        populateDrivers(enterprise);

        List<Vehicle> vehicles = new ArrayList<>();
        for (int i = 0; i < numVehicles; i++) {
            vehicles.add(generateVehicle(enterprise, numVehicles));
        }

        clearDrivers();
        return vehicles;
    }

    private Vehicle generateVehicle(Enterprise enterprise, int numVehicles) {
        boolean isActive = getIsDriverActive();

        VehicleModel vehicleModel = getVehicleModel();
        String vin = generateVin();
        BigDecimal costUsd = generateCost();
        String color = getColor();
        int mileage = getMileage();
        int productionYear = getProductionYear();
        List<Driver> drivers = getDrivers(numVehicles, isActive);
        Vehicle vehicle = new Vehicle(vehicleModel, vin, costUsd, color, mileage, productionYear, drivers, enterprise);
        drivers.forEach(d -> d.setVehicle(vehicle));

        return vehicle;
    }

    private List<Driver> getDrivers(int numVehicles, boolean isActive) {
        List<Driver> driversForVehicle = new ArrayList<>();
        if (drivers.size() > 0) {


            int numDriversForVehicle = new Random().nextInt(0, drivers.size());
            for (int i = 0; i < numDriversForVehicle; i++) {
                int randomIndex = new Random().nextInt(0, drivers.size() );
                Driver driver = drivers.get(randomIndex);
                if (isActive) {
                    driver.setActive(true);
                    isActive = false;
                }
                drivers.remove(driver);
            }
        }
        return driversForVehicle;
    }

    private boolean getIsDriverActive() {
        int randomIndex = new Random().nextInt(1, 11);
        return randomIndex == 10;
    }

    private VehicleModel getVehicleModel() {
        int randomIndex = new Random().nextInt(0, vehicleModels.size());
        return vehicleModels.get(randomIndex);
    }

    private String generateVin() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(LETTERS, DIGITS)
                .build();
        return generator.generate(VIN_LENGTH);
    }

    private BigDecimal generateCost() {
        return new BigDecimal(BigInteger.valueOf(new Random().nextInt(1000, 100001)), 2);
    }

    private String getColor() {
        int randomIndex = new Random().nextInt(0, colors.size());
        return colors.get(randomIndex);
    }

    private int getMileage() {
        return new Random().nextInt(MAX_MILEAGE);
    }

    private int getProductionYear() {
        return new Random().nextInt(OLDEST_YEAR_OF_PRODUCTION, NEWEST_YEAR_OF_PRODUCTION);
    }


}
