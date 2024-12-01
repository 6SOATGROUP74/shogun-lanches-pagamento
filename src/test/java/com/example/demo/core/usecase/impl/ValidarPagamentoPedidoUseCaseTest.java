package com.example.demo.core.usecase.impl;

import com.example.demo.adapter.gateway.interfaces.pagamento.BuscarPagamentoAdapterPort;
import com.example.demo.adapter.gateway.interfaces.pagamento.SalvarPagamentoAdapterPort;
import com.example.demo.core.domain.Pagamento;
import com.example.demo.core.domain.StatusPagamento;
import com.example.demo.exceptions.PagamentoNotFoundException;
import com.example.demo.mocks.PagamentoMockBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ValidarPagamentoPedidoUseCaseTest {

    BuscarPagamentoAdapterPort buscarPagamentoAdapterPort = mock(BuscarPagamentoAdapterPort.class);
    SalvarPagamentoAdapterPort salvarPagamentoAdapterPort = mock(SalvarPagamentoAdapterPort.class);
    ValidarPagamentoPedidoUseCase validarPagamentoPedidoUseCase = new ValidarPagamentoPedidoUseCase(buscarPagamentoAdapterPort, salvarPagamentoAdapterPort);

    @Test
    public void deveValidarPagamentoNoPagbankComSucesso(){
        Long pagamentoID = 1L;
        Pagamento pagamento = PagamentoMockBuilder.builder().idPagamento(pagamentoID).build();
        Pagamento pagamentoAtualizado = PagamentoMockBuilder.builder()
                .idPagamento(pagamentoID)
                .statusDoPagamento(StatusPagamento.APROVADO.name())
                .build();

        when(buscarPagamentoAdapterPort.buscar(pagamentoID)).thenReturn(pagamento);
        when(salvarPagamentoAdapterPort.salvar(pagamentoAtualizado)).thenReturn(pagamentoAtualizado);

        validarPagamentoPedidoUseCase.execute(pagamento);

        verify(buscarPagamentoAdapterPort, times(1)).buscar(anyLong());
        verify(salvarPagamentoAdapterPort, times(1)).salvar(any());
    }

    @Test
    public void deveLancarExcessaoQuandoNaoEncontrarPagamento(){
        Long pagamentoID = 1L;
        Pagamento pagamento = PagamentoMockBuilder.builder().idPagamento(pagamentoID).build();
        Pagamento pagamentoAtualizado = PagamentoMockBuilder.builder()
                .idPagamento(pagamentoID)
                .statusDoPagamento(StatusPagamento.APROVADO.name())
                .build();

        Pagamento result = null;
        try {
           result = validarPagamentoPedidoUseCase.execute(pagamento);
            Assertions.fail();
        } catch (PagamentoNotFoundException e) {
            Assertions.assertNull(result);
        }

        verify(buscarPagamentoAdapterPort, times(1)).buscar(anyLong());
        verify(salvarPagamentoAdapterPort, times(0)).salvar(any());
    }

}