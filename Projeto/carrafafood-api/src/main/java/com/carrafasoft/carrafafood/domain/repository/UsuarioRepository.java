package com.carrafasoft.carrafafood.domain.repository;

import com.carrafasoft.carrafafood.domain.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {
}
