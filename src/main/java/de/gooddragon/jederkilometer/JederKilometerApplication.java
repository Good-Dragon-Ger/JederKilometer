package de.gooddragon.jederkilometer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JederKilometerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JederKilometerApplication.class, args);
    }

}
