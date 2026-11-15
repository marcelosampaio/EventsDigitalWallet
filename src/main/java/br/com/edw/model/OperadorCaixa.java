package br.com.edw.model;

import java.util.ArrayList;
import java.util.List;

public class OperadorCaixa extends Funcionario {

    private static final List<OperadorCaixa> operadores = new ArrayList<>();
    private static Integer proximoId = 1;

    private final List<Integer> eventosIds = new ArrayList<>();

    public OperadorCaixa(String nome) {
        super(nome);
    }

    public Boolean validarDados() {
        return nome != null && !nome.isBlank();
    }

    public Boolean existeOperadorNoEvento(Integer eventoId) {
        return eventosIds.contains(eventoId);
    }

    public Boolean registrar() {

        if (!validarDados()) {
            return false;
        }

        if (funcionarioId != null) {
            return false;
        }

        funcionarioId = proximoId++;
        operadores.add(this);

        return true;
    }

    public Boolean atualizar() {

        if (funcionarioId == null || !validarDados()) {
            return false;
        }

        OperadorCaixa operadorExistente = localizarOperador(funcionarioId);

        if (operadorExistente == null) {
            return false;
        }

        operadorExistente.nome = nome;

        return true;
    }

    private static OperadorCaixa localizarOperador(Integer funcionarioId) {
        return operadores.stream()
                .filter(operador -> operador.funcionarioId.equals(funcionarioId))
                .findFirst()
                .orElse(null);
    }
}
