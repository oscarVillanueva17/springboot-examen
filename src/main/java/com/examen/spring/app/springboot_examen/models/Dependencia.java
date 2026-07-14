package com.examen.spring.app.springboot_examen.models;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

// Entidad para la tabla "dependencias" en la BD
@Entity
@Table(name = "dependencias")
@Data // Crea automáticamente todos los Getters, Setters, toString, equals y hashCode.
@NoArgsConstructor // Crea el constructor vacío (el que JPA exige para funcionar).
@AllArgsConstructor // Crea un constructor con todos los atributos de la clase.
@Builder // Permite crear objetos de forma fluida (ej:
         // Dependencia.builder().nombre("RH").build()).
public class Dependencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "el nombre es obligatorio")
    @Size(min = 3, message = "el nombre debe tener al menos 3 caracteres")
    private String nombre;

    private String direccion;
    private String telefono;

    @Email(message = "el correo no es valido")
    @NotBlank(message = "el correo es obligatorio")
    private String correo;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaRegistro;

    @UpdateTimestamp
    private LocalDateTime fechaActualizacion;

    // Valor por defecto activo = true usando Lombok Builder
    @Builder.Default
    private Boolean activo = true;
}
