package com.grupo06.sistemapedidos.config;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvConfig {

    // Cargar las variables del archivo .env y establecerlas como propiedades del sistema
    public DotenvConfig() {
        Dotenv dotenv = Dotenv.configure().load();
        dotenv.entries().forEach(entry ->
            System.setProperty(entry.getKey(), entry.getValue())
        );
    }
}
