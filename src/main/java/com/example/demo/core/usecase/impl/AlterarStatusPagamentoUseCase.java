package com.example.demo.core.usecase.impl;


import com.example.demo.adapter.gateway.interfaces.pagamento.BuscarPagamentoAdapterPort;
import com.example.demo.adapter.gateway.interfaces.pagamento.SalvarPagamentoAdapterPort;
import com.example.demo.core.domain.Pagamento;
import static com.example.demo.core.domain.StatusPagamento.APROVADO;
import com.example.demo.core.usecase.interfaces.pagamento.AlterarStatusPagamentoUseCasePort;
import com.example.demo.exceptions.PagamentoNotFoundException;
import com.example.demo.infrastructure.integration.pagbank.ProcessaStatusPagamentoPagbankAdapter;

import java.util.Objects;

public class AlterarStatusPagamentoUseCase implements AlterarStatusPagamentoUseCasePort {

    private final ProcessaStatusPagamentoPagbankAdapter processaStatusPagamentoPagbankAdapter;
    private final BuscarPagamentoAdapterPort buscarPagamentoAdapterPort;
    private final SalvarPagamentoAdapterPort salvarPagamentoAdapterPort;

    public AlterarStatusPagamentoUseCase(ProcessaStatusPagamentoPagbankAdapter processaStatusPagamentoPagbankAdapter, BuscarPagamentoAdapterPort buscarPagamentoAdapterPort, SalvarPagamentoAdapterPort salvarPagamentoAdapterPort) {
        this.processaStatusPagamentoPagbankAdapter = processaStatusPagamentoPagbankAdapter;
        this.buscarPagamentoAdapterPort = buscarPagamentoAdapterPort;
        this.salvarPagamentoAdapterPort = salvarPagamentoAdapterPort;
    }

    @Override
    public Pagamento execute(Long pagamentoId) {
        Pagamento pagamentoAtual = buscarPagamentoAdapterPort.buscar(pagamentoId);

        if(Objects.isNull(pagamentoAtual)){
            throw new PagamentoNotFoundException("Pagamento não encontrado");
        }

        final Pagamento pagamentoProcessado = processaStatusPagamentoPagbankAdapter.execute(pagamentoAtual);
        pagamentoProcessado.setStatus(APROVADO.name());

        salvarPagamentoAdapterPort.salvar(pagamentoProcessado);

        return pagamentoProcessado;
    }
}
