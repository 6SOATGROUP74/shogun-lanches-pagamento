package com.example.demo.adapter.gateway.interfaces.impl;

import com.example.demo.core.domain.Pagamento;
import com.example.demo.infrastructure.repository.PagamentoRepository;
import com.example.demo.infrastructure.repository.entity.PagamentoEntity;
import static com.example.demo.mocks.PagamentoHelper.convertePagamentoParaPagamentoEntity;
import com.example.demo.mocks.PagamentoMockBuilder;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SalvarPagamentoAdapterTest {

    PagamentoRepository pagamentoRepository = mock(PagamentoRepository.class);
    SalvarPagamentoAdapter salvarPagamentoAdapter = new SalvarPagamentoAdapter(pagamentoRepository);

    @Test
    public void deveSalvarPagamentoComSucesso() {
        Pagamento pagamento = PagamentoMockBuilder.builder().build();
        PagamentoEntity pagamentoEntity = convertePagamentoParaPagamentoEntity(pagamento);

        when(pagamentoRepository.save(pagamentoEntity)).thenReturn(pagamentoEntity);

        salvarPagamentoAdapter.salvar(pagamento);

        verify(pagamentoRepository, times(1)).save(any());
    }
}