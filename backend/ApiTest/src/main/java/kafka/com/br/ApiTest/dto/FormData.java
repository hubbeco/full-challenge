package kafka.com.br.ApiTest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record FormData(
        @NotBlank
        @JsonProperty("g-recaptcha-response")
        String gRecaptchaResponse,
        @NotBlank
        String comment,
        @NotBlank(message = "The name must not be blank")
        String name,
        @NotBlank(message = "The email is invalid")
        @Email
        String mail
) {
}
