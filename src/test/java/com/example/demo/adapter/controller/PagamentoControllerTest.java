package com.example.demo.adapter.controller;

import com.example.demo.adapter.controller.request.pagamento.PagamentoRequest;
import com.example.demo.adapter.gateway.interfaces.pagamento.BuscarPagamentoAdapterPort;
import com.example.demo.core.domain.Pagamento;
import com.example.demo.core.usecase.interfaces.pagamento.PagarPedidoUseCasePort;
import com.example.demo.core.usecase.interfaces.pagamento.ValidarPagamentoPedidoUseCasePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class PagamentoControllerTest {

    @Mock
    private PagarPedidoUseCasePort pagarPedidoUseCasePort;

    @Mock
    private ValidarPagamentoPedidoUseCasePort validarPagamentoPedidoUseCasePort;

    @Mock
    private BuscarPagamentoAdapterPort buscarPagamentoAdapterPort;

    @InjectMocks
    private PagamentoController pagamentoController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void realizarPagamento_DeveRetornarStatusCreated() {

        when(pagarPedidoUseCasePort.checkout(any(Pagamento.class)))
                .thenReturn(new Pagamento());

        ResponseEntity<?> responseEntity = pagamentoController.realizarPagamento(any(PagamentoRequest.class));

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void consultaStatusPagamento_DeveRetornarStatusOK() {

        when(buscarPagamentoAdapterPort.buscar(anyLong()))
                .thenReturn(new Pagamento());

        ResponseEntity<?> responseEntity = pagamentoController.consultaStatusPagamento(anyLong());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    void recebeConfirmacaoDePagamentoWebhook_DeveRetornarStatusCreated() {

        ResponseEntity<?> responseEntity = pagamentoController.recebeConfirmacaoDePagamentoWebhook(any());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

}