package com.example.demo.infrastructure.integration.producao;

import com.example.demo.adapter.controller.PagamentoController;
import com.example.demo.adapter.controller.request.common.ProducaoRequest;
import com.example.demo.adapter.gateway.interfaces.producao.EnviaPedidoParaProducaoAdapterPort;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnviaPedidoParaProducaoAdapter implements EnviaPedidoParaProducaoAdapterPort {

    private static final Logger logger = LoggerFactory.getLogger(PagamentoController.class);

    @Autowired
    private SqsTemplate sqsTemplate;

    @Override
    public void enviaPedido(ProducaoRequest producaoRequest) {

        logger.info("m=enviaPedido, status=init, msg=Enviando pedido para a cozinha, producaoRequest={}", producaoRequest);

        sqsTemplate.send(to -> to.queue("producao").payload(producaoRequest));

        logger.info("m=enviaPedido, status=success, msg=Pedido enviado para a cozinha, producaoRequest={}", producaoRequest);
    }
}
