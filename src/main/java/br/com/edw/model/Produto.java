package br.com.edw.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Produto {

    private static final List<Produto> produtos = new ArrayList<>();
    private static Integer proximoId = 1;

    private Integer produtoId;
    private String nome;
    private BigDecimal preco;
    private Integer eventoId;

    public Produto(String nome, BigDecimal preco, Integer eventoId) {
        this.nome = nome;
        this.preco = preco;
        this.eventoId = eventoId;
    }

    public Boolean validarDados() {
        return nome != null && !nome.isBlank()
                && preco != null && preco.compareTo(BigDecimal.ZERO) > 0
                && eventoId != null;
    }

    public Boolean existeProdutoNoEvento(Integer eventoId) {
        return produtos.stream()
                .anyMatch(produto ->
                        produto.eventoId.equals(eventoId)
                                && produto.nome.equalsIgnoreCase(nome));
    }

    public Boolean registrar() {

        if (!validarDados() || existeProdutoNoEvento(eventoId)) {
            return false;
        }

        produtoId = proximoId++;
        produtos.add(this);

        return true;
    }

    public Boolean atualizar() {

        if (produtoId == null || !validarDados()) {
            return false;
        }

        Produto produtoExistente = localizarProduto(produtoId);

        if (produtoExistente == null) {
            return false;
        }

        boolean nomeDuplicado = produtos.stream()
                .anyMatch(produto ->
                        !produto.produtoId.equals(produtoId)
                                && produto.eventoId.equals(eventoId)
                                && produto.nome.equalsIgnoreCase(nome));

        if (nomeDuplicado) {
            return false;
        }

        produtoExistente.nome = nome;
        produtoExistente.preco = preco;
        produtoExistente.eventoId = eventoId;

        return true;
    }

    public static Produto localizarProduto(Integer produtoId) {
        return produtos.stream()
                .filter(produto -> produto.produtoId.equals(produtoId))
                .findFirst()
                .orElse(null);
    }

    public BigDecimal consultarValor() {
        return preco;
    }
}
