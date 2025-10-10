package com.carrafasoft.carrafafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                    .authorizeRequests()
                    //.antMatchers("/v1/cozinhas/**").permitAll()//Caso queira permitir uma pagina sem autenticação tem que ser antes do .anyRequest().authenticated();
                    .anyRequest().authenticated()
                .and()
                    //.oauth2ResourceServer().opaqueToken();
                    .oauth2ResourceServer().jwt();

//        http.httpBasic()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/v1/cozinhas/**").permitAll()//Caso queira permitir uma pagina sem autenticação tem que ser antes do .anyRequest().authenticated();
//                    .anyRequest().authenticated()
//
//                .and()
//                .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Não envia cookie de sessão
//
//                .and()
//                .csrf().disable();
    }

    @Bean
    public JwtDecoder jwtDecoder() {

        var secret = new SecretKeySpec("0gO2yPfwxUgi p/eh+Gwp82oOJoR+nxy+/NFdXHqSti4=".getBytes(), "HmacSHA256");

        return NimbusJwtDecoder.withSecretKey(secret).build();
    }
}
