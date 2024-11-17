//package com.example.demo.core.usecase.impl;
//
//import com.example.demo.adapter.gateway.interfaces.pagamento.BuscarPagamentoAdapterPort;
//import com.example.demo.adapter.gateway.interfaces.pagamento.SalvarPagamentoAdapterPort;
//import com.example.demo.adapter.gateway.interfaces.pedido.BuscarPedidoAdapterPort;
//import com.example.demo.core.domain.Pagamento;
//import com.example.demo.core.domain.Pedido;
//import com.example.demo.exceptions.PagamentoNotFoundException;
//import com.example.demo.infrastructure.integration.pagbank.ProcessaStatusPagamentoPagbankAdapter;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//class AlterarStatusPagamentoUseCaseTest {
//
//    @Mock
//    private ProcessaStatusPagamentoPagbankAdapter processaStatusPagamentoPagbankAdapter;
//
//    @Mock
//    private BuscarPagamentoAdapterPort buscarPagamentoAdapterPort;
//
//    @Mock
//    private SalvarPagamentoAdapterPort salvarPagamentoAdapterPort;
//
//    @Mock
//    private BuscarPedidoAdapterPort buscarPedidoAdapterPort;
//
//    @InjectMocks
//    AlterarStatusPagamentoUseCase alterarStatusPagamentoUseCase;
//
//    @BeforeEach
//    void setup(){
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void execute_DeveLancarExceptionPagamentoNotFound(){
//
//        when(buscarPagamentoAdapterPort.buscar(anyLong())).thenReturn(null);
//
//        assertThrows(PagamentoNotFoundException.class, ()-> alterarStatusPagamentoUseCase.execute(anyLong()));
//
//    }
//
//    @Test
//    void execute_DeveRetornarUmPagamentoValido(){
//
//        Pagamento pagamento = new Pagamento();
//        pagamento.setNumeroPedido(1L);
//
//        Pedido pedido = CommonsMock.buildPedido();
//
//        when(buscarPagamentoAdapterPort.buscar(anyLong())).thenReturn(pagamento);
//        when(buscarPedidoAdapterPort.execute(eq(pagamento.getNumeroPedido()))).thenReturn(pedido);
//
//        Pagamento pagamentoProcessado = new Pagamento();
//
//        when(processaStatusPagamentoPagbankAdapter.execute(eq(pagamento))).thenReturn(pagamentoProcessado);
//
//        assertNotNull(alterarStatusPagamentoUseCase.execute(anyLong()));
//
//        verify(salvarPagamentoAdapterPort, times(1)).salvar(eq(pagamentoProcessado));
//
//    }
//
//}