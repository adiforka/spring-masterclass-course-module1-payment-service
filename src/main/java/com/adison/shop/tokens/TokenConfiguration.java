package com.adison.shop.tokens;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfiguration {

    @Bean
    public TokenService tokenService(TokenRepository tokenRepository) {
        return new TokenService(tokenRepository);
    }
}
