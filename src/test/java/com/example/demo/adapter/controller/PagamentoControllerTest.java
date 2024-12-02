package com.example.demo.adapter.controller;

import com.example.demo.adapter.gateway.interfaces.pagamento.BuscarPagamentoAdapterPort;
import com.example.demo.core.domain.FormasPagamentoEnum;
import com.example.demo.core.domain.Pagamento;
import com.example.demo.core.usecase.interfaces.pagamento.AlterarStatusPagamentoUseCasePort;
import com.example.demo.core.usecase.interfaces.pagamento.PagarPedidoUseCasePort;
import com.example.demo.core.usecase.interfaces.pagamento.ValidarPagamentoPedidoUseCasePort;
import com.example.demo.exceptions.PagamentoNotFoundException;
import com.example.demo.infrastructure.integration.pagbank.request.PagbankWebhookRequest;
import static com.example.demo.mocks.PagamentoHelper.gerarPagamentoRequest;
import static com.example.demo.mocks.PagamentoHelper.gerarPagbankWebhookRequest;
import com.example.demo.mocks.PagamentoMockBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class PagamentoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PagarPedidoUseCasePort pagarPedidoUseCasePort;

    @Mock
    private ValidarPagamentoPedidoUseCasePort validarPagamentoPedidoUseCasePort;

    @Mock
    private BuscarPagamentoAdapterPort buscarPagamentoAdapterPort;

    @Mock
    private AlterarStatusPagamentoUseCasePort alterarStatusPagamentoUseCasePort;

    AutoCloseable opeMocks;

    @BeforeEach
    void setup() {
        opeMocks = MockitoAnnotations.openMocks(this);
        PagamentoController pagamentoController = new PagamentoController(pagarPedidoUseCasePort, validarPagamentoPedidoUseCasePort, buscarPagamentoAdapterPort, alterarStatusPagamentoUseCasePort);
        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController)
                .setControllerAdvice(new CustomExceptionHandlers())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        opeMocks.close();
    }

    @Test
    void deveRealizarPagamento() throws Exception {
        var pagamentoRequest = gerarPagamentoRequest();
        when(pagarPedidoUseCasePort.checkout(any(Pagamento.class)))
                .thenAnswer(i -> i.getArgument(0));

        mockMvc.perform(post("/v1/pagamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pagamentoRequest)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(pagarPedidoUseCasePort, times(1)).checkout(any(Pagamento.class));
    }

    @Test
    void naoDeveRealizarPagamento() throws Exception {
        var pagamentoRequest = gerarPagamentoRequest();
        when(pagarPedidoUseCasePort.checkout(any())).thenThrow(PagamentoNotFoundException.class);

        mockMvc.perform(post("/v1/pagamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pagamentoRequest)))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(pagarPedidoUseCasePort, times(1)).checkout(any(Pagamento.class));
    }

    @Test
    void deveConsultarStatusDoPagamento() throws Exception {
        Long pagamentoId = 20L;
        Pagamento pagamento = pagamentoPendente(pagamentoId);
        when(buscarPagamentoAdapterPort.buscar(any())).thenReturn(pagamento);

        mockMvc.perform(get("/v1/pagamento/{pagamentoId}", pagamentoId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        verify(buscarPagamentoAdapterPort, times(1)).buscar(any());
    }

    @Test
    void deveLancarExcessaoAoConsultarStatusDoPagamento() throws Exception {
        Long pagamentoId = 20L;
        when(buscarPagamentoAdapterPort.buscar(pagamentoId)).thenThrow(PagamentoNotFoundException.class);

        mockMvc.perform(get("/v1/pagamento/{pagamentoId}", pagamentoId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(buscarPagamentoAdapterPort, times(1)).buscar(any());
    }

    @Test
    void deveConfirmarPagamento() throws Exception {
        Long pagamentoId = 20L;
        Pagamento pagamentoPendente = pagamentoPendente(pagamentoId);
        Pagamento pagamentoAprovado = pagamentoAprovado(pagamentoId);

        when(buscarPagamentoAdapterPort.buscar(any())).thenReturn(pagamentoPendente);
        when(validarPagamentoPedidoUseCasePort.execute(pagamentoPendente)).thenReturn(pagamentoAprovado);

        mockMvc.perform(post("/v1/pagamento/confirma-pagamento/{pagamentoId}", pagamentoId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());

        verify(buscarPagamentoAdapterPort, times(1)).buscar(any());
        verify(validarPagamentoPedidoUseCasePort, times(1)).execute(any());
    }

    @Test
    void deveReceberConfirmacaoDePagamentoViaWebhookDoPagamento() throws Exception {
        Long pagamentoId = 20L;
        PagbankWebhookRequest pagbankWebhookRequest = gerarPagbankWebhookRequest();
        Pagamento pagamentoAprovado = pagamentoAprovado(pagamentoId);

        when(alterarStatusPagamentoUseCasePort.execute(pagamentoId)).thenReturn(pagamentoAprovado);

        mockMvc.perform(post("/v1/pagamento/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pagbankWebhookRequest))
                )
                .andDo(print())
                .andExpect(status().isCreated());

        verify(alterarStatusPagamentoUseCasePort, times(1)).execute(any());
    }

    private Pagamento pagamentoPendente(Long pagamentoId) {
        return PagamentoMockBuilder.builder()
                .idPagamento(pagamentoId)
                .numeroPedido(1L)
                .statusDoPagamento("PENDENTE")
                .valorTotal(BigDecimal.valueOf(17.99))
                .tipoDoPamento(FormasPagamentoEnum.QR_CODE_PAGBANK.name())
                .dataPagamento(LocalDateTime.now().toString())
                .codPagamento("ORDER_123")
                .copiaCola("pagbak@bc.com")
                .qrCodeLink("bc@pagbank.com")
                .build();
    }

    private Pagamento pagamentoAprovado(Long pagamentoId) {
        return PagamentoMockBuilder.builder()
                .idPagamento(pagamentoId)
                .numeroPedido(1L)
                .statusDoPagamento("APROVADO")
                .valorTotal(BigDecimal.valueOf(17.99))
                .tipoDoPamento(FormasPagamentoEnum.QR_CODE_PAGBANK.name())
                .dataPagamento(LocalDateTime.now().toString())
                .codPagamento("ORDER_123")
                .copiaCola("pagbak@bc.com")
                .qrCodeLink("bc@pagbank.com")
                .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}