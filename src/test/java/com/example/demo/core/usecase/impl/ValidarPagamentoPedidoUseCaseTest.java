//package com.example.demo.core.usecase.impl;
//
//import com.example.demo.adapter.gateway.interfaces.pagamento.BuscarPagamentoAdapterPort;
//import com.example.demo.adapter.gateway.interfaces.pagamento.SalvarPagamentoAdapterPort;
//import com.example.demo.adapter.gateway.interfaces.pedido.AtualizarPedidoAdapterPort;
//import com.example.demo.core.domain.Pagamento;
//import com.example.demo.core.domain.Pedido;
//import com.example.demo.core.usecase.interfaces.pedido.ListarPedidosUseCasePort;
//import com.example.demo.exceptions.PagamentoNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//class ValidarPagamentoPedidoUseCaseTest {
//
//    @Mock
//    private ListarPedidosUseCasePort listarPedidosUseCasePort;
//
//    @Mock
//    private BuscarPagamentoAdapterPort buscarPagamentoAdapterPort;
//
//    @Mock
//    private SalvarPagamentoAdapterPort salvarPagamentoAdapterPort;
//
//    @Mock
//    private AtualizarPedidoAdapterPort atualizarPedidoAdapterPort;
//
//    @InjectMocks
//    ValidarPagamentoPedidoUseCase validarPagamentoPedidoUseCase;
//
//    @BeforeEach
//    void setup(){
//        MockitoAnnotations.openMocks(this);
//    }
//
//
//    @Test
//    void execute_DeveLancarPedidoNotFoundException(){
//
//        when(listarPedidosUseCasePort.listarPorCodReferencia(
//                eq(buildPagamento().getPedido().getCodReferenciaPedido())))
//                .thenReturn(null);
//
//        assertThrows(PedidoNotFoundException.class, () -> validarPagamentoPedidoUseCase.execute(buildPagamento()));
//    }
//
//    @Test
//    void execute_DeveLancarPagamentoNotFoundException(){
//
//        Pagamento pagamento = buildPagamento();
//        Pedido pedidoResponse = new Pedido();
//        pedidoResponse.setIdPagamento(1L);
//
//        when(listarPedidosUseCasePort.listarPorCodReferencia(anyString())).thenReturn(pedidoResponse);
//        when(buscarPagamentoAdapterPort.buscar(eq(pedidoResponse.getIdPagamento()))).thenReturn(null);
//
//        assertThrows(PagamentoNotFoundException.class, () -> validarPagamentoPedidoUseCase.execute(pagamento));
//    }
//
//    @Test
//    void execute_DeveExecutarComSucesso(){
//
//        Pagamento pagamento = buildPagamento();
//        Pedido pedidoResponse = buildPedido();
//
//        Pagamento pagamentoAtualizado = buildPagamento();
//
//        when(listarPedidosUseCasePort.listarPorCodReferencia(anyString())).thenReturn(pedidoResponse);
//        when(buscarPagamentoAdapterPort.buscar(eq(pedidoResponse.getIdPagamento()))).thenReturn(pagamentoAtualizado);
//        when(salvarPagamentoAdapterPort.salvar(any(Pagamento.class))).thenReturn(pagamento);
//
//        assertDoesNotThrow(() -> validarPagamentoPedidoUseCase.execute(pagamento));
//
//        verify(atualizarPedidoAdapterPort, times(1)).execute(any(Pedido.class));
//
//    }
//
//    Pagamento buildPagamento(){
//        Pedido pedido = new Pedido();
//        pedido.setIdPagamento(1L);
//        pedido.setCodReferenciaPedido("123");
//        pedido.setValorTotal(10.2);
//
//        Pagamento pagamento = new Pagamento();
//        pagamento.setPedido(pedido);
//
//        return pagamento;
//    }
//
//    Pedido buildPedido(){
//        Pedido pedido = new Pedido();
//        pedido.setIdPagamento(1L);
//        pedido.setCodReferenciaPedido("123");
//        pedido.setValorTotal(10.2);
//        return pedido;
//    }
//
//}