package com.projectfinal.greenthumb_backend.controller;

import com.projectfinal.greenthumb_backend.dto.ClienteRegistroDTO;
import com.projectfinal.greenthumb_backend.dto.UsuarioDTO;
import com.projectfinal.greenthumb_backend.entities.Usuario;
import com.projectfinal.greenthumb_backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:3000")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> getUsuarioActual(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // =========== INICIO DE LOGS DE DEPURACIÓN ===========
        System.out.println("======================================================");
        System.out.println("== INICIANDO DEPURACIÓN DE AUTENTICACIÓN ==");
        System.out.println("== ENDPOINT: /api/usuarios/me ==");

        // 1. Imprimimos el objeto Authentication completo
        System.out.println("1. Objeto Authentication: " + authentication);

        // 2. Imprimimos las 'authorities' (roles) que Spring ha procesado
        System.out.println("2. Autoridades Procesadas: " + authentication.getAuthorities());

        // 3. Imprimimos todos los claims (datos) del token JWT
        Jwt jwt = (Jwt) authentication.getPrincipal();
        Map<String, Object> claims = jwt.getClaims();
        System.out.println("3. Claims del Token JWT: " + claims);

        // 4. Imprimimos específicamente el claim donde esperamos los roles
        System.out.println("4. Claim de Roles Específico ('https://greenthumb.com/'): " + claims.get("https://greenthumb.com/"));

        System.out.println("== FIN DE DEPURACIÓN ==");
        System.out.println("======================================================");
        // =========== FIN DE LOGS DE DEPURACIÓN ===========String auth0Id = jwt.getSubject();

        String auth0Id = jwt.getSubject();
        Optional<Usuario> usuarioOpt = usuarioService.findByAuth0Id(auth0Id);

        if (usuarioOpt.isPresent()) {
            // Si el usuario existe, lo convertimos a DTO y lo devolvemos
            Usuario usuario = usuarioOpt.get();
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            UsuarioDTO dto = new UsuarioDTO(
                    usuario.getUsuarioId(),
                    usuario.getEmail(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getAuth0Id(),
                    roles
            );
            return ResponseEntity.ok(dto);
        } else {
            // Si no existe, devolvemos 404 Not Found.
            // Esta es la señal para que el frontend muestre el formulario de registro.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> registrarNuevoCliente(@RequestBody ClienteRegistroDTO registroDTO, Authentication authentication) {
        // La autenticación ya fue verificada por Spring Security para poder llegar aquí.
        // Ahora solo pasamos el DTO con todos los datos.
        Usuario nuevoUsuario = usuarioService.registrarNuevoUsuario(registroDTO, authentication);

        UsuarioDTO dto = new UsuarioDTO(
                nuevoUsuario.getUsuarioId(),
                nuevoUsuario.getEmail(),
                nuevoUsuario.getNombre(),
                nuevoUsuario.getApellido(),
                nuevoUsuario.getAuth0Id(),
                null // Los roles no son relevantes en la respuesta de un registro nuevo
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

}