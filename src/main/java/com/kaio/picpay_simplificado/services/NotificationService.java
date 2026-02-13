package com.kaio.picpay_simplificado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kaio.picpay_simplificado.dtos.NotificationDTO;
import com.kaio.picpay_simplificado.models.User;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();

        NotificationDTO notificationRequest = new NotificationDTO(email, message);
        
        String url = "https://util.devi.tools/api/v1/notify";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, notificationRequest, String.class);

            if(!(response.getStatusCode() == HttpStatus.OK)) {
                System.out.println("Erro ao enviar notificação");
                throw new Exception("Serviço de notificação está fora do ar");
            }
        } catch (Exception e) {
            // don't throw exception because this error doesn't break the application.
            System.out.println("Erro ao enviar notificação para o usuário: " + e.getMessage());
        }
    }
}
