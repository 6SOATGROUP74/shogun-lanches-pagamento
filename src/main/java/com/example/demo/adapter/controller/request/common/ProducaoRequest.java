package com.example.demo.adapter.controller.request.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProducaoRequest {

    @JsonProperty("numero_pedido")
    private Long numeroPedido;

    @JsonProperty("status_pedido")
    private String statusPedido;

    public ProducaoRequest(){}

    public ProducaoRequest(Long numeroPedido, String statusPedido) {
        this.numeroPedido = numeroPedido;
        this.statusPedido = statusPedido;
    }

    public Long getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(Long numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    @Override
    public String toString() {
        return "ProducaoRequest{" +
                "numeroPedido=" + numeroPedido +
                ", statusPedido='" + statusPedido + '\'' +
                '}';
    }
}
