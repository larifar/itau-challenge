package challenge.itau.service;

import challenge.itau.dto.Estatistica;
import challenge.itau.dto.TransacaoException;
import challenge.itau.dto.TransacaoRequest;
import challenge.itau.model.Transacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class TransacaoService {
    private final List<Transacao> transacoes = new ArrayList<>();
    // Criação do logger manualmente
    private static final Logger logger = LoggerFactory.getLogger(TransacaoService.class);

    public List<Transacao> getTransacoes(){
        return this.transacoes;
    }

    public Transacao getTransacao(int index){
        return this.transacoes.get(index);
    }
    public void add(TransacaoRequest request){
        if (request.dataHora().isAfter(OffsetDateTime.now())){
            logger.warn("Tentativa de transação no futuro: {}", request);
            throw new TransacaoException("Transação não pode ser feita no futuro.");
        }
        if (request.valor() <= 0){
            logger.warn("Transação não pode ter valor <= 0: {}", request);
            throw new TransacaoException("Transação não pode ter valor nulo ou negativo.");
        }
        this.transacoes.add(new Transacao(request));
        logger.info("Transação {} foi adicionada", request);
    }

    public void delete(){
        this.transacoes.clear();
        logger.info("Lista de transações apagada");
    }

    public Estatistica getStatistics(Integer time){
        long startTime = System.currentTimeMillis();

        OffsetDateTime limit = OffsetDateTime.now().minus(Duration.ofSeconds(time));
        DoubleSummaryStatistics statistics = new DoubleSummaryStatistics();

        if (transacoes.isEmpty()){
            logger.info("Lista de transações está vazia, retornando estatísticas nulas");
            return new Estatistica(0,0,0,0,0);
        }

        transacoes.stream()
                .filter(transacao -> transacao.getDataHora().isAfter(limit))
                .map(Transacao::getValor)
                .forEach(statistics::accept);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        logger.info("Retornando estatisticas, tempo: {} ms", duration);
        return new Estatistica(statistics);
    }
}
