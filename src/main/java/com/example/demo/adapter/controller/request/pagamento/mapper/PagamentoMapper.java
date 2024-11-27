package com.example.demo.adapter.controller.request.pagamento.mapper;

import com.example.demo.adapter.controller.request.pagamento.PagamentoRequest;
import com.example.demo.adapter.controller.response.pagamento.PagamentoResponse;
import com.example.demo.adapter.controller.request.pagamento.PagamentoStatusResponse;
import com.example.demo.core.domain.Pagamento;
import com.example.demo.infrastructure.integration.pagbank.request.PagbankWebhookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper
public interface PagamentoMapper {

    PagamentoMapper INSTANCE = Mappers.getMapper(PagamentoMapper.class);

    @Mapping(target = "codPagamento", expression = "java(pagbankWebhookRequest.getPagamentos().get(0).getCodigoDoPagamento())")
    @Mapping(target = "pedido.codReferenciaPedido", expression = "java(pagbankWebhookRequest.getPagamentos().get(0).getCodigoReferenciaDoPedido())")
    @Mapping(target = "statusDoPagamento", expression = "java(pagbankWebhookRequest.getPagamentos().get(0).getStatus())")
    @Mapping(target = "valorTotal", expression = "java(converteParaBigDecimal(pagbankWebhookRequest.getPagamentos().get(0).getTotal().getValor()))")
    Pagamento mapFrom(PagbankWebhookRequest pagbankWebhookRequest);

    Pagamento mapFrom(PagamentoRequest pagamentoRequest);

    @Mapping(target = "statusDoPagamento", source = "statusDoPagamento")
    @Mapping(target = "valorTotal", source = "valorTotal")
    @Mapping(target = "tipoDoPagamento", source = "tipoDoPagamento")
    @Mapping(target = "dataPagamento", source = "dataPagamento")
    @Mapping(target = "codPagamento", source = "codPagamento")
    @Mapping(target = "idPagamento", source = "idPagamento")
    @Mapping(target = "copiaCola", source = "copiaCola")
    @Mapping(target = "qrCodeLink", source = "qrCodeLink")
    PagamentoResponse mapFrom(Pagamento pagamento);

    @Mapping(target = "statusDoPagamento", source = "statusDoPagamento")
    @Mapping(target = "valorTotal", source = "valorTotal")
    @Mapping(target = "codPagamento", source = "codPagamento")
    PagamentoStatusResponse mapConvertFrom(Pagamento pagamento);

    @Named("converteParaBigDecimal")
    default BigDecimal converteParaBigDecimal(Long valor) {
        if (valor != null) {
            return BigDecimal.valueOf(valor);
        }
        return BigDecimal.ZERO;
    }
}

