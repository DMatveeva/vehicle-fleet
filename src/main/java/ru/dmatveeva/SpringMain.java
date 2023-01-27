package ru.dmatveeva;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.dmatveeva.util.GeometryDecoder;
import ru.dmatveeva.web.VehicleController;

import java.util.Arrays;

public class SpringMain {

    public static void main(String[] args) {


        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext(
                "spring/spring-app.xml",
                "spring/db/spring-db.xml",
                "spring/spring-security.xml",
                "spring/spring-mvc.xml")) {


        }
    }
}
