package com.example.demo.mocks;

import com.example.demo.adapter.controller.request.pagamento.PagamentoRequest;
import com.example.demo.core.domain.FormasPagamentoEnum;
import com.example.demo.core.domain.StatusPagamento;
import com.example.demo.infrastructure.integration.pagbank.presenter.Cliente;
import com.example.demo.infrastructure.integration.pagbank.presenter.LinkQRCode;
import com.example.demo.infrastructure.integration.pagbank.presenter.Produto;
import com.example.demo.infrastructure.integration.pagbank.presenter.Total;
import com.example.demo.infrastructure.integration.pagbank.request.PagbankPagamentoRequest;
import com.example.demo.infrastructure.integration.pagbank.request.PagbankWebhookRequest;
import com.example.demo.infrastructure.integration.pagbank.request.QRCodeRequest;
import com.example.demo.infrastructure.integration.pagbank.response.Pagamento;
import com.example.demo.infrastructure.integration.pagbank.response.PagbankPagamentoResponse;
import com.example.demo.infrastructure.integration.pagbank.response.PagbankStatusPagamentoResponse;
import com.example.demo.infrastructure.integration.pagbank.response.QrCodeResponse;
import com.example.demo.infrastructure.repository.entity.PagamentoEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static PagamentoEntity convertePagamentoParaPagamentoEntity(com.example.demo.core.domain.Pagamento pagamento) {
        PagamentoEntity pagamentoEntity = new PagamentoEntity();
        pagamentoEntity.setIdPagamento(pagamento.getIdPagamento());
        pagamentoEntity.setNumeroPedido(pagamento.getNumeroPedido());
        pagamentoEntity.setStatus(pagamento.getstatusDoPagamento());
        pagamentoEntity.setValorTotal(pagamento.getValorTotal());
        pagamentoEntity.setTipoDoPagamento(pagamento.getTipoDoPagamento());
        pagamentoEntity.setDataPagamento(pagamento.getDataPagamento());
        pagamentoEntity.setCodPagamento(pagamento.getCodPagamento());
        pagamentoEntity.setCopiaCola(pagamento.getCopiaCola());
        pagamentoEntity.setQrCodeLink(pagamentoEntity.getQrCodeLink());
        return pagamentoEntity;
    }

    public static PagbankPagamentoRequest gerarPagbankPagamentoRequest(com.example.demo.core.domain.Pagamento pagamento) {
        PagbankPagamentoRequest pagbankPagamentoRequest = new PagbankPagamentoRequest(
                pagamento.getNumeroPedido().toString(),
                new Cliente("Shogun Lanches", "shogunlanches@gmail.com", "78026897000110"),
                List.of(new Produto("Ordem de pedido Shogun Lanches", 1L, pagamento.getValorTotal().longValue())),
                List.of(new QRCodeRequest(new Total(pagamento.getValorTotal().longValue()))),
                List.of("https://webhook.site/51035dbc-3bb3-43ac-b00d-6be6f3cf51d8")
        );
        return pagbankPagamentoRequest;
    }

    public static PagbankPagamentoResponse gerarPagbankPagamentoResponse() {
        PagbankPagamentoResponse pagamentoResponse = new PagbankPagamentoResponse(
                "TESTE",
                LocalDateTime.now().toString(),
                "ORDER_12345",
                new Cliente("Shogun Lanches", "shogunlanches@gmail.com", "78026897000110"),
                List.of(new Produto("Ordem de pedido Shogun Lanches", 1L, 1000L)),
                List.of(new QrCodeResponse("testeQrCode", LocalDateTime.now().plusDays(10L).toString(), new Total(1000L), "copiaCola", List.of(new LinkQRCode("linkdoQRCode"))))
        );
        return pagamentoResponse;
    }

    public static PagbankStatusPagamentoResponse gerarPagbankStatusPagamentoResponse(String status) {
        List<Pagamento> pagamentoList = Arrays.asList(new Pagamento("TESTE", "ORDER_12345", status, new Total(1000L), LocalDateTime.now().plusDays(10L).toString()));

        PagbankStatusPagamentoResponse pagbankStatusPagamentoResponse = PagbankStatusPagamentoResponse.builder().pagamentos(pagamentoList).build();

        return pagbankStatusPagamentoResponse;
    }
}
