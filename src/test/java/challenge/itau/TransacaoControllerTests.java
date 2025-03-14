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
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TransacaoControllerTests {
    @MockitoBean
    private TransacaoService transacaoService;
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private MockMvc mockMvc;

    @Test
    void endpoint_transacao_post_return_201() throws Exception {
        TransacaoRequest request = new TransacaoRequest(100, OffsetDateTime.now());

        mockMvc.perform(MockMvcRequestBuilders.post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void endpoint_transacao_post_error_return_422() throws Exception {
        TransacaoRequest request = new TransacaoRequest(0, OffsetDateTime.now());

        doThrow(new TransacaoException("Valor inválido")).when(transacaoService).add(any(TransacaoRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());

    }

    @Test
    void endpoint_transacao_post_error_return_400() throws Exception {
        TransacaoRequest request = new TransacaoRequest(10, OffsetDateTime.now());
        doThrow(new RuntimeException()).when(transacaoService).add(any(TransacaoRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void endpoint_transacao_delete_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/transacao"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void endpoint_estatistica_get_200() throws Exception {
        int seconds = 60;
        Estatistica stats = new Estatistica(10, 100, 5, 2, 20);

        when(transacaoService.getStatistics(seconds)).thenReturn(stats);

        mockMvc.perform(MockMvcRequestBuilders.get("/estatistica/{seconds}", seconds)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.sum").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.avg").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.min").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.max").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(10));

    }

}
