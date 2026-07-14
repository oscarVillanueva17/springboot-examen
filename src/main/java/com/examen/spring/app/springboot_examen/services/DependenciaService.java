package com.examen.spring.app.springboot_examen.services;

import com.examen.spring.app.springboot_examen.models.Dependencia;
import com.examen.spring.app.springboot_examen.repositories.DependenciaRepository;
import com.examen.spring.app.springboot_examen.repositories.DependenciaSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

// Servicio para manejar toda la lógica del CRUD de Dependencias
@Service
@RequiredArgsConstructor // Inyección automática por constructor usando Lombok
public class DependenciaService {

    private final DependenciaRepository repo;

    // Método para listar dependencias aplicando filtros opcionales de búsqueda y paginación
    public Page<Dependencia> listar(String nombre, String correo, Boolean activo, int page, int size) {
        // Junta los filtros de búsqueda dinámicos definidos en DependenciaSpec
        Specification<Dependencia> spec = Specification
                .where(DependenciaSpec.byNombre(nombre))
                .and(DependenciaSpec.byCorreo(correo))
                .and(DependenciaSpec.byActivo(activo));
        
        // Ejecuta la consulta usando las especificaciones y el objeto de paginación
        return repo.findAll(spec, PageRequest.of(page, size));
    }

    // Guarda una nueva dependencia
    public Dependencia guardar(Dependencia d) {
        return repo.save(d);
    }

    // Busca y actualiza los campos permitidos de una dependencia existente
    public Dependencia actualizar(Long id, Dependencia d) {
        // Si no la encuentra, orElseThrow() lanza NoSuchElementException y el Handler responde 404
        Dependencia existing = repo.findById(id).orElseThrow();
        
        // Setea solo los datos que el usuario puede editar
        existing.setNombre(d.getNombre());
        existing.setDireccion(d.getDireccion());
        existing.setTelefono(d.getTelefono());
        existing.setCorreo(d.getCorreo());
        
        return repo.save(existing);
    }

    // Elimina una dependencia. Lanza error 404 si el ID no existe en la BD
    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new java.util.NoSuchElementException();
        }
        repo.deleteById(id);
    }

    // Cambia el estado de activo (true/false) de una dependencia
    public Dependencia toggleActivo(Long id) {
        Dependencia d = repo.findById(id).orElseThrow();
        
        // Invierte el estado actual manejando valores nulos de forma segura (si es null lo activa)
        d.setActivo(!Boolean.TRUE.equals(d.getActivo()));
        return repo.save(d);
    }
}
