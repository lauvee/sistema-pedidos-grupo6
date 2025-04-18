package com.grupo06.sistemapedidos.enums;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeración de respuestas exitosas de la API con sus respectivos códigos de estado HTTP y mensajes.
 * Esta enumeración se utiliza para estandarizar los mensajes de éxito devueltos por la API.
 * @Getter, @RequiredArgsConstructor son anotaciones de Lombok para generar automáticamente los getters y
 * constructores con todos los argumentos
 */
@Getter
@RequiredArgsConstructor
public enum ApiSuccess {
	RESOURCE_RETRIEVED("Resource Retrieved", HttpStatus.OK),
	RESOURCE_CREATED("Resource Created", HttpStatus.CREATED),
	RESOURCE_UPDATED("Resource Updated", HttpStatus.OK),
	RESOURCE_REMOVED("Resource Removed", HttpStatus.NO_CONTENT),
	RESOURCE_NO_CONTENT("Resource No Content", HttpStatus.NO_CONTENT),
	RESOURCE_REGISTERED("Correctly registered user", HttpStatus.OK),
	RESOURCE_ALREADY_REGISTERED("Resource Already Registered", HttpStatus.BAD_REQUEST),
	USER_LOGGED_IN("User logged in successfully", HttpStatus.OK);

	private final String message;
	private final HttpStatus status;
}