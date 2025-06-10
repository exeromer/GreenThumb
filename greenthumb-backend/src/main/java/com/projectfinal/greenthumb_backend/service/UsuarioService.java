package com.projectfinal.greenthumb_backend.service;

import com.projectfinal.greenthumb_backend.dto.ClienteRegistroDTO;
import com.projectfinal.greenthumb_backend.entities.Administrador;
import com.projectfinal.greenthumb_backend.entities.Cliente;
import com.projectfinal.greenthumb_backend.entities.Usuario;
import com.projectfinal.greenthumb_backend.repositories.AdministradorRepository;
import com.projectfinal.greenthumb_backend.repositories.ClienteRepository;
import com.projectfinal.greenthumb_backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Busca un usuario en la BD local usando el ID de Auth0 del token.
     * @param auth0Id El ID de Auth0 del usuario.
     * @return Un Optional que contiene al Usuario si se encuentra.
     */
    public Optional<Usuario> findByAuth0Id(String auth0Id) {
        return usuarioRepository.findByAuth0Id(auth0Id);
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     * Decide si crear un Cliente o un Administrador basándose en los roles del token.
     * @param registroDTO Contiene todos los datos del perfil y del formulario.
     * @param authentication Contiene los detalles de la sesión del usuario, incluidos los roles.
     * @return La entidad Usuario recién creada.
     */
    @Transactional
    public Usuario registrarNuevoUsuario(ClienteRegistroDTO registroDTO, Authentication authentication) {

        // Comprobamos si el usuario ya existe para evitar duplicados.
        if (usuarioRepository.findByAuth0Id(registroDTO.getAuth0Id()).isPresent()) {
            throw new IllegalStateException("Error: El usuario con Auth0 ID " + registroDTO.getAuth0Id() + " ya está registrado.");
        }

        // La comprobación final y correcta, usando "ADMIN" en mayúsculas como viene en el token.
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        if (isAdmin) {
            // --- Lógica para crear un Administrador ---
            Administrador nuevoAdmin = new Administrador();

            // Seteamos los campos de Usuario desde el DTO
            nuevoAdmin.setAuth0Id(registroDTO.getAuth0Id());
            nuevoAdmin.setEmail(registroDTO.getEmail());
            nuevoAdmin.setNombre(registroDTO.getNombre());
            nuevoAdmin.setApellido(registroDTO.getApellido());
            nuevoAdmin.setTelefono(registroDTO.getTelefono());
            nuevoAdmin.setContrasena(passwordEncoder.encode("auth0-placeholder-password"));

            // Seteamos los campos específicos de Administrador
            nuevoAdmin.setNivelAcceso("AdminGeneral");
            nuevoAdmin.setFechaUltimoAcceso(LocalDateTime.now());

            // Guardamos y devolvemos el nuevo Administrador
            return administradorRepository.save(nuevoAdmin);

        } else {
            // --- Lógica para crear un Cliente ---
            Cliente nuevoCliente = new Cliente();

            // Seteamos los campos de Usuario desde el DTO
            nuevoCliente.setAuth0Id(registroDTO.getAuth0Id());
            nuevoCliente.setEmail(registroDTO.getEmail());
            nuevoCliente.setNombre(registroDTO.getNombre());
            nuevoCliente.setApellido(registroDTO.getApellido());
            nuevoCliente.setTelefono(registroDTO.getTelefono());
            nuevoCliente.setContrasena(passwordEncoder.encode("auth0-placeholder-password"));

            // Seteamos los campos específicos de Cliente desde el DTO
            nuevoCliente.setCalle(registroDTO.getCalle());
            nuevoCliente.setNumero(registroDTO.getNumero());
            nuevoCliente.setCodigoPostal(registroDTO.getCodigoPostal());
            nuevoCliente.setCiudad(registroDTO.getCiudad());
            nuevoCliente.setProvincia(registroDTO.getProvincia());

            // Guardamos y devolvemos el nuevo Cliente
            return clienteRepository.save(nuevoCliente);
        }
    }
}