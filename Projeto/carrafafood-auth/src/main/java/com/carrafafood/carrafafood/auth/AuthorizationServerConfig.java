package com.carrafafood.carrafafood.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient("carrafafood-web")
                    .secret(passwordEncoder.encode("web123"))
                    .authorizedGrantTypes("password", "refresh_token")
                    .scopes("write", "read")
                .accessTokenValiditySeconds(60 * 60 * 6)
                .refreshTokenValiditySeconds(60* 60)

                //criando um client_credentials simulando um outro backend pra se conectar a API
                .and()
                .withClient("faturamento")
                    .secret(passwordEncoder.encode("faturamento123"))
                    .authorizedGrantTypes("client_credentials")
                    .scopes("write", "read")

                //Usando authorization_code
                .and()
                .withClient("foodanalytics")
                    .secret(passwordEncoder.encode("foodanalytics123"))
                    .authorizedGrantTypes("authorization_code")
                    .scopes("write", "read")
                    .redirectUris("http://aplicacao-cliente.com.br");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security.checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .reuseRefreshTokens(false);
    }
}
