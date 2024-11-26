package com.example.demo.mocks;

import com.example.demo.adapter.controller.request.pagamento.PagamentoRequest;
import com.example.demo.core.domain.FormasPagamentoEnum;
import com.example.demo.infrastructure.integration.pagbank.presenter.Total;
import com.example.demo.infrastructure.integration.pagbank.request.PagbankWebhookRequest;
import com.example.demo.infrastructure.integration.pagbank.response.Pagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public abstract class PagamentoHelper {

    public static PagamentoRequest gerarPagamentoRequest() {
        return PagamentoRequest.builder()
                .numeroPedido(11L)
                .valorTotal(BigDecimal.valueOf(4219))
                .tipoDoPagamento(FormasPagamentoEnum.QR_CODE_PAGBANK)
                .build();
    }

    public static PagbankWebhookRequest gerarPagbankWebhookRequest() {
        return PagbankWebhookRequest.builder()
                .codPedido("f1f8b77f-3d79-4025-9a38-69c54c6e2733")
                .pagamentos(
                        Arrays.asList(
                                Pagamento.builder()
                                        .codigoDoPagamento("f1f8b77f-3d79-4025-9a38-69c54c6e2733")
                                        .codigoReferenciaDoPedido("ORDER_123")
                                        .status("PAID")
                                        .total(Total.builder().valor(1989L).build())
                                        .dataPagamento(LocalDateTime.now().toString()
                                        ).build()
                        )).build();
    }
}
