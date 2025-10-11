package com.carrafafood.carrafafood.auth.core;

import com.sun.istack.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@ConfigurationProperties("algafood.jwt.keystore")
public class JwtKeyStoreProperties {

    @NotNull
    private String path;

    @NotNull
    private String password;

    @NotNull
    private String keypairAlias;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKeypairAlias() {
        return keypairAlias;
    }

    public void setKeypairAlias(String keypairAlias) {
        this.keypairAlias = keypairAlias;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
