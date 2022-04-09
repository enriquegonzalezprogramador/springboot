package com.juniorgonzalez.spring.example.configuration;

import com.juniorgonzalez.spring.example.caseuse.GetUser;
import com.juniorgonzalez.spring.example.caseuse.GetUserImplement;
import com.juniorgonzalez.spring.example.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaseUseConfiguration {

    @Bean
    GetUser getUser(UserService userService) {
        return new GetUserImplement(userService);
    }

}
