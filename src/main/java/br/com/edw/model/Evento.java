package br.com.edw.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Evento {

    private static final List<Evento> eventos = new ArrayList<>();
    private static Integer proximoId = 1;

    private Integer eventoId;
    private String nome;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private Boolean encerrado;

    public Evento(String nome, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {
        this.nome = nome;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.encerrado = false;
    }

    public Boolean validarDados() {
        return nome != null && !nome.isBlank()
                && dataHoraInicio != null
                && dataHoraFim != null
                && dataHoraFim.isAfter(dataHoraInicio);
    }

    public Boolean registrar() {

        if (!validarDados()) {
            return false;
        }

        eventoId = proximoId++;
        eventos.add(this);

        return true;
    }

    public Boolean validarCondicoesEncerramento() {
        return eventoId != null
                && !encerrado;
    }

    public Boolean encerrar() {

        if (!validarCondicoesEncerramento()) {
            return false;
        }

        dataHoraFim = LocalDateTime.now();
        encerrado = true;

        return true;
    }

    public Boolean permiteAdicionarCreditos() {
        return !encerrado;
    }

    public Boolean permiteRegistrarConsumo() {
        return !encerrado;
    }
}
