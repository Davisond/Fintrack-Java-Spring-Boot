package com.example.fintrack.controller;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.fintrack.categoria.CategoriaNaoEncontradaException;
import com.example.fintrack.modelo.LancamentoNaoEncontradoException;

@RestControllerAdvice
public class TratadorDeErros extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(TratadorDeErros.class);

    public record ErroDeCampo(String campo, String mensagem) {
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        List<ErroDeCampo> erros = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> new ErroDeCampo(e.getField(), e.getDefaultMessage()))
                .sorted(Comparator.comparing(ErroDeCampo::campo))
                .toList();

        ProblemDetail problema = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Um ou mais campos são inválidos");
        problema.setTitle("Requisição inválida");
        problema.setProperty("erros", erros);

        return handleExceptionInternal(ex, problema, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(LancamentoNaoEncontradoException.class)
    public ProblemDetail naoEncontrado(LancamentoNaoEncontradoException ex) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage());
        problema.setTitle("Lançamento não encontrado");
        return problema;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail erroInesperado(Exception ex) {
        log.error("Erro inesperado", ex);
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno. Tente novamente mais tarde.");
        problema.setTitle("Erro interno");
        return problema;
    }

    @ExceptionHandler(CategoriaNaoEncontradaException.class)
    public ProblemDetail categoriaNaoEncontrada(CategoriaNaoEncontradaException ex) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage());
        problema.setTitle("Categoria não encontrada");
        return problema;
    }



}