package com.example.demo.adapter.controller.request.pagamento.mapper;

import com.example.demo.adapter.controller.request.pagamento.PagamentoRequest;
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

    @Named("converteParaBigDecimal")
    default BigDecimal converteParaBigDecimal(Long valor) {
        if (valor != null) {
            return BigDecimal.valueOf(valor);
        }
        return BigDecimal.ZERO;
    }
}

