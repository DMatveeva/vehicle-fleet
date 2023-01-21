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
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            VehicleController controller = appCtx.getBean(VehicleController.class);

            System.out.println(GeometryDecoder.decodeGeometry(
                    "ghrlHkr~s@?DICqELI?IAsCi@ICKAuA_@i@GSA?_@?M?eA?S?W?S@wB@k@?U@{H?MI@I@u@FkBPwBTcALI@?M[mGAMC?B?KiB?KAM[aFASg@sI?CmAt@y@f@?BVlELpC",
                    false));
            //controller.getAll().forEach(System.out::println);

        }
    }
}
