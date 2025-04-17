package com.grupo06.sistemapedidos.repository;

import com.grupo06.sistemapedidos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * UserRepository es una interfaz que extiende JpaRepository para realizar operaciones CRUD en la entidad User.
 * Proporciona métodos para buscar usuarios por ID, correo electrónico, nombre de usuario y rol.
 *
 * {@link Repository} Anotación de Spring que indica que esta interfaz es un repositorio de acceso a datos.
 */
@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Busca un usuario que copincidan con su electrónico y su nombre.
     *
     * @param email el correo electrónico del usuario a buscar
     * @param name el nombre del usuario a buscar
     * @return un Optional que contiene el usuario si se encuentra, o vacío si no
     */
    Optional<Usuario> findByEmailAndName(String email, String name);

    /**
     * Busca un usuario por su nombre de correo electrónico.
     * 
     * @param email el correo electrónico del usuario a buscar
     * @return un Optional que contiene el usuario si se encuentra, o vacío si no
     */
    Optional<Usuario> findByEmail(String email);


    /**
     * Busca todos los usuarios pasandole una lista con sus id
     *
     * @param list lista de identificadores de usuarios
     * @return un Optional que contiene el usuario si se encuentra, o vacío si no
     */
    Optional<List<Usuario>> findByIdIn(List<Integer> list);
}