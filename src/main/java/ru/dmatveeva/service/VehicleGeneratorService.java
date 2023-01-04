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
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
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

    private final int MAX_NUM_DRIVERS_FOR_VEHICLE = 2;

    private static int driverNum = 0;

    private static Date MIN_DATE_OF_PURCHASE_2000 = new Date(946684800000L);
    private static Date MAX_DATE_OF_PURCHASE_2020 = new Date(1577836800000L);


    List<VehicleModel> vehicleModels;

 //   List<Driver> drivers;

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

        List<Vehicle> vehicles = new ArrayList<>();
        for (int i = 0; i < numVehicles; i++) {
            vehicles.add(generateVehicle(enterprise));
        }

        return vehicles;
    }

    private Vehicle generateVehicle(Enterprise enterprise) {
        boolean isActive = getIsDriverActive();
        Vehicle vehicle = new Vehicle();

        VehicleModel vehicleModel = getVehicleModel();
        String vin = generateVin();
        BigDecimal costUsd = generateCost();
        String color = getColor();
        int mileage = getMileage();
        int productionYear = getProductionYear();
        Date purchaseDate = generateDate(MIN_DATE_OF_PURCHASE_2000, MAX_DATE_OF_PURCHASE_2020);

        List<Driver> drivers = getDrivers(enterprise, vehicle, isActive);
        vehicle.setVehicleModel(vehicleModel);
        vehicle.setVin(vin);
        vehicle.setCostUsd(costUsd);
        vehicle.setColor(color);
        vehicle.setMileage(mileage);
        vehicle.setProductionYear(productionYear);
        vehicle.setDrivers(drivers);
        vehicle.setEnterprise(enterprise);
        vehicle.setPurchaseDate(purchaseDate);
        drivers.forEach(d -> d.setVehicle(vehicle));

        return vehicle;
    }



    public Date generateDate(Date startInclusive, Date endExclusive) {
        long startMillis = startInclusive.getTime();
        long endMillis = endExclusive.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);

        return new Date(randomMillisSinceEpoch);
    }



    private List<Driver> getDrivers(Enterprise enterprise, Vehicle vehicle, boolean isActive) {
        List<Driver> driversForVehicle = new ArrayList<>();
        int numDrivers = new Random().nextInt(0, MAX_NUM_DRIVERS_FOR_VEHICLE);
        for(int i = 0; i < numDrivers; i++) {
            Driver driver = getDriver(enterprise, vehicle, isActive);
            isActive = false;
            driversForVehicle.add(driver);
        }
        return driversForVehicle;
    }

    private Driver getDriver(Enterprise enterprise, Vehicle vehicle, boolean isActive) {
        String firstName = "FN" + String.valueOf(driverNum);
        String secondName = "SN" + String.valueOf(driverNum);
        driverNum++;
        BigDecimal salaryUSD = generateSalary();
        int drivingExperienceYears = getExperienceYears();
        return new Driver(firstName, secondName, salaryUSD, drivingExperienceYears, enterprise, vehicle, isActive);
    }

    private BigDecimal generateSalary() {
        return new BigDecimal(BigInteger.valueOf(new Random().nextInt(1000, 40001)), 2);
    }

    private int getExperienceYears() {
        return new Random().nextInt(2, 41);
    }

    /*
        this.isActive = isActive;
    }*/

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
