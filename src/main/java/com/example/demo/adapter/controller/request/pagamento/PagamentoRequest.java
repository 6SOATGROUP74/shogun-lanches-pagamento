package com.example.demo.adapter.controller.request.pagamento;

import com.example.demo.core.domain.FormasPagamentoEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagamentoRequest {

    @NotEmpty(message = "O campo não pode ser vazio.")
    @NotNull(message = "O campo obrigatório")
    @JsonProperty("numero_pedido")
    private Long numeroPedido;

    @JsonProperty("valor_total")
    private Long valorTotal;

    @NotEmpty(message = "O campo não pode ser vazio.")
    @NotNull(message = "O campo obrigatório")
    @JsonProperty("tipo_do_pagamento")
    private FormasPagamentoEnum tipoDoPagamento;
}
