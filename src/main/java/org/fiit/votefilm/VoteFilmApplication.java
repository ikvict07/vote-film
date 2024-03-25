package org.fiit.votefilm;

import org.fiit.votefilm.service.DatabaseInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VoteFilmApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoteFilmApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(DatabaseInitializer databaseInitializer) {
        return args -> databaseInitializer.init();
    }
}
