package ru.dmatveeva;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.dmatveeva.model.Vehicle;
import ru.dmatveeva.web.VehicleController;

import java.math.BigDecimal;
import java.util.Arrays;

public class SpringMain {

    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            VehicleController vehicleController = appCtx.getBean(VehicleController.class);
            vehicleController.create(new Vehicle(1,"4Y1SL65848Z411439", BigDecimal.TEN, "blue", 60000, 2003));
        }
    }
}
