package org.martpilot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "org.martpilot.repository")
@EntityScan(basePackages = "org.martpilot.entity")
@SpringBootApplication(scanBasePackages = {"org.martpilot"})
@EnableJpaAuditing
public class MartPilotApplication {

    public static void main(String[] args) {
        SpringApplication.run(MartPilotApplication.class, args);
    }

}
