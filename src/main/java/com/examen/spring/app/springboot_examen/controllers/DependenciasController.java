package com.examen.spring.app.springboot_examen.controllers;

import com.examen.spring.app.springboot_examen.models.Dependencia;
import com.examen.spring.app.springboot_examen.services.DependenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controlador REST expuesto en /api/dependencias. Habilitado CORS para localhost:4200
@Tag(name = "Dependencias", description = "CRUD de dependencias")
@RestController
@RequestMapping("/api/dependencias")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DependenciasController {

    private final DependenciaService service;

    // Obtener lista paginada con filtros opcionales de búsqueda
    @Operation(summary = "Listar dependencias", description = "Retorna lista de dependencias paginada con filtros opcionales.")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public Page<Dependencia> listar(
            @Parameter(description = "Filtro de búsqueda por nombre") @RequestParam(required = false) String nombre,
            @Parameter(description = "Filtro de búsqueda por correo") @RequestParam(required = false) String correo,
            @Parameter(description = "Filtro de búsqueda por estado (activo/inactivo)") @RequestParam(required = false) Boolean activo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.listar(nombre, correo, activo, page, size);
    }

    // Crear una nueva dependencia con validaciones automáticas
    @Operation(summary = "Crear dependencia")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creado"),
            @ApiResponse(responseCode = "400", description = "Validación fallida")
    })
    @PostMapping
    public ResponseEntity<Dependencia> crear(@Valid @RequestBody Dependencia d) {
        return ResponseEntity.status(201).body(service.guardar(d));
    }

    // Modificar datos de una dependencia
    @Operation(summary = "Actualizar dependencia")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actualizado"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Dependencia> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Dependencia d) {
        return ResponseEntity.ok(service.actualizar(id, d));
    }

    // Eliminar dependencia de forma definitiva
    @Operation(summary = "Eliminar dependencia")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Eliminado"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Alternar el estado activo / inactivo
    @Operation(summary = "Activar / Desactivar dependencia")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado cambiado"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Dependencia> toggle(@PathVariable Long id) {
        return ResponseEntity.ok(service.toggleActivo(id));
    }
}
