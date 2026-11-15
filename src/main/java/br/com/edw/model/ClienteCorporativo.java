package br.com.edw.model;

import java.util.ArrayList;
import java.util.List;

public class ClienteCorporativo {
    private static final List<ClienteCorporativo> clientes = new ArrayList<>();
    private static Integer proximoId = 1;

    private Integer clienteCorporativoId;
    private String razaoSocial;
    private String cnpj;

    public ClienteCorporativo(String razaoSocial, String cnpj) {
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
    }

    public Boolean validarDados() {
        return razaoSocial != null && !razaoSocial.isBlank()
                && cnpj != null && !cnpj.isBlank();
    }

    public static Boolean existeClientePorCnpj(String cnpj) {
        return clientes.stream()
                .anyMatch(cliente -> cliente.cnpj.equals(cnpj));
    }

    public Boolean registrar() {

        if (!validarDados()) {
            return false;
        }

        if (existeClientePorCnpj(cnpj)) {
            return false;
        }

        clienteCorporativoId = proximoId++;
        clientes.add(this);

        return true;
    }
}
