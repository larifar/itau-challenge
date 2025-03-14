package challenge.itau;

import challenge.itau.dto.Estatistica;
import challenge.itau.dto.TransacaoException;
import challenge.itau.dto.TransacaoRequest;
import challenge.itau.service.TransacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransacaoServiceTests {
    @Autowired
    private TransacaoService transacaoService;
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private MockMvc mockMvc;

    @Test
    void service_when_add_success(){
        transacaoService.delete();
        TransacaoRequest request = new TransacaoRequest(100, OffsetDateTime.now().minusMinutes(1));

        transacaoService.add(request);

        assertFalse(transacaoService.getTransacoes().isEmpty());
        assertEquals(1, transacaoService.getTransacoes().size());
        assertEquals(100, transacaoService.getTransacao(0).getValor());
    }

    @Test
    void service_when_add_negative_value(){
        TransacaoRequest request = new TransacaoRequest(-1, OffsetDateTime.now().minusMinutes(1));

        assertThrows(TransacaoException.class, () -> {
            transacaoService.add(request);
        });
    }

    @Test
    void service_when_add_future_date(){
        TransacaoRequest request = new TransacaoRequest(-1, OffsetDateTime.now().plusDays(1));

        assertThrows(TransacaoException.class, () -> {
            transacaoService.add(request);
        });
    }

    @Test
    void service_delete_clear_list(){
        transacaoService.delete();
        assertTrue(transacaoService.getTransacoes().isEmpty());
    }

    @Test
    void service_getStats_when_list_has_transactions(){
        transacaoService.delete();

        transacaoService.add(new TransacaoRequest(10, OffsetDateTime.now().minusSeconds(30)));
        transacaoService.add(new TransacaoRequest(20, OffsetDateTime.now().minusSeconds(20)));
        transacaoService.add(new TransacaoRequest(30, OffsetDateTime.now().minusSeconds(10)));

        Estatistica estatistica = transacaoService.getStatistics(60);

       assertEquals(60, estatistica.sum());
        assertEquals(20, estatistica.avg());
        assertEquals(10, estatistica.min());
        assertEquals(30, estatistica.max());
        assertEquals(3, estatistica.count());
    }

    @Test
    void service_getStats_when_list_is_empty(){
        transacaoService.delete();

        Estatistica estatistica = transacaoService.getStatistics(60);

        assertEquals(0, estatistica.sum());
        assertEquals(0, estatistica.avg());
        assertEquals(0, estatistica.min());
        assertEquals(0, estatistica.max());
        assertEquals(0, estatistica.count());
    }
}
