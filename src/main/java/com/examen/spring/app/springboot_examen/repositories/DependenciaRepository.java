package com.examen.spring.app.springboot_examen.repositories;

import com.examen.spring.app.springboot_examen.models.Dependencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

// Repositorio JPA para Dependencia. JpaSpecificationExecutor nos permite usar filtros dinámicos (Spec)
public interface DependenciaRepository
                extends JpaRepository<Dependencia, Long>,
                JpaSpecificationExecutor<Dependencia> {
        long countByActivo(Boolean activo);

}
