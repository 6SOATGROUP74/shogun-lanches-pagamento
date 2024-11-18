package com.example.demo.core.usecase.impl;

import com.example.demo.adapter.gateway.interfaces.pagamento.BuscarPagamentoAdapterPort;
import com.example.demo.adapter.gateway.interfaces.pagamento.SalvarPagamentoAdapterPort;
import com.example.demo.core.domain.Pagamento;
import com.example.demo.core.domain.StatusPagamento;
import com.example.demo.core.usecase.interfaces.pagamento.ValidarPagamentoPedidoUseCasePort;
import com.example.demo.exceptions.PagamentoNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ValidarPagamentoPedidoUseCase implements ValidarPagamentoPedidoUseCasePort {


    private final BuscarPagamentoAdapterPort buscarPagamentoAdapterPort;
    private final SalvarPagamentoAdapterPort salvarPagamentoAdapterPort;

    public ValidarPagamentoPedidoUseCase(BuscarPagamentoAdapterPort buscarPagamentoAdapterPort, SalvarPagamentoAdapterPort salvarPagamentoAdapterPort) {
        this.buscarPagamentoAdapterPort = buscarPagamentoAdapterPort;
        this.salvarPagamentoAdapterPort = salvarPagamentoAdapterPort;
    }

    @Override
    public Pagamento execute(Pagamento pagamento) {

        Pagamento pagamentoAtualizado = buscarPagamentoAdapterPort.buscar(pagamento.getIdPagamento());

        if(Objects.isNull(pagamentoAtualizado)){
            throw new PagamentoNotFoundException("Pagamento nao localizado.");
        }

        pagamentoAtualizado.setstatusDoPagamento(StatusPagamento.APROVADO.name());
        pagamentoAtualizado.setDataPagamento(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        final var dadosPagamento = salvarPagamentoAdapterPort.salvar(pagamentoAtualizado);

        return dadosPagamento;
    }
}
