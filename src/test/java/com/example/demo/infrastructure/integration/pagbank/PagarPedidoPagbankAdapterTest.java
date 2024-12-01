package com.example.demo.infrastructure.integration.pagbank;

import com.example.demo.core.domain.Pagamento;
import com.example.demo.core.domain.StatusPagamento;
import com.example.demo.infrastructure.integration.pagbank.response.PagbankPagamentoResponse;
import static com.example.demo.mocks.PagamentoHelper.gerarPagbankPagamentoResponse;
import com.example.demo.mocks.PagamentoMockBuilder;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class PagarPedidoPagbankAdapterTest {
    PagbankClient pagbankClient = mock(PagbankClient.class);
    PagarPedidoPagbankAdapter pagarPedidoPagbankAdapter = new PagarPedidoPagbankAdapter(pagbankClient);

    @Test
    public void deveGerarQrCodePagbankComSucesso() {
        Pagamento pagamento = PagamentoMockBuilder.builder()
                .idPagamento(1L)
                .numeroPedido(1L)
                .statusDoPagamento(StatusPagamento.PENDENTE.name())
                .valorTotal(BigDecimal.valueOf(100))
                .tipoDoPamento("QRCODE_PAGBANK")
                .dataPagamento(LocalDateTime.now().toString())
                .codPagamento("ORDER_123")
                .copiaCola("testeCopiaCola")
                .qrCodeLink("testeQrCodeLink")
                .build();
        PagbankPagamentoResponse pagbankPagamentoResponse = gerarPagbankPagamentoResponse();
        ResponseEntity<PagbankPagamentoResponse> pagbankPagamentoResponseResponseEntity = new ResponseEntity(pagbankPagamentoResponse, HttpStatus.OK);

        when(pagbankClient.realizaPagamentoQRCodePix(any(), any())).thenReturn(pagbankPagamentoResponseResponseEntity);

        pagarPedidoPagbankAdapter.pagar(pagamento);

        verify(pagbankClient, times(1)).realizaPagamentoQRCodePix(any(), any());
    }
}