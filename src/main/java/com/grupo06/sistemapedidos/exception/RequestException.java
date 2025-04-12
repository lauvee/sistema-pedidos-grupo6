package com.grupo06.sistemapedidos.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;
import com.grupo06.sistemapedidos.enums.ApiError;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Excepción personalizada para representar errores de petición HTTP.
 * Esta clase extiende RuntimeException para permitir excepciones no verificadas
 * y proporciona información estructurada sobre los errores que ocurren durante
 * el procesamiento de las peticiones.
 * 
 * La anotación @Getter de Lombok genera automáticamente métodos getter para todos los campos.
 * La anotación @Slf4j proporciona un logger para registrar información sobre las excepciones.
 */
@Slf4j
@Getter
public class RequestException extends RuntimeException {
	private final boolean hasError;
	private final String title;
	private final String detail;
	private final HttpStatus statusCode;
	private Map<String, String> reasons;
	private ApiError apiError;

	/**
	 * Constructor para crear una excepción de petición con un mensaje de error específico. mediante el enum ApiError.
	 * 
	 * @param APiError Enum que representa el errores de la API
	 */
	public RequestException(ApiError apiError) {
		this.hasError = true;
		this.title = apiError.getTitle();
		this.detail = apiError.getDetail();
		this.statusCode = apiError.getStatus();
		this.apiError = apiError;
		log.error("ID error ReservationNotFoundException: {}", apiError);
	}

	/**
	 * Constructor para crear una excepción de petición con un mensaje de error específico,
	 * con un código de estado HTTP mendiante el enim APiError y algunos detalles adicionales
	 * introducidos por el usuario. 
	 * 
	 * @param apiError Enum que representa el errores de la API
	 * @param title titulo de la excepcion
	 * @param detail detalles de la excepcion
	 */
	public RequestException(ApiError apiError, String title, String detail) {
		this.hasError = true;
		this.title = title;
		this.detail = detail;
		this.statusCode = apiError.getStatus();
		this.apiError = apiError;
		log.error("ID error ReservationNotFoundException: {}", apiError);
	}

	/**
	 * Constructor para crear una excepción de petición con un mensaje de error específico y un código de estado HTTP. mediante 
	 * los datos introducidos por el usuario en la excepcion, de forma ampliamente personalizada.
	 * 
	 * @param title titulo de la excepcion
	 * @param detail detalles de la excepcion
	 * @param statusCode código de estado HTTP
	 * @param reasons razones adicionales del error
	 */
	public RequestException(String title, String detail, HttpStatus statusCode, Map<String, String> reasons) {
		this.hasError = true;
		this.title = title;
		this.detail = detail;
		this.statusCode = statusCode;
		this.reasons = reasons;
		log.error("ID error ReservationNotFoundException: {}", apiError);
	}
}