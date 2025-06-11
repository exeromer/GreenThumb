// greenthumb-backend/src/main/java/com/projectfinal/greenthumb_backend/controller/MercadoPagoController.java
package com.projectfinal.greenthumb_backend.controller;

import com.projectfinal.greenthumb_backend.dto.MercadoPagoPreferenceRequestDTO;
import com.projectfinal.greenthumb_backend.dto.MercadoPagoPreferenceResponseDTO;
import com.projectfinal.greenthumb_backend.service.MercadoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mercadopago")
public class MercadoPagoController {

    private final MercadoPagoService mercadoPagoService;

    @Autowired
    public MercadoPagoController(MercadoPagoService mercadoPagoService) {
        this.mercadoPagoService = mercadoPagoService;
    }

    @PostMapping("/create-preference")
    public ResponseEntity<MercadoPagoPreferenceResponseDTO> createPreference(@RequestBody MercadoPagoPreferenceRequestDTO request) {
        try {
            MercadoPagoPreferenceResponseDTO response = mercadoPagoService.createPreference(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para simular la notificación de pago (IPN)
    // En un entorno real, Mercado Pago enviaría una notificación a este endpoint.
    // Para la prueba, el frontend o Postman podría simularlo.
    @PostMapping("/webhook")
    public ResponseEntity<Void> receiveWebhook(@RequestBody String payload) {
        // Aquí puedes parsear el payload de Mercado Pago para verificar el estado del pago.
        // Para la prueba, simplemente loggearemos el payload.
        System.out.println("Webhook de Mercado Pago recibido: " + payload);

        // En un escenario real, buscarías el pedido asociado al pago,
        // verificarías el estado (aprobado, pendiente, rechazado) y actualizarías tu base de datos.
        // Por ejemplo:
        // if (payload.contains("approved")) {
        //     // Actualizar estado del pedido a "Aprobado"
        // } else if (payload.contains("pending")) {
        //     // Actualizar estado del pedido a "Pendiente"
        // }

        return new ResponseEntity<>(HttpStatus.OK); // Siempre responde 200 OK a Mercado Pago
    }
}