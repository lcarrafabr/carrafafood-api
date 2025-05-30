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
                    .oauth2ResourceServer().opaqueToken();

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
}
