package com.example.fintrack.Servico;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.fintrack.modelo.Lancamento;
import com.example.fintrack.modelo.LancamentoNaoEncontradoException;
import com.example.fintrack.modelo.TipoLancamento;
import com.example.fintrack.repositorio.RepositorioLancamentos;

public class LancamentoServico {

    private final RepositorioLancamentos repositorio;

    public LancamentoServico(RepositorioLancamentos repositorio){
        this.repositorio = repositorio;
    }

    public Lancamento buscar(UUID id){
        return repositorio.buscarPorId(id)
            .orElseThrow(() -> new LancamentoNaoEncontradoException(id));
    }

    public Lancamento salvar(Lancamento lancamento){
        return repositorio.salvar(lancamento);
    }

    public List<Lancamento> listar() {
        return repositorio.listar();
    }

    public BigDecimal totalDespesasDoMes(YearMonth mes){
        return repositorio.listar().stream()
                .filter(l -> l.tipo() == TipoLancamento.DESPESA)
                .filter(l -> YearMonth.from(l.data()).equals(mes))
                .map(Lancamento::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Optional<Lancamento> maiorDespesa() {
        return repositorio.listar().stream()
                .filter(l -> l.tipo() == TipoLancamento.DESPESA)
                .max(Comparator.comparing(Lancamento::valor));
    }

    public  Map<TipoLancamento, List<Lancamento>> agruparPorTipo(){
        return repositorio.listar().stream()
            .collect(Collectors.groupingBy(Lancamento::tipo));
    }

    public List<Lancamento> maisRecentes(int quantidade){
        return repositorio.listar().stream()
            .sorted(Comparator.comparing(Lancamento::data).reversed())
            .limit(quantidade)
            .toList();
    }

    public void remover(UUID id){
        if(!repositorio.remover(id)){
            throw new LancamentoNaoEncontradoException(id);
        }

    }



}
