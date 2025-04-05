package com.grupo06.sistemapedidos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.grupo06.sistemapedidos.model.Usuario;

/**
 * UserRepository es una interfaz que extiende JpaRepository para realizar operaciones CRUD en la entidad User.
 * Proporciona métodos para buscar usuarios por ID, correo electrónico, nombre de usuario y rol.
 * 
 * Está marcada con la anotación @Repository para que Spring la detecte como un componente de acceso a datos.
 */
@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer> {
    
    /**
     * Busca un usuario por su ID.
     * 
     * @param id el ID del usuario a buscar
     * @return un Optional que contiene el usuario si se encuentra, o vacío si no
     */
    Optional<Usuario> findById(int id);

    /**
     * Busca un usuario por su correo electrónico.
     * 
     * @param email el correo electrónico del usuario a buscar
     * @return un Optional que contiene el usuario si se encuentra, o vacío si no
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param username el nombre de usuario a buscar
     * @return un Optional que contiene el usuario si se encuentra, o vacío si no
     */
    Optional<Usuario> findByName(String username);

    /**
     * Busca un usuario por su nombre de usuario o correo electrónico.
     * 
     * @param username el nombre de usuario a buscar
     * @param email el correo electrónico del usuario a buscar
     * @return un Optional que contiene el usuario si se encuentra, o vacío si no
     */
    Optional<Usuario> findByNameOrEmail(String username, String email);

     /**
     * Busca todos los usuarios pasandole una lista con sus id
     * 
     * @param list lista de identificadores de usuarios
     * @return un Optional que contiene el usuario si se encuentra, o vacío si no
     */
    Optional<List<Usuario>> findByIdIn(List<Integer> list);
}