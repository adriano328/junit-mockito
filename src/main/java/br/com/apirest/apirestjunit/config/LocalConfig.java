package br.com.apirest.apirestjunit.config;

import br.com.apirest.apirestjunit.domain.User;
import br.com.apirest.apirestjunit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository repository;

    @Bean
    public void startDB() {
        User u1 = new User(null, "Lucas", "lucas@gmail.com", "123");
        User u2 = new User(null, "Luiz", "Luiz@gmail.com", "123");

        repository.saveAll(List.of(u1, u2));
    }

}
