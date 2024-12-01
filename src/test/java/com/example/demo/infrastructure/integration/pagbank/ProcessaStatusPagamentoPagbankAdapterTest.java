package com.example.demo.infrastructure.integration.pagbank;


import com.example.demo.core.domain.Pagamento;
import com.example.demo.core.domain.StatusPagamento;
import com.example.demo.infrastructure.integration.pagbank.response.PagbankPagamentoResponse;
import com.example.demo.infrastructure.integration.pagbank.response.PagbankStatusPagamentoResponse;
import static com.example.demo.mocks.PagamentoHelper.gerarPagbankPagamentoResponse;
import static com.example.demo.mocks.PagamentoHelper.gerarPagbankStatusPagamentoResponse;
import com.example.demo.mocks.PagamentoMockBuilder;
import org.junit.jupiter.api.Assertions;
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

class ProcessaStatusPagamentoPagbankAdapterTest {

    PagbankClient pagbankClient = mock(PagbankClient.class);
    ProcessaStatusPagamentoPagbankAdapter processaStatusPagamentoPagbankAdapter = new ProcessaStatusPagamentoPagbankAdapter(pagbankClient);

    @Test
    public void deveProcessarPagamentoComSucesso(){
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
        PagbankStatusPagamentoResponse pagbankStatusPagamentoResponse = gerarPagbankStatusPagamentoResponse();
        ResponseEntity<PagbankStatusPagamentoResponse> pagbankPagamentoResponseResponseEntity = new ResponseEntity(pagbankStatusPagamentoResponse, HttpStatus.OK);

        when(pagbankClient.consultaStatusPagamento(any(), any())).thenReturn(pagbankPagamentoResponseResponseEntity);

        processaStatusPagamentoPagbankAdapter.execute(pagamento);

        verify(pagbankClient, times(1)).consultaStatusPagamento(any(), any());
    }
}