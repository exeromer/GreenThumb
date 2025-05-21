package com.projectfinal.greenthumb_backend.entities;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "carritoitems")
@IdClass(CarritoItemId.class)
public class CarritoItem {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UsuarioID_Cliente", referencedColumnName = "UsuarioID")
    private Cliente cliente;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductoID")
    private Producto producto;

    @Column(name = "Cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "PrecioAlAgregar", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioAlAgregar;

    @Column(name = "FechaAgregado", nullable = false, updatable = false)
    private LocalDateTime fechaAgregado;

    @Column(name = "FechaUltimaModificacion", nullable = false)
    private LocalDateTime fechaUltimaModificacion;

    // Constructores
    public CarritoItem() {
    }

    public CarritoItem(Cliente cliente, Producto producto, Integer cantidad, BigDecimal precioAlAgregar) {
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioAlAgregar = precioAlAgregar;
    }

    @PrePersist
    protected void onPrePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.fechaAgregado = now;
        this.fechaUltimaModificacion = now;
    }

    @PreUpdate
    protected void onPreUpdate() {
        this.fechaUltimaModificacion = LocalDateTime.now();
    }

    // Getters y Setters
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioAlAgregar() {
        return precioAlAgregar;
    }

    public void setPrecioAlAgregar(BigDecimal precioAlAgregar) {
        this.precioAlAgregar = precioAlAgregar;
    }

    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    public LocalDateTime getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(LocalDateTime fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    // equals y hashCode para entidades con claves compuestas
    // Se basan en los campos que forman la clave primaria.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarritoItem that = (CarritoItem) o;
        // Compara las entidades Cliente y Producto directamente si están disponibles,
        // o sus IDs si es más apropiado y están garantizados.
        return Objects.equals(cliente != null ? cliente.getUsuarioId() : null, that.cliente != null ? that.cliente.getUsuarioId() : null) &&
                Objects.equals(producto != null ? producto.getProductoId() : null, that.producto != null ? that.producto.getProductoId() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente != null ? cliente.getUsuarioId() : null,
                producto != null ? producto.getProductoId() : null);
    }


    @Override
    public String toString() {
        return "CarritoItem{" +
                "clienteId=" + (cliente != null ? cliente.getUsuarioId() : "null") +
                ", productoId=" + (producto != null ? producto.getProductoId() : "null") +
                ", cantidad=" + cantidad +
                ", precioAlAgregar=" + precioAlAgregar +
                '}';
    }
}

