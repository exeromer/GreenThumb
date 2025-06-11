// greenthumb-backend/src/main/java/com/projectfinal/greenthumb_backend/service/MercadoPagoService.java
package com.projectfinal.greenthumb_backend.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import com.projectfinal.greenthumb_backend.dto.MercadoPagoPreferenceRequestDTO;
import com.projectfinal.greenthumb_backend.dto.MercadoPagoPreferenceResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MercadoPagoService {

    @Value("${mercadopago.access_token}")
    private String accessToken;

    @Value("${mercadopago.client_id}")
    private String clientId;

    @Value("${mercadopago.client_secret}")
    private String clientSecret;

    public MercadoPagoPreferenceResponseDTO createPreference(MercadoPagoPreferenceRequestDTO request) {
        try {
            // Configura las credenciales de Mercado Pago
            MercadoPagoConfig.setAccessToken(accessToken);

            // Crea los ítems de la preferencia a partir del DTO
            List<PreferenceItemRequest> items = request.getItems().stream()
                    .map(itemDto ->
                            PreferenceItemRequest.builder()
                                    .id(itemDto.getId())
                                    .title(itemDto.getTitle())
                                    .description(itemDto.getDescription())
                                    .quantity(itemDto.getQuantity())
                                    .unitPrice(itemDto.getUnitPrice())
                                    .build())
                    .collect(Collectors.toList());

            // Crea la solicitud de preferencia
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    // Aquí puedes añadir más configuraciones como URLs de retorno, etc.
                    .build();

            // Crea la preferencia usando el cliente de Mercado Pago
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            // Retorna la URL de inicio del pago y el ID de la preferencia
            return new MercadoPagoPreferenceResponseDTO(
                    preference.getId(),
                    preference.getInitPoint(),
                    preference.getSandboxInitPoint()
            );
        } catch (Exception e) {
            // Manejo de errores (puedes loggear la excepción o relanzarla como una excepción personalizada)
            e.printStackTrace();
            throw new RuntimeException("Error al crear la preferencia de Mercado Pago: " + e.getMessage());
        }
    }
}