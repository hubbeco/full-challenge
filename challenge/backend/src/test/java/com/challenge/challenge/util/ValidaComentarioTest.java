package com.challenge.challenge.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.challenge.challenge.service.EmailService;
import org.junit.jupiter.api.Test;

public class ValidaComentarioTest {

    private final EmailService emailService = new EmailService(null);

    @Test
    void testComentarioValido() {
        assertTrue(emailService.isValidComment("Este é um comentário válido."), "Comentário deve ser válido");
    }

    @Test
    void testComentarioInvalido() {
        assertFalse(emailService.isValidComment("Curto"), "Comentário deve ser inválido");
        assertFalse(emailService.isValidComment(null), "Comentário deve ser inválido");
    }
}
