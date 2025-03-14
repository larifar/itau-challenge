package challenge.itau.dto;

import java.time.OffsetDateTime;

public record TransacaoRequest(double valor, OffsetDateTime dataHora) {
}
