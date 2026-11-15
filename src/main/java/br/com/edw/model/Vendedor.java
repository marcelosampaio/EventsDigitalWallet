package br.com.edw.model;

import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Funcionario {

    private static final List<Vendedor> vendedores = new ArrayList<>();
    private static Integer proximoId = 1;

    private final List<Integer> pontosDeVendaIds = new ArrayList<>();

    public Vendedor(String nome) {
        super(nome);
    }

    public Boolean validarDados() {
        return nome != null && !nome.isBlank();
    }

    public Boolean existeVendedorNoPontoDeVenda(Integer pontoDeVendaId) {
        return pontosDeVendaIds.contains(pontoDeVendaId);
    }

    public Boolean registrar() {

        if (!validarDados()) {
            return false;
        }

        if (funcionarioId != null) {
            return false;
        }

        funcionarioId = proximoId++;
        vendedores.add(this);

        return true;
    }

    public Boolean atualizar() {

        if (funcionarioId == null || !validarDados()) {
            return false;
        }

        Vendedor vendedorExistente = localizarVendedor(funcionarioId);

        if (vendedorExistente == null) {
            return false;
        }

        vendedorExistente.nome = nome;

        return true;
    }

    private static Vendedor localizarVendedor(Integer funcionarioId) {
        return vendedores.stream()
                .filter(vendedor -> vendedor.funcionarioId.equals(funcionarioId))
                .findFirst()
                .orElse(null);
    }
}
