package br.com.edw.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarteiraDigital {

    private static final List<CarteiraDigital> carteiras = new ArrayList<>();
    private static Integer proximoId = 1;

    private Integer carteiraDigitalId;
    private String qrCode;
    private BigDecimal saldo;
    private Integer eventoId;

    public Boolean validarDados() {
        return qrCode != null && !qrCode.isBlank()
                && saldo != null
                && saldo.compareTo(BigDecimal.ZERO) >= 0;
    }

    public Boolean existeCarteira() {
        return carteiras.stream()
                .anyMatch(carteira -> carteira.qrCode.equals(qrCode));
    }

    public Boolean criar() {

        if (carteiraDigitalId != null) {
            return false;
        }

        qrCode = UUID.randomUUID().toString();
        saldo = BigDecimal.ZERO;

        if (!validarDados() || existeCarteira()) {
            qrCode = null;
            saldo = null;
            return false;
        }

        carteiraDigitalId = proximoId++;
        carteiras.add(this);

        return true;
    }

    public Boolean associarEvento(Integer eventoId) {

        if (carteiraDigitalId == null || eventoId == null
                || Evento.localizarEvento(eventoId) == null) {
            return false;
        }

        this.eventoId = eventoId;

        return true;
    }

    public static CarteiraDigital localizarPorQrCode(String qrCode) {

        if (qrCode == null || qrCode.isBlank()) {
            return null;
        }

        return carteiras.stream()
                .filter(carteira -> carteira.qrCode.equals(qrCode))
                .findFirst()
                .orElse(null);
    }

    public Boolean validarQuantidadeCreditos(BigDecimal quantidade) {
        return quantidade != null
                && quantidade.compareTo(BigDecimal.ZERO) > 0;
    }

    public Boolean adicionarCreditos(BigDecimal quantidade) {

        if (carteiraDigitalId == null
                || !validarQuantidadeCreditos(quantidade)
                || eventoId == null) {
            return false;
        }

        Evento evento = Evento.localizarEvento(eventoId);

        if (evento == null || !evento.permiteAdicionarCreditos()) {
            return false;
        }

        atualizarSaldo(quantidade);

        return true;
    }

    public BigDecimal consultarSaldo() {
        return saldo;
    }

    public Boolean possuiCreditosDisponiveis() {
        return saldo != null
                && saldo.compareTo(BigDecimal.ZERO) > 0;
    }

    public Boolean possuiSaldoSuficiente(BigDecimal valor) {
        return validarQuantidadeCreditos(valor)
                && saldo != null
                && saldo.compareTo(valor) >= 0;
    }

    public Boolean descontarCreditos(BigDecimal valor) {

        if (carteiraDigitalId == null
                || !possuiSaldoSuficiente(valor)
                || eventoId == null) {
            return false;
        }

        Evento evento = Evento.localizarEvento(eventoId);

        if (evento == null || !evento.permiteRegistrarConsumo()) {
            return false;
        }

        atualizarSaldo(valor.negate());

        return true;
    }

    public BigDecimal atualizarSaldo(BigDecimal quantidade) {

        if (quantidade == null || saldo == null) {
            return saldo;
        }

        BigDecimal novoSaldo = saldo.add(quantidade);

        if (novoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            return saldo;
        }

        saldo = novoSaldo;

        return saldo;
    }
}
