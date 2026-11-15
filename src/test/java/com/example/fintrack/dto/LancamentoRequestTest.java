package com.example.fintrack.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.example.fintrack.modelo.TipoLancamento;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class LancamentoRequestTest {
    
    @Test
    void rejeitaValorComTresCasasDecimais() {
        LancamentoRequest request = new LancamentoRequest(
                "Teste", new BigDecimal("10.999"), LocalDate.of(2026, 10, 1), TipoLancamento.DESPESA);

        Set<ConstraintViolation<LancamentoRequest>> violacoes = validator.validate(request);

        assertEquals(1, violacoes.size());
        assertEquals("valor", violacoes.iterator().next().getPropertyPath().toString());
    }

    private final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void requestValidoNaoTemViolacoes() {
        LancamentoRequest request = new LancamentoRequest(
                "Mercado", new BigDecimal("150.90"), LocalDate.of(2026, 10, 1), TipoLancamento.DESPESA);

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void requestVazioViolaOsQuatroCampos() {
        LancamentoRequest request = new LancamentoRequest(null, null, null, null);

        Set<String> campos = validator.validate(request).stream()
                .map(v -> v.getPropertyPath().toString())
                .collect(Collectors.toSet());

        assertEquals(Set.of("descricao", "valor", "data", "tipo"), campos);
    }

    @Test
    void rejeitaDataNoFuturo() {
        LancamentoRequest request = new LancamentoRequest(
                "Futuro", new BigDecimal("10.00"), LocalDate.now().plusDays(1), TipoLancamento.DESPESA);

        Set<ConstraintViolation<LancamentoRequest>> violacoes = validator.validate(request);

        assertEquals(1, violacoes.size());
        assertEquals("data", violacoes.iterator().next().getPropertyPath().toString());
    }
    
}
