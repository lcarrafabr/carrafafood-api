package com.carrafafood.carrafafood.auth.core;

import com.carrafafood.carrafafood.auth.domain.Usuario;
import com.carrafafood.carrafafood.auth.domain.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrato com o e-mail informado";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(USUARIO_NAO_ENCONTRADO));

        return new AuthUser(usuario);
    }
}
