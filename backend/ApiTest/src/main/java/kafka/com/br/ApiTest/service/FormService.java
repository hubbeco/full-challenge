package kafka.com.br.ApiTest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.com.br.ApiTest.dto.FormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class FormService {

    @Value("${recaptcha.key}")
    private String secretKey;

    @Value("${recaptcha.url}")
    private String baseUrl;

    @Autowired
    private JavaMailSender javaMailSender;

    public boolean verifyCaptcha(FormData data) {

        String url = baseUrl + "?secret=" + secretKey + "?response=" + data.gRecaptchaResponse();

        try{
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            var response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .join();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response);

            System.out.println("Response: " + json);
            return json.get("success").asBoolean();

        }catch (Exception e){
            System.out.println("Erro ao realizar verificação do captcha: " + e.getLocalizedMessage());
            return false;
        }
    }

}
