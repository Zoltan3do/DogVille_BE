package manu_barone.DogVille.configs;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import manu_barone.DogVille.entities.Utente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailgunSender {
    private String apiKey;
    private String domain;

    public MailgunSender(@Value("${mailgun.apikey}") String apiKey,
                         @Value("${mailgun.domain}") String domain) {
        this.apiKey = apiKey;
        this.domain = domain;
    }

    public void sendAdoptionEmail(Utente recipient, String oggetto, String messaggio) {
        if (recipient == null || recipient.getEmail() == null) {
            throw new IllegalArgumentException("Recipient o email del destinatario sono null");
        }
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domain + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", "baronemanu109@gmail.com")
                .queryString("to", "baronemanu109@gmail.com")
                .queryString("subject", oggetto)
                .queryString("text", messaggio)
                .asJson();
        System.out.println("Response: " + response.getBody());
    }


}