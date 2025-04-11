package com.grupo06.sistemapedidos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Clase de configuración para la conexión a la base de datos.
 * Define y expone el bean DataSource que será utilizado por Spring Data JPA.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.grupo06.sistemapedidos.repository")
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    /**
     * Crea y configura el bean DataSource que se utilizará para conectar a la base de datos PostgreSQL.
     *
     * @return DataSource configurado con los valores proporcionados.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        // Se especifica el driver para PostgreSQL
        dataSource.setDriverClassName("org.postgresql.Driver");
        // Se asignan los valores de conexión desde las propiedades externas
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        return dataSource;
    }
}
