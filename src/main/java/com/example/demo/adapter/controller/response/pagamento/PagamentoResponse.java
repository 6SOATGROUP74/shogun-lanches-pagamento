package com.example.demo.adapter.controller.response.pagamento;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoResponse {
    @JsonProperty("status")
    private String status;
    @JsonProperty("valor_total")
    private BigDecimal valorTotal;
    @JsonProperty("tipo_do_pagamento")
    private String tipoDoPagamento;
    @JsonProperty("data_pagamento")
    private String dataPagamento;
    @JsonProperty("cod_pagamento")
    private String codPagamento;
}
