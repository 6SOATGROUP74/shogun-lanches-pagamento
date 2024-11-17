package com.example.demo.adapter.gateway.interfaces.impl;

import com.example.demo.core.domain.Pagamento;
import com.example.demo.infrastructure.repository.PagamentoRepository;
import com.example.demo.infrastructure.repository.entity.PagamentoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class SalvarPagamentoAdapterTest {

    @Mock
    private PagamentoRepository repository;

    @InjectMocks
    private SalvarPagamentoAdapter salvarPagamentoAdapter;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvar_NaoDeveRetornarException() {
        when(repository.save(any(PagamentoEntity.class)))
                .thenReturn(new PagamentoEntity());

        assertDoesNotThrow(() -> salvarPagamentoAdapter.salvar(any(Pagamento.class)));
    }
}