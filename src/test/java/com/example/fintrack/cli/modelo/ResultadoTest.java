package com.example.fintrack.cli.modelo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


public class ResultadoTest {

        @Test
    void resultadoOkEhValido() {
        Resultado<String> r = Resultado.ok("tudo certo");

        assertTrue(r.ehValido());
        assertEquals("tudo certo", r.valor());
    }

    @Test
    void resultadoComFalhaNaoEhValido() {
        Resultado<String> r = Resultado.falha(List.of("erro 1", "erro 2"));

        assertFalse(r.ehValido());
        assertEquals(2, r.erros().size());
    }
}
