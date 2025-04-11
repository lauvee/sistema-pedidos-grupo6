package com.grupo06.sistemapedidos.repository;

import com.grupo06.sistemapedidos.enums.RoleEnum;
import com.grupo06.sistemapedidos.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para gestionar las entidades de tipo 'Roles'.
 * Este repositorio permite realizar operaciones de persistencia en la base de datos relacionadas con los roles de usuario.
 * Extiende JpaRepository, que proporciona métodos CRUD y más para interactuar con la base de datos.
 *
 * @Repository Anotación de Spring que indica que esta interfaz es un repositorio de acceso a datos.
 */
@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {

    /**
     * Método para encontrar un rol por su nombre.
     *
     * Este método busca un rol basado en el nombre del rol definido en el enum 'RoleEnum'.
     *
     * @param name El nombre del rol, representado como un valor del enumerado 'RoleEnum'.
     * @return Un objeto Optional que contiene el rol encontrado, o vacío si no se encuentra.
     */
    Optional<Roles> findByName(RoleEnum name);
}
