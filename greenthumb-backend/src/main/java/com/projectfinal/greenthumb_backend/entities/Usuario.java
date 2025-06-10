package com.projectfinal.greenthumb_backend.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED) // Estrategia para que Cliente y Administrador tengan su propia tabla
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UsuarioID")
    private Integer usuarioId;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "Apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "Email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "Contrasena", nullable = false, length = 255)
    private String contrasena;

    @Column(name = "Telefono", nullable = false, length = 20)
    private String telefono;

    @Column(name = "FechaAlta", nullable = false, updatable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "auth0_id", unique = true, length = 255)
    private String auth0Id;

    // --- Constructores ---

    public Usuario() {
        // Constructor vacío requerido por JPA
        this.fechaAlta = LocalDateTime.now();
    }

    public Usuario(String nombre, String apellido, String email, String contrasena, String telefono, String auth0Id) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.auth0Id = auth0Id;
        this.fechaAlta = LocalDateTime.now();
    }

    // --- Getters y Setters ---

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        // Generalmente la fecha de alta no se modifica, pero se incluye el setter por completitud
        this.fechaAlta = fechaAlta;
    }

    public String getAuth0Id() {
        return auth0Id;
    }

    public void setAuth0Id(String auth0Id) {
        this.auth0Id = auth0Id;
    }

    // --- Métodos de Utilidad (equals, hashCode, toString) ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(usuarioId, usuario.usuarioId) && Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, email);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "usuarioId=" + usuarioId +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}