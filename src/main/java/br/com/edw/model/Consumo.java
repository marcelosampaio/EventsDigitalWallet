package br.com.edw.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Consumo {

    private static final List<Consumo> consumos = new ArrayList<>();
    private static Integer proximoId = 1;

    private Integer consumoId;
    private LocalDateTime dataHora;
    private Integer quantidade;
    private BigDecimal valor;

    public Boolean registrar(Integer quantidade, BigDecimal valor) {

        if (consumoId != null
                || quantidade == null || quantidade <= 0
                || valor == null
                || valor.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        this.dataHora = LocalDateTime.now();
        this.quantidade = quantidade;
        this.valor = valor;
        this.consumoId = proximoId++;

        consumos.add(this);

        return true;
    }
}
