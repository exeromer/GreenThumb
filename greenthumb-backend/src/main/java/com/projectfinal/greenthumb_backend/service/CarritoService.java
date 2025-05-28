// greenthumb-backend/src/main/java/com/projectfinal/greenthumb_backend/service/CarritoService.java
package com.projectfinal.greenthumb_backend.service;

import com.projectfinal.greenthumb_backend.entities.CarritoItem;
import com.projectfinal.greenthumb_backend.entities.CarritoItemId;
import com.projectfinal.greenthumb_backend.entities.Cliente;
import com.projectfinal.greenthumb_backend.entities.Producto;
import com.projectfinal.greenthumb_backend.entities.PrecioProductoActual;
import com.projectfinal.greenthumb_backend.entities.ImagenesProducto;
import com.projectfinal.greenthumb_backend.dto.CarritoItemDTO;
import com.projectfinal.greenthumb_backend.repositories.CarritoItemRepository;
import com.projectfinal.greenthumb_backend.repositories.ClienteRepository;
import com.projectfinal.greenthumb_backend.repositories.ProductoRepository;
import com.projectfinal.greenthumb_backend.repositories.PrecioProductoActualRepository;
import com.projectfinal.greenthumb_backend.repositories.ImagenesProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarritoService {

    private final CarritoItemRepository carritoItemRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final PrecioProductoActualRepository precioProductoActualRepository;
    private final ImagenesProductoRepository imagenesProductoRepository;

    @Autowired
    public CarritoService(CarritoItemRepository carritoItemRepository, ClienteRepository clienteRepository, ProductoRepository productoRepository, PrecioProductoActualRepository precioProductoActualRepository, ImagenesProductoRepository imagenesProductoRepository) {
        this.carritoItemRepository = carritoItemRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.precioProductoActualRepository = precioProductoActualRepository;
        this.imagenesProductoRepository = imagenesProductoRepository;
    }

    // 1. Agregar Producto al Carrito
    @Transactional
    public CarritoItemDTO agregarProductoAlCarrito(Integer clienteId, Integer productoId, Integer cantidad) { // Cambiado a Integer
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        Cliente cliente = clienteRepository.findById(clienteId) // clienteId es Integer
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));
        Producto producto = productoRepository.findById(productoId) // productoId es Integer
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));

        // Verificar si el producto tiene stock suficiente
        if (producto.getStockActual() < cantidad) {
            throw new IllegalArgumentException("No hay suficiente stock para el producto: " + producto.getNombreProducto() + ". Stock disponible: " + producto.getStockActual());
        }

        CarritoItemId id = new CarritoItemId(clienteId, productoId);
        Optional<CarritoItem> existingItem = carritoItemRepository.findById(id);

        // Obtener el precio actual del producto al momento de agregarlo
        PrecioProductoActual precioActual = precioProductoActualRepository.findByProducto(producto) // findByProducto
                .orElseThrow(() -> new RuntimeException("Precio actual del producto no encontrado para ID: " + productoId));
        BigDecimal precioAlAgregar = precioActual.getPrecioVenta(); // getPrecioVenta()

        CarritoItem carritoItem;
        if (existingItem.isPresent()) {
            carritoItem = existingItem.get();
            carritoItem.setCantidad(carritoItem.getCantidad() + cantidad);
            carritoItem.setPrecioAlAgregar(precioAlAgregar);
        } else {
            carritoItem = new CarritoItem(cliente, producto, cantidad, precioAlAgregar);
        }

        CarritoItem savedItem = carritoItemRepository.save(carritoItem);
        return mapToDTO(savedItem);
    }

    // 2. Obtener Carrito de un Cliente
    @Transactional(readOnly = true)
    public List<CarritoItemDTO> obtenerCarritoPorClienteId(Integer clienteId) { // Cambiado a Integer
        Cliente cliente = clienteRepository.findById(clienteId) // clienteId es Integer
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));

        List<CarritoItem> carritoItems = carritoItemRepository.findByCliente(cliente);
        return carritoItems.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 3. Actualizar Cantidad de un Producto en el Carrito
    @Transactional
    public CarritoItemDTO actualizarCantidadProductoEnCarrito(Integer clienteId, Integer productoId, Integer nuevaCantidad) { // Cambiado a Integer
        if (nuevaCantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
        if (nuevaCantidad == 0) {
            eliminarProductoDelCarrito(clienteId, productoId);
            return null;
        }

        CarritoItemId id = new CarritoItemId(clienteId, productoId);
        CarritoItem carritoItem = carritoItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en el carrito para el cliente con ID: " + clienteId + " y producto con ID: " + productoId));

        if (carritoItem.getProducto().getStockActual() < nuevaCantidad) {
            throw new IllegalArgumentException("No hay suficiente stock para el producto: " + carritoItem.getProducto().getNombreProducto() + ". Stock disponible: " + carritoItem.getProducto().getStockActual());
        }

        PrecioProductoActual precioActual = precioProductoActualRepository.findByProducto(carritoItem.getProducto()) // findByProducto
                .orElseThrow(() -> new RuntimeException("Precio actual del producto no encontrado para ID: " + productoId));
        BigDecimal precioAlActualizar = precioActual.getPrecioVenta(); // getPrecioVenta()

        carritoItem.setCantidad(nuevaCantidad);
        carritoItem.setPrecioAlAgregar(precioAlActualizar);
        CarritoItem savedItem = carritoItemRepository.save(carritoItem);
        return mapToDTO(savedItem);
    }

    // 4. Eliminar Producto del Carrito
    @Transactional
    public void eliminarProductoDelCarrito(Integer clienteId, Integer productoId) { // Cambiado a Integer
        CarritoItemId id = new CarritoItemId(clienteId, productoId);
        if (!carritoItemRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado en el carrito para el cliente con ID: " + clienteId + " y producto con ID: " + productoId);
        }
        carritoItemRepository.deleteById(id);
    }

    // 5. Vaciar Carrito de un Cliente
    @Transactional
    public void vaciarCarrito(Integer clienteId) { // Cambiado a Integer
        Cliente cliente = clienteRepository.findById(clienteId) // clienteId es Integer
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));
        carritoItemRepository.deleteByCliente(cliente);
    }

    // Método auxiliar para mapear CarritoItem a CarritoItemDTO
    private CarritoItemDTO mapToDTO(CarritoItem carritoItem) {
        CarritoItemDTO dto = new CarritoItemDTO();
        dto.setClienteId(carritoItem.getCliente().getUsuarioId());
        dto.setProductoId(carritoItem.getProducto().getProductoId()); // productoId es Integer, no necesita .longValue()
        dto.setCantidad(carritoItem.getCantidad());
        dto.setNombreProducto(carritoItem.getProducto().getNombreProducto());

        Optional<PrecioProductoActual> precioActual = precioProductoActualRepository.findByProducto(carritoItem.getProducto());
        // Aquí debes usar getPrecioVenta()
        precioActual.ifPresent(p -> dto.setPrecioUnitario(p.getPrecioVenta().doubleValue())); // Convertir BigDecimal a Double

        List<ImagenesProducto> imagenes = carritoItem.getProducto().getImagenes();
        if (imagenes != null && !imagenes.isEmpty()) {
            dto.setImagenProductoUrl(imagenes.get(0).getUrlImagen());
        }

        return dto;
    }
}