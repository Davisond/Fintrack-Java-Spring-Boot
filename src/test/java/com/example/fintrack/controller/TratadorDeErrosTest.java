package com.example.fintrack.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.fintrack.Servico.LancamentoServico;
import com.example.fintrack.mapper.LancamentoMapper;
import com.example.fintrack.repositorio.RepositorioEmMemoria;

class TratadorDeErrosTest {

    private MockMvc mockMvc;

    @BeforeEach
    void preparar() {
        LancamentoServico servico = new LancamentoServico(new RepositorioEmMemoria());
        LancamentoController controller = new LancamentoController(servico, new LancamentoMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new TratadorDeErros())
                .build();
    }

    @Test
    void corpoVazioDevolve400ComTodosOsCamposInvalidos() throws Exception {
        mockMvc.perform(post("/api/v1/lancamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("application/problem+json"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.erros.length()").value(4));
    }

    @Test
    void idInexistenteDevolve404NoFormatoProblemDetails() throws Exception {
        mockMvc.perform(get("/api/v1/lancamentos/00000000-0000-0000-0000-000000000000"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith("application/problem+json"))
                .andExpect(jsonPath("$.status").value(404));
    }

        @Test
    void erroInesperadoDevolve500SemVazarDetalhes() {
        ProblemDetail problema = new TratadorDeErros()
                .erroInesperado(new RuntimeException("senha-do-banco"));

        assertEquals(500, problema.getStatus());
        assertFalse(String.valueOf(problema.getDetail()).contains("senha-do-banco"));
    }
    
}