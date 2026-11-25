package com.example.fintrack.cli.app;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.example.fintrack.modelo.Lancamento;
import com.example.fintrack.modelo.LancamentoNaoEncontradoException;
import com.example.fintrack.modelo.Resultado;
import com.example.fintrack.modelo.TipoLancamento;
import com.example.fintrack.repositorio.RepositorioEmMemoria;
import com.example.fintrack.servico.LancamentoServico;

public class Cli {

    private final LancamentoServico servico;
    private final Scanner entrada;

    Cli(LancamentoServico servico, Scanner entrada){
        this.servico = servico;
        this.entrada = entrada;
    }

    public static void main(String[] args){
        LancamentoServico servico = new LancamentoServico(new RepositorioEmMemoria());
        new Cli(servico, new Scanner(System.in)).executar();
    }

    void executar() {
        System.out.println("FinTrack CLI. Comandos: add, list, remove <id>, resumo, sair");
        while (true) {
            System.out.print("> ");
            if (!entrada.hasNextLine()) {
                break;
            }
            String linha = entrada.nextLine().trim();
            if (linha.isEmpty()) {
                continue;
            }
            String[] partes = linha.split("\\s+", 2);
            String comando = partes[0].toLowerCase();
            String argumento = partes.length > 1 ? partes[1] : "";

            if (comando.equals("sair")) {
                System.out.println("Até logo!");
                break;
            }
            try {
                executarComando(comando, argumento);
            } catch (LancamentoNaoEncontradoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void executarComando(String comando, String argumento) {
    switch (comando) {
        case "add" -> adicionar();
        case "list" -> listar();
        case "remove" -> remover(argumento);
        case "resumo" -> resumo();
        default -> System.out.println("Comando desconhecido: " + comando);
    }
}

    private void adicionar() {
        try {
            String descricao = perguntar("Descrição: ");
            BigDecimal valor = new BigDecimal(perguntar("Valor (ex: 150.90): ").replace(',', '.'));
            LocalDate data = LocalDate.parse(perguntar("Data (AAAA-MM-DD): "));
            TipoLancamento tipo = TipoLancamento.valueOf(
                    perguntar("Tipo (RECEITA ou DESPESA): ").toUpperCase());

            Resultado<Lancamento> resultado = Lancamento.criar(descricao, valor, data, tipo);
            if (resultado.ehValido()) {
                servico.salvar(resultado.valor());
                System.out.println("Salvo: " + resultado.valor().id());
            } else {
                resultado.erros().forEach(erro -> System.out.println("Erro: " + erro));
            }
        } catch (IllegalArgumentException | DateTimeParseException e) {
            System.out.println("Valor, data ou tipo em formato inválido. Nada foi salvo.");
        }
    }

    private void listar() {
        List<Lancamento> todos = servico.listar();
        if (todos.isEmpty()) {
            System.out.println("Nenhum lançamento.");
            return;
        }
        todos.stream()
                .sorted(Comparator.comparing(Lancamento::data))
                .forEach(this::imprimir);
    }

    private void remover(String argumento) {
        if (argumento.isBlank()) {
            System.out.println("Uso: remove <id>");
            return;
        }
        try {
            servico.remover(UUID.fromString(argumento.trim()));
            System.out.println("Removido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Id inválido.");
        }
    }

    private void resumo() {
        YearMonth mes = YearMonth.now();
        System.out.println("Despesas de " + mes + ": R$ " + servico.totalDespesasDoMes(mes));

        servico.maiorDespesa().ifPresentOrElse(
                l -> System.out.println("Maior despesa: " + l.descricao() + " (R$ " + l.valor() + ")"),
                () -> System.out.println("Maior despesa: nenhuma"));

        var grupos = servico.agruparPorTipo();
        for (TipoLancamento tipo : TipoLancamento.values()) {
            int quantidade = grupos.getOrDefault(tipo, List.of()).size();
            System.out.println(tipo + ": " + quantidade + " lançamento(s)");
        }

        System.out.println("Mais recentes:");
        servico.maisRecentes(5).forEach(this::imprimir);
    }

    private void imprimir(Lancamento l) {
        System.out.printf("%s  %s  %-8s  R$ %s  %s%n",
                l.id(), l.data(), l.tipo(), l.valor(), l.descricao());
    }

    private String perguntar(String texto) {
        System.out.print(texto);
        return entrada.nextLine().trim();
    }

}
