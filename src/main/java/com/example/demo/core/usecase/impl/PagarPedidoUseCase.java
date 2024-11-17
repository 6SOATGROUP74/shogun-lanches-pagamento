package com.example.demo.core.usecase.impl;

import com.example.demo.adapter.gateway.interfaces.pagamento.SalvarPagamentoAdapterPort;
import com.example.demo.core.domain.Pagamento;
import com.example.demo.core.usecase.interfaces.pagamento.PagarPedidoUseCasePort;
import com.example.demo.infrastructure.integration.pagbank.PagarPedidoPagbankAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PagarPedidoUseCase implements PagarPedidoUseCasePort {

    private final PagarPedidoPagbankAdapter pagarPedidoPagbankAdapter;
    private final SalvarPagamentoAdapterPort salvarPagamentoAdapterPort;

    public PagarPedidoUseCase(PagarPedidoPagbankAdapter pagarPedidoPagbankAdapter, SalvarPagamentoAdapterPort salvarPagamentoAdapterPort) {
        this.pagarPedidoPagbankAdapter = pagarPedidoPagbankAdapter;
        this.salvarPagamentoAdapterPort = salvarPagamentoAdapterPort;
    }

    @Override
    public Pagamento checkout(Pagamento pagamento) {

        logger.info("m=checkout, status=init,  msg=Iniciando processo de checkout, pagamento={}", pagamento);


        final Pagamento pagamentoProcessado = pagarPedidoPagbankAdapter.pagar(pagamento);



        final var dadosPagamento = salvarPagamentoAdapterPort.salvar(pagamentoProcessado);


        logger.info("m=checkout, status=succes,  msg=Checkout realizado com sucesso, pagamento={}, pedido={}", dadosPagamento);

        return pagamento;
    }

    private Logger logger = LoggerFactory.getLogger(PagarPedidoUseCase.class);
}
