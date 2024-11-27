package com.example.demo.core.usecase.impl;


import com.example.demo.adapter.gateway.interfaces.pagamento.SalvarPagamentoAdapterPort;
import com.example.demo.core.domain.Pagamento;
import com.example.demo.core.domain.StatusPagamento;
import com.example.demo.infrastructure.integration.pagbank.PagarPedidoPagbankAdapter;
import com.example.demo.mocks.PagamentoMockBuilder;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PagarPedidoUseCaseTest {

    PagarPedidoPagbankAdapter pagarPedidoPagbankAdapter = mock(PagarPedidoPagbankAdapter.class);
    SalvarPagamentoAdapterPort salvarPagamentoAdapterPort = mock(SalvarPagamentoAdapterPort.class);
    PagarPedidoUseCase pagarPedidoUseCase = new PagarPedidoUseCase(pagarPedidoPagbankAdapter, salvarPagamentoAdapterPort);

    @Test
    public void deveRealizarPagamentoViaPagbankComSucesso() {
        Pagamento pagamento = PagamentoMockBuilder.builder().build();
        Pagamento pagamentoProcessado = PagamentoMockBuilder.builder()
                .statusDoPagamento(StatusPagamento.PENDENTE.name())
                .build();

        when(pagarPedidoPagbankAdapter.pagar(pagamento)).thenReturn(pagamentoProcessado);
        when(salvarPagamentoAdapterPort.salvar(pagamentoProcessado)).thenReturn(pagamentoProcessado);

        pagarPedidoUseCase.checkout(pagamento);

        verify(pagarPedidoPagbankAdapter, times(1)).pagar(any());
        verify(salvarPagamentoAdapterPort, times(1)).salvar(any());
    }
}