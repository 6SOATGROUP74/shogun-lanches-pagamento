package com.example.demo.adapter.gateway.interfaces.producao;

import com.example.demo.adapter.controller.request.common.ProducaoRequest;

public interface EnviaPedidoParaProducaoAdapterPort {
    void enviaPedido(ProducaoRequest producaoRequest);
}
