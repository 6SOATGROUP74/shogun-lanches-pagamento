package com.example.demo.adapter.controller.request.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProducaoRequest {

    @JsonProperty("numero_pedido")
    private Long numeroPedido;

    @JsonProperty("id_pagamento")
    private Long idPagamento;

    @JsonProperty("status_pedido")
    private String statusPedido;
}
