package com.example.demo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Schema(description = "DTO para la gestión de libros")
public class BookDTO {
    
    private Long id;

    @NotBlank(message = "El título es requerido")
    @Size(min = 1, max = 100, message = "El título debe tener entre 1 y 100 caracteres")
    @Schema(description = "Título del libro", example = "El Quijote")
    private String title;

    @NotBlank(message = "El autor es requerido")
    @Size(min = 1, max = 100, message = "El autor debe tener entre 1 y 100 caracteres")
    @Schema(description = "Autor del libro", example = "Miguel de Cervantes")
    private String author;

    @NotNull(message = "El precio es requerido")
    @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
    @Schema(description = "Precio del libro", example = "29.99")
    private BigDecimal price;

    @Schema(description = "Fecha de publicación del libro")
    private LocalDateTime publicationDate;

    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$", message = "ISBN inválido")
    @Schema(description = "ISBN del libro", example = "978-3-16-148410-0")
    private String isbn;

    @Schema(description = "Disponibilidad del libro", example = "true")
    private Boolean available;
} 