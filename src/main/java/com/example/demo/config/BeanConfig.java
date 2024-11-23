package com.example.demo.config;

import com.example.demo.adapter.gateway.interfaces.pagamento.BuscarPagamentoAdapterPort;
import com.example.demo.adapter.gateway.interfaces.pagamento.SalvarPagamentoAdapterPort;
import com.example.demo.adapter.gateway.interfaces.producao.EnviaPedidoParaProducaoAdapterPort;
import com.example.demo.core.usecase.impl.AlterarStatusPagamentoUseCase;
import com.example.demo.core.usecase.impl.PagarPedidoUseCase;
import com.example.demo.core.usecase.impl.ValidarPagamentoPedidoUseCase;
import com.example.demo.core.usecase.interfaces.pagamento.AlterarStatusPagamentoUseCasePort;
import com.example.demo.core.usecase.interfaces.pagamento.PagarPedidoUseCasePort;
import com.example.demo.core.usecase.interfaces.pagamento.ValidarPagamentoPedidoUseCasePort;
import com.example.demo.infrastructure.integration.pagbank.PagarPedidoPagbankAdapter;
import com.example.demo.infrastructure.integration.pagbank.ProcessaStatusPagamentoPagbankAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public PagarPedidoUseCasePort pagarPedidoUseCasePort(PagarPedidoPagbankAdapter pagarPedidoPagbankAdapter,
                                                         SalvarPagamentoAdapterPort salvarPagamentoAdapterPort) {
        return new PagarPedidoUseCase(pagarPedidoPagbankAdapter, salvarPagamentoAdapterPort);
    }

    @Bean
    public AlterarStatusPagamentoUseCasePort alterarStatusPagamentoUseCasePort(ProcessaStatusPagamentoPagbankAdapter processaStatusPagamentoPagbankAdapter, BuscarPagamentoAdapterPort buscarPagamentoAdapterPort, SalvarPagamentoAdapterPort salvarPagamentoAdapterPort) {
        return new AlterarStatusPagamentoUseCase(processaStatusPagamentoPagbankAdapter, buscarPagamentoAdapterPort, salvarPagamentoAdapterPort);
    }

    @Bean
    public ValidarPagamentoPedidoUseCasePort validarPagamentoPedidoUseCasePort(SalvarPagamentoAdapterPort salvarPagamentoAdapterPort, BuscarPagamentoAdapterPort buscarPagamentoAdapterPort) {
        return new ValidarPagamentoPedidoUseCase(buscarPagamentoAdapterPort, salvarPagamentoAdapterPort);
    }
}
