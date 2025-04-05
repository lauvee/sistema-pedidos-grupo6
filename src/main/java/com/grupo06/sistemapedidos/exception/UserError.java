package com.grupo06.sistemapedidos.exception;

/**
 * Clase de error personalizada para manejar errores relacionados con usuarios.
 * Esta clase extiende la clase Error de Java y proporciona constructores para
 * crear instancias de UserError con o sin un mensaje específico.
 */
public class UserError extends Error {
    public UserError() {
        super();
    }

    public UserError(String message) {
        super(message);
    }
}
