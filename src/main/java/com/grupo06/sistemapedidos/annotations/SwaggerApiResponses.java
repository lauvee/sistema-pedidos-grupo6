package com.grupo06.sistemapedidos.annotations;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.grupo06.sistemapedidos.dto.ExceptionDTO;
/**
 * Anotación para definir las respuestas de los endpoints de la API. Manejado por Swagger.	
 * @Target para indicar que la anotación puede ser aplicada a un método o tipo.
 * @Retention para indicar que la anotación estará disponible en tiempo de ejecución.
 * @interface para definir una anotación, se crea la anotacion SwaggerApiResponses. 
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))),
    @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))),
    @ApiResponse(responseCode = "403", description = "Prohibido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))),
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))),
    @ApiResponse(responseCode = "405", description = "Método no permitido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))),
    @ApiResponse(responseCode = "408", description = "Tiempo de espera agotado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))),
    @ApiResponse(responseCode = "409", description = "Conflicto", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))),
    @ApiResponse(responseCode = "422", description = "Entidad no procesable", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))),
    @ApiResponse(responseCode = "503", description = "Servicio no disponible", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class)))
})
public @interface SwaggerApiResponses {
}