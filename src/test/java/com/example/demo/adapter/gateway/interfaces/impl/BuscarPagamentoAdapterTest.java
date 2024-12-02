package com.example.demo.adapter.gateway.interfaces.impl;

import com.example.demo.core.domain.Pagamento;
import com.example.demo.infrastructure.repository.PagamentoRepository;
import com.example.demo.infrastructure.repository.entity.PagamentoEntity;
import static com.example.demo.mocks.PagamentoHelper.convertePagamentoParaPagamentoEntity;
import com.example.demo.mocks.PagamentoMockBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;


class BuscarPagamentoAdapterTest {

    PagamentoRepository pagamentoRepository = mock(PagamentoRepository.class);
    BuscarPagamentoAdapter buscarPagamentoAdapter = new BuscarPagamentoAdapter(pagamentoRepository);


    @Test
    public void deveBuscarPagamentoComSucesso(){
        Long pagamentoID = 1L;
        Pagamento pagamento = PagamentoMockBuilder.builder().idPagamento(pagamentoID).build();
        PagamentoEntity pagamentoEntity = convertePagamentoParaPagamentoEntity(pagamento);

        when(pagamentoRepository.findById(pagamentoID)).thenReturn(Optional.of(pagamentoEntity));

        buscarPagamentoAdapter.buscar(pagamentoID);

        verify(pagamentoRepository, times(1)).findById(anyLong());
    }

    @Test
    public void deveLancarExeceptionQuandoNaoEncontrarPagamento(){
        Long pagamentoID = 1L;

        when(pagamentoRepository.findById(pagamentoID)).thenReturn(null);

        Pagamento pagamento = null;
        try {
            pagamento = buscarPagamentoAdapter.buscar(pagamentoID);
            Assertions.fail();
        } catch (Exception e) {
            verify(pagamentoRepository, times(1)).findById(anyLong());
            Assertions.assertNull(pagamento);
        }
    }
}