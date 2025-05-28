// greenthumb-backend/src/main/java/com/projectfinal/greenthumb_backend/controller/CarritoController.java
package com.projectfinal.greenthumb_backend.controller;

import com.projectfinal.greenthumb_backend.dto.CarritoItemDTO;
import com.projectfinal.greenthumb_backend.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    @Autowired
    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    // Endpoint para agregar o actualizar un producto en el carrito
    @PostMapping("/{clienteId}/agregar")
    public ResponseEntity<CarritoItemDTO> agregarOActualizarProductoEnCarrito(
            @PathVariable Integer clienteId, // Cambiado a Integer
            @RequestParam Integer productoId, // Cambiado a Integer
            @RequestParam Integer cantidad) {
        try {
            CarritoItemDTO carritoItem = carritoService.agregarProductoAlCarrito(clienteId, productoId, cantidad);
            return new ResponseEntity<>(carritoItem, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para obtener el carrito de un cliente
    @GetMapping("/{clienteId}")
    public ResponseEntity<List<CarritoItemDTO>> obtenerCarritoPorCliente(@PathVariable Integer clienteId) { // Cambiado a Integer
        try {
            List<CarritoItemDTO> carritoItems = carritoService.obtenerCarritoPorClienteId(clienteId);
            if (carritoItems.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(carritoItems, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para actualizar la cantidad de un producto en el carrito
    @PutMapping("/{clienteId}/actualizar")
    public ResponseEntity<CarritoItemDTO> actualizarCantidadProducto(
            @PathVariable Integer clienteId, // Cambiado a Integer
            @RequestParam Integer productoId, // Cambiado a Integer
            @RequestParam Integer nuevaCantidad) {
        try {
            CarritoItemDTO carritoItem = carritoService.actualizarCantidadProductoEnCarrito(clienteId, productoId, nuevaCantidad);
            if (carritoItem == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(carritoItem, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para eliminar un producto del carrito
    @DeleteMapping("/{clienteId}/eliminar/{productoId}")
    public ResponseEntity<Void> eliminarProductoDelCarrito(
            @PathVariable Integer clienteId, // Cambiado a Integer
            @PathVariable Integer productoId) { // Cambiado a Integer
        try {
            carritoService.eliminarProductoDelCarrito(clienteId, productoId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para vaciar el carrito de un cliente
    @DeleteMapping("/{clienteId}/vaciar")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Integer clienteId) { // Cambiado a Integer
        try {
            carritoService.vaciarCarrito(clienteId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}