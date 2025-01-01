package com.carrafasoft.carrafafood.domain.repository;

import com.carrafasoft.carrafafood.domain.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
}
