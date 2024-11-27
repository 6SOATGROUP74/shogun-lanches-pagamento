package com.example.demo.core.usecase.impl;

import com.example.demo.adapter.gateway.interfaces.pagamento.BuscarPagamentoAdapterPort;
import com.example.demo.adapter.gateway.interfaces.pagamento.SalvarPagamentoAdapterPort;
import com.example.demo.core.domain.Pagamento;
import com.example.demo.core.domain.StatusPagamento;
import com.example.demo.exceptions.PagamentoNotFoundException;
import com.example.demo.infrastructure.integration.pagbank.ProcessaStatusPagamentoPagbankAdapter;
import com.example.demo.mocks.PagamentoMockBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AlterarStatusPagamentoUseCaseTest {

    ProcessaStatusPagamentoPagbankAdapter processaStatusPagamentoPagbankAdapter = mock(ProcessaStatusPagamentoPagbankAdapter.class);
    BuscarPagamentoAdapterPort buscarPagamentoAdapterPort = mock(BuscarPagamentoAdapterPort.class);
    SalvarPagamentoAdapterPort salvarPagamentoAdapterPort = mock(SalvarPagamentoAdapterPort.class);
    AlterarStatusPagamentoUseCase alterarStatusPagamentoUseCase = new AlterarStatusPagamentoUseCase(processaStatusPagamentoPagbankAdapter, buscarPagamentoAdapterPort, salvarPagamentoAdapterPort);

    @Test
    public void deveAlterarStatusPagamentoComSucesso() {
        Long pagamentoId = 1L;
        Pagamento pagamento = PagamentoMockBuilder.builder().idPagamento(pagamentoId).build();
        Pagamento pagamentoProcessado = PagamentoMockBuilder.builder()
                .idPagamento(pagamentoId)
                .statusDoPagamento(StatusPagamento.APROVADO.name())
                .build();

        when(buscarPagamentoAdapterPort.buscar(pagamentoId)).thenReturn(pagamento);
        when(processaStatusPagamentoPagbankAdapter.execute(pagamento)).thenReturn(pagamentoProcessado);
        when(salvarPagamentoAdapterPort.salvar(pagamentoProcessado)).thenReturn(pagamentoProcessado);

        alterarStatusPagamentoUseCase.execute(pagamentoId);

        verify(buscarPagamentoAdapterPort, times(1)).buscar(anyLong());
        verify(processaStatusPagamentoPagbankAdapter, times(1)).execute(pagamento);
        verify(salvarPagamentoAdapterPort, times(1)).salvar(any());
    }

    @Test
    public void deveLancarExecessaoAoNaoEncontrarPagamento() {
        Long pagamentoId = 1L;
        Pagamento pagamento = PagamentoMockBuilder.builder().idPagamento(pagamentoId).build();
        Pagamento pagamentoProcessado = PagamentoMockBuilder.builder()
                .idPagamento(pagamentoId)
                .statusDoPagamento(StatusPagamento.APROVADO.name())
                .build();

        when(buscarPagamentoAdapterPort.buscar(pagamentoId)).thenReturn(null);

        Pagamento result = null;
        try {
            result = alterarStatusPagamentoUseCase.execute(pagamentoId);
            Assertions.fail();
        } catch (PagamentoNotFoundException e) {
            Assertions.assertNull(result);
        }

        verify(buscarPagamentoAdapterPort, times(1)).buscar(anyLong());
        verify(processaStatusPagamentoPagbankAdapter, times(0)).execute(pagamento);
        verify(salvarPagamentoAdapterPort, times(0)).salvar(any());
    }
}