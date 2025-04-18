package com.grupo06.sistemapedidos.dto;

import java.time.LocalDateTime;
import java.util.Map;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object para Errores
 * @Schema Permite configurar la documentación de Swagger para este DTO
 * @Getter, @Setter y @RequiredArgsConstructor son anotaciones de Lombok para generar automáticamente los getters, setters y constructores con todos los argumentos
 * Para más infromacion sobre el dto, ver la documentacion de Swagger en: localhost:8080/api/swagger-ui.html
 */
@Getter
@Setter
@RequiredArgsConstructor
@Schema(description = "DTO para manejar excepciones")
public class ExceptionDTO {
	@Schema(description = "Indica si hay un error", type = "boolean", example = "true")
	private final boolean hasError = true;

	@Schema(description = "Mensaje del error", type = "string")
	private final String message;

	@Schema(description = "Descripción detallada del error", type = "string")
	private final String description;

	@Schema(description = "Código de error", type = "integer")
	private final int code;

	@Schema(description = "Razones adicionales del error", type = "map")
	private final Map<String, String> reasons;

	@Schema(description = "Marca de tiempo del error", format = "date-time")
	private final LocalDateTime timestamp;
}