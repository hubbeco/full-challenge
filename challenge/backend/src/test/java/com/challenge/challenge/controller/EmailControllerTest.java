package com.challenge.challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import com.challenge.challenge.model.ContactForm;
import com.challenge.challenge.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ContactForm contactForm;
    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        contactForm = new ContactForm();
        contactForm.setName("Nome Teste");
        contactForm.setMail("teste@teste.com");
        contactForm.setComment("Este é um comentário de teste com mais de 15 caracteres.");
    }

    @Test
    void criarFormulario() throws Exception {
        when(emailService.verifyRecaptcha("6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe")).thenReturn(true);

        String formJson = objectMapper.writeValueAsString(contactForm);

        this.mockMvc.perform(post("/api/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .content(formJson)
                .header("recaptchaToken", "6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe"))
                .andExpect(status().isCreated());
    }
}
