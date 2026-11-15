package com.example.fintrack.validacao;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ValorMonetarioValidatorTest {

    private final ValorMonetarioValidator validador = new ValorMonetarioValidator();

    @ParameterizedTest
    @ValueSource(strings = { "10.99", "10.9", "10", "100", "0.01", "10.990" })
    void aceitaAteDuasCasasDecimais(String valor) {
        assertTrue(validador.isValid(new BigDecimal(valor), null));
    }

    @ParameterizedTest
    @ValueSource(strings = { "10.999", "0.001", "1.234" })
    void rejeitaMaisDeDuasCasasDecimais(String valor) {
        assertFalse(validador.isValid(new BigDecimal(valor), null));
    }

    @Test
    void nuloEhAceitoPoisObrigatoriedadeEPapelDoNotNull() {
        assertTrue(validador.isValid(null, null));
    }

    
}
