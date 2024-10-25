package com.challenge.challenge.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.challenge.challenge.service.EmailService;
import org.junit.jupiter.api.Test;

public class ValidaNomeTest {

    private final EmailService emailService = new EmailService(null);

    @Test
    void testNomeValido() {
        assertTrue(emailService.isValidName("Nome Válido"), "Nome deve ser válido");
    }

    @Test
    void testNomeInvalido() {
        assertFalse(emailService.isValidName(""), "Nome deve ser inválido");
        assertFalse(emailService.isValidName(null), "Nome deve ser inválido");
    }
}
