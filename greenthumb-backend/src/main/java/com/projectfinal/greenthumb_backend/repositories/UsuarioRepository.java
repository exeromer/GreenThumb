package com.projectfinal.greenthumb_backend.repositories;
import com.projectfinal.greenthumb_backend.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByAuth0Id(String auth0Id);

}
