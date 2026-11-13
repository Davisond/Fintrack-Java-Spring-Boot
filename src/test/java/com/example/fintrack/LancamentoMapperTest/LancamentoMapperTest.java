package com.example.fintrack.LancamentoMapperTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.example.fintrack.dto.LancamentoRequest;
import com.example.fintrack.dto.LancamentoResponse;
import com.example.fintrack.mapper.LancamentoMapper;
import com.example.fintrack.modelo.Lancamento;
import com.example.fintrack.modelo.TipoLancamento;

public class LancamentoMapperTest {

    private final LancamentoMapper mapper = new LancamentoMapper();

    @Test
    void converteRequestParaDominio() {
        LancamentoRequest request = new LancamentoRequest(
                "Mercado", new BigDecimal("150.90"), LocalDate.of(2026, 10, 1), TipoLancamento.DESPESA);

        Lancamento lancamento = mapper.toDominio(request);

        assertNotNull(lancamento.id());
        assertEquals("Mercado", lancamento.descricao());
        assertEquals(new BigDecimal("150.90"), lancamento.valor());
    }

    @Test
    void converteDominioParaResponse() {
        UUID id = UUID.randomUUID();
        Lancamento lancamento = new Lancamento(id, "Salário",
                new BigDecimal("5000.00"), LocalDate.of(2026, 10, 5), TipoLancamento.RECEITA);

        LancamentoResponse response = mapper.toResponse(lancamento);

        assertEquals(id, response.id());
        assertEquals("Salário", response.descricao());
        assertEquals(TipoLancamento.RECEITA, response.tipo());
    }
}
