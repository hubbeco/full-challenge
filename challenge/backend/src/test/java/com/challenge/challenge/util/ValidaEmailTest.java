package com.challenge.challenge.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.challenge.challenge.service.EmailService;
import org.junit.jupiter.api.Test;

public class ValidaEmailTest {

    private final EmailService emailService = new EmailService(null);

    @Test
    void testEmailValido() {
        assertTrue(emailService.isValidEmail("teste@teste.com"), "Email deve ser válido");
    }

    @Test
    void testEmailInvalido() {
        assertFalse(emailService.isValidEmail("teste@com"), "Email deve ser inválido");
        assertFalse(emailService.isValidEmail(""), "Email deve ser inválido");
        assertFalse(emailService.isValidEmail(null), "Email deve ser inválido");
    }
}
