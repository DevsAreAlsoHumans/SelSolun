package fr.doranco.solsolunback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SolSolunBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolSolunBackApplication.class, args);
    }

}
