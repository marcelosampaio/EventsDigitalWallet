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

        if (carteiraDigitalId == null || eventoId == null) {
            return false;
        }

        this.eventoId = eventoId;

        return true;
    }
}
