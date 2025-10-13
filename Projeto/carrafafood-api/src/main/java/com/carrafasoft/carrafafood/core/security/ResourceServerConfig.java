package com.carrafasoft.carrafafood.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                    .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
                .antMatchers(HttpMethod.PUT, "/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
                .antMatchers(HttpMethod.GET, "/v1/cozinhas/**").authenticated()
                    //.antMatchers("/v1/cozinhas/**").permitAll()//Caso queira permitir uma pagina sem autenticação tem que ser antes do .anyRequest().authenticated();
                    .anyRequest().denyAll()
                .and()
                    //.oauth2ResourceServer().opaqueToken();
                    .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());

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

    private JwtAuthenticationConverter jwtAuthenticationConverter() {

        var jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {

            List<String> authorities = jwt.getClaimAsStringList("authorities");

            if(authorities == null) {
                authorities = Collections.emptyList();
            }

            return authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        });

        return jwtAuthenticationConverter;
    }


    //REMOVIDO APÓS INLCUIR A CHAVE PUBLICA algafood-pkey.pem
//    @Bean
//    public JwtDecoder jwtDecoder() {
//
//        var secret = new SecretKeySpec("0gO2yPfwxUgi p/eh+Gwp82oOJoR+nxy+/NFdXHqSti4=".getBytes(), "HmacSHA256");
//
//        return NimbusJwtDecoder.withSecretKey(secret).build();
//    }
}
