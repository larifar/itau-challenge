package challenge.itau.controller;

import challenge.itau.dto.Estatistica;
import challenge.itau.dto.TransacaoRequest;
import challenge.itau.service.TransacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransacaoController {
    private final TransacaoService service;
    private static final Logger logger = LoggerFactory.getLogger(TransacaoController.class);

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @PostMapping("/transacao")
    public ResponseEntity<Void> save(@RequestBody TransacaoRequest request) {
        logger.info("Recebendo transação: {}", request);
        service.add(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/transacao")
    public ResponseEntity<Void> delete() {
        service.delete();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/estatistica/{seconds}")
    public ResponseEntity<Estatistica> getStatistics(@PathVariable Integer seconds) {
        return new ResponseEntity<>(service.getStatistics(seconds), HttpStatus.OK);
    }
}
