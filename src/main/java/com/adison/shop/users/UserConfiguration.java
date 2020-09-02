package com.adison.shop.users;


import com.adison.shop.common.TextSource;
import com.adison.shop.mails.MailService;
import com.adison.shop.tokens.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    @Bean
    public UserService userService(UserRepository userRepository,
                                   TokenService tokenService,
                                   TextSource textSource,
                                   MailService mailService) {
        return new UserService(userRepository, tokenService, textSource, mailService);
    }
}
