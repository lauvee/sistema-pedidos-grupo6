package com.grupo06.sistemapedidos.enums;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeración de errores de la API con sus respectivos códigos de estado HTTP, títulos y detalles.
 * Esta enumeración se utiliza para estandarizar los mensajes de error devueltos por la API.
 * 
 * @Getter, @RequiredArgsConstructor son anotaciones de Lombok para generar automáticamente los getters
 * y constructores con todos los argumentos
 */
@Getter
@RequiredArgsConstructor
public enum ApiError {
	ROLE_NOT_FOUND(
		HttpStatus.NOT_FOUND,
		"Role Not Found",
		"Role not found in the system."),
	USER_DELETE_FAILED(
		HttpStatus.INTERNAL_SERVER_ERROR,
		"User Deletion Failed",
		"Failed to delete user from the system."),
	USER_NOT_FOUND(
		HttpStatus.NOT_FOUND,
		"User Not Found",
		"User not found in the system."),
	BAD_REQUEST(
			HttpStatus.BAD_REQUEST,
			"Invalid Request",
			"The request contains malformed syntax or invalid parameters."),
	INVALID_REQUEST_DATA(
			HttpStatus.BAD_REQUEST,
			"Invalid Data",
			"Request data contains invalid values or incorrect format."),
	AUTHENTICATION_FAILED(
			HttpStatus.UNAUTHORIZED,
			"Authentication Failed",
			"Invalid or missing authentication credentials."),
	FORBIDDEN(
			HttpStatus.FORBIDDEN,
			"Forbidden Action",
			"Insufficient permissions for the requested operation."),
	RECORD_NOT_FOUND(
			HttpStatus.NOT_FOUND,
			"Resource Not Found",
			"The requested resource does not exist."),
	METHOD_NOT_ALLOWED(
			HttpStatus.METHOD_NOT_ALLOWED,
			"Method Not Allowed",
			"HTTP method not supported for this endpoint."),
	CONFLICT(
			HttpStatus.CONFLICT,
			"Resource Conflict",
			"Operation violates resource state constraints."),
	DUPLICATE_RESOURCE(
			HttpStatus.CONFLICT,
			"Duplicate Resource",
			"Resource with unique identifier already exists."),
	UNPROCESSABLE_ENTITY(
			HttpStatus.UNPROCESSABLE_ENTITY,
			"Validation Error",
			"Request validation failed for entity constraints."),
	ASSOCIATED_RESOURCES(
			HttpStatus.CONFLICT,
			"Associated Resources",
			"Cannot modify resource with existing dependencies."),
	REQUEST_TIMEOUT(
			HttpStatus.REQUEST_TIMEOUT,
			"Request Timeout",
			"Operation exceeded maximum allowed time."),
	INTERNAL_SERVER_ERROR(
			HttpStatus.INTERNAL_SERVER_ERROR,
			"Server Error",
			"Unexpected internal server error occurred. Please try again later."),
	EXTERNAL_API_ERROR(
			HttpStatus.SERVICE_UNAVAILABLE,
			"Service Unavailable",
			"Required external service is temporarily unavailable."),
	DATABASE_ERROR(
			HttpStatus.INTERNAL_SERVER_ERROR,
			"Database Error",
			"Failed to complete database operation."),
	INVALID_FORMAT(
			HttpStatus.BAD_REQUEST,
			"Invalid Format",
			"Request data format is not supported or malformed."),
	ENDPOINT_NOT_FOUND(
			HttpStatus.NOT_FOUND,
			"Endpoint Not Found",
			"Requested API endpoint does not exist."),
	DUPLICATE_EMAIL(
			HttpStatus.CONFLICT,
			"Duplicate Email",
			"Email already registered");

	private final HttpStatus status;
	private final String title;
	private final String detail;
}