package challenge.itau.model;

import challenge.itau.dto.TransacaoRequest;

import java.time.OffsetDateTime;

public class Transacao {
    private double valor;
    private OffsetDateTime dataHora;

    public Transacao(TransacaoRequest request){
        this.valor = request.valor();
        this.dataHora = request.dataHora();
    }

    public double getValor() {
        return valor;
    }

    public OffsetDateTime getDataHora() {
        return dataHora;
    }
}
