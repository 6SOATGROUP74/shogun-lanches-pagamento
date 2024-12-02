package com.example.demo.config;


import com.example.demo.adapter.gateway.interfaces.pagamento.BuscarPagamentoAdapterPort;
import com.example.demo.adapter.gateway.interfaces.pagamento.SalvarPagamentoAdapterPort;
import com.example.demo.infrastructure.integration.pagbank.PagarPedidoPagbankAdapter;
import com.example.demo.infrastructure.integration.pagbank.ProcessaStatusPagamentoPagbankAdapter;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BeanConfigTest {

    private BeanConfig beanConfig;

    @Mock
    private PagarPedidoPagbankAdapter pagarPedidoPagbankAdapter;

    @Mock
    private SalvarPagamentoAdapterPort salvarPagamentoAdapterPort;

    @Mock
    private ProcessaStatusPagamentoPagbankAdapter processaStatusPagamentoPagbankAdapter;

    @Mock
    private BuscarPagamentoAdapterPort buscarPagamentoAdapterPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        beanConfig = new BeanConfig();
    }

    @Test
    void pagarPedidoUseCasePort_shouldReturnNotNull() {
        var pagarPedidoUseCasePort = beanConfig.pagarPedidoUseCasePort(pagarPedidoPagbankAdapter, salvarPagamentoAdapterPort);
        assertNotNull(pagarPedidoUseCasePort, "pagarPedidoUseCasePort bean should not be null");
    }

    @Test
    void alterarStatusPagamentoUseCasePort_shouldReturnNotNull() {
        var alterarStatusPagamentoUseCasePort = beanConfig.alterarStatusPagamentoUseCasePort(
                processaStatusPagamentoPagbankAdapter,
                buscarPagamentoAdapterPort,
                salvarPagamentoAdapterPort
        );
        assertNotNull(alterarStatusPagamentoUseCasePort, "alterarStatusPagamentoUseCasePort bean should not be null");
    }

    @Test
    void validarPagamentoPedidoUseCasePort_shouldReturnNotNull() {
        var validarPagamentoPedidoUseCasePort = beanConfig.validarPagamentoPedidoUseCasePort(
                salvarPagamentoAdapterPort,
                buscarPagamentoAdapterPort
        );
        assertNotNull(validarPagamentoPedidoUseCasePort, "validarPagamentoPedidoUseCasePort bean should not be null");
    }

}