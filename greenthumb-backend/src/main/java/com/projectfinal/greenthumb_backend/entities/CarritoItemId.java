package com.projectfinal.greenthumb_backend.entities;
import java.io.Serializable;
import java.util.Objects;

public class CarritoItemId implements Serializable {
    private Integer cliente; // Coincide con el tipo de Usuario.usuarioId (PK de Cliente)
    private Integer producto; // Coincide con el tipo de Producto.productoId

    // Constructor
    public CarritoItemId() {
    }

    public CarritoItemId(Integer clienteId, Integer productoId) {
        this.cliente = clienteId;
        this.producto = productoId;
    }

    // Getters
    public Integer getCliente() {
        return cliente;
    }
    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Integer getProducto() {
        return producto;
    }
    public void setProducto(Integer producto) {
        this.producto = producto;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarritoItemId that = (CarritoItemId) o;
        return Objects.equals(cliente, that.cliente) &&
                Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, producto);
    }
}

