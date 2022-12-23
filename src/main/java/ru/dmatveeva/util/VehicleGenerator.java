package ru.dmatveeva.util;

import org.apache.commons.text.RandomStringGenerator;
import ru.dmatveeva.model.Driver;
import ru.dmatveeva.model.Enterprise;
import ru.dmatveeva.model.Vehicle;
import ru.dmatveeva.model.VehicleModel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

public class VehicleGenerator {
   /* private static final int VIN_LENGTH = 17;
    private static final int MAX_MILEAGE = 500_000;
    private static final int OLDEST_YEAR_OF_PRODUCTION = 1980;
    private static final int NEWEST_YEAR_OF_PRODUCTION = 2020;


    static List<VehicleModel> vehicleModels;

    static List<Driver> drivers;

    static List<String> colors = List.of(
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

   // private static populateModelsFromDB

    private static List<Vehicle> generateVehiclesForEnterprises(List<Enterprise> enterprises, int numVehicles) {
        return enterprises.stream()
                .map(e -> generateVehiclesForEnterprise(e, numVehicles))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private static List<Vehicle> generateVehiclesForEnterprise(Enterprise enterprise, int numVehicles) {
        List<Vehicle> vehicles = new ArrayList<>();
        Vehicle vehicle;
        for (int i = 0; i < numVehicles; i++) {
            vehicles.add(generateVehicle(enterprise));
        }
    }

    private static Vehicle generateVehicle(Enterprise enterprise){

        VehicleModel vehicleModel = getVehicleModel();
        String vin = generateVin();
        BigDecimal costUsd = generateCost();
        String color = getColor();
        int mileage = getMileage();
        int productionYear = getProductionYear();
        List<Driver> drivers = getDrivers();

        return new Vehicle(vehicleModel, vin, costUsd, color, mileage, productionYear, drivers, enterprise);
    }

    private static List<Driver> getDrivers() {
    }

    private static VehicleModel getVehicleModel() {
        int randomIndex = new Random().nextInt(0, vehicleModels.size() - 1);
        return vehicleModels.get(randomIndex);
    }

    private static String generateVin() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(LETTERS, DIGITS)
                .build();
        return generator.generate(VIN_LENGTH);
    }

    private static BigDecimal generateCost() {
        return new BigDecimal(BigInteger.valueOf(new Random().nextInt(1000, 100001)), 2);
    }

    private static String getColor() {
        int randomIndex = new Random().nextInt(0, colors.size() - 1);
        return colors.get(randomIndex);
    }

    private static int getMileage() {
        return new Random().nextInt(MAX_MILEAGE);
    }

    private static int getProductionYear() {
        return new Random().nextInt(OLDEST_YEAR_OF_PRODUCTION, NEWEST_YEAR_OF_PRODUCTION);
    }
*/


}
