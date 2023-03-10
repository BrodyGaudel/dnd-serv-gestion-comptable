package com.dndserv.brody.gestioncomptable;

import com.dndserv.brody.gestioncomptable.entities.Role;
import com.dndserv.brody.gestioncomptable.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;


@SpringBootApplication
@Slf4j
public class GestionComptableApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionComptableApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder getBCE() {
        return new BCryptPasswordEncoder();

    }

    @Bean
    CommandLineRunner start(RoleRepository repository){
        return args -> {

            List<Role> roleList = repository.findAll();
            if(roleList.isEmpty()){
                log.info("There is no role in database");
                repository.save(new Role((long)1, "SUPER_ADMIN"));
                repository.save(new Role((long)2, "ADMIN"));
                repository.save(new Role((long)3, "USER"));
               log.info("Role added");
            }

        };
    }
}
