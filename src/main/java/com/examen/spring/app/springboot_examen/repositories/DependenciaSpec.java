package com.examen.spring.app.springboot_examen.repositories;

import com.examen.spring.app.springboot_examen.models.Dependencia;
import org.springframework.data.jpa.domain.Specification;

/**
 * Clase para definir los filtros dinámicos (Specifications) de Dependencias.
 * 
 * Se usa JPA Criteria para construir las consultas de búsqueda de forma dinámica.
 * Si algún filtro llega nulo o vacío, se ignora y no se incluye en la consulta final.
 */
public class DependenciaSpec {

    /**
     * Filtra dependencias cuyo nombre contenga el texto indicado (búsqueda parcial).
     * La comparación es insensible a mayúsculas/minúsculas (LOWER + LIKE).
     *
     * @param nombre texto a buscar dentro del nombre
     * @return Specification con la condición LIKE, o null si el valor está vacío
     */
    public static Specification<Dependencia> byNombre(String nombre) {
        // Si no se envió filtro, retorna null → Spring Data ignora esta condición
        return (root, q, cb) -> nombre == null || nombre.isBlank() ? null
                : cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%");
    }

    /**
     * Filtra dependencias cuyo correo contenga el texto indicado (búsqueda parcial).
     *
     * @param correo texto a buscar dentro del correo
     * @return Specification con la condición LIKE, o null si el valor está vacío
     */
    public static Specification<Dependencia> byCorreo(String correo) {
        return (root, q, cb) -> correo == null || correo.isBlank() ? null
                : cb.like(cb.lower(root.get("correo")), "%" + correo.toLowerCase() + "%");
    }

    /**
     * Filtra dependencias por su estado activo/inactivo (coincidencia exacta).
     *
     * @param activo true para activas, false para inactivas, null para no filtrar
     * @return Specification con la condición de igualdad, o null si no se indicó valor
     */
    public static Specification<Dependencia> byActivo(Boolean activo) {
        return (root, q, cb) -> activo == null ? null : cb.equal(root.get("activo"), activo);
    }
}
