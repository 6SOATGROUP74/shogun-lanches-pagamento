package com.example.demo.infrastructure.integration.pagbank.response;

import com.example.demo.infrastructure.integration.pagbank.presenter.Total;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Pagamento {

    @JsonProperty("id")
    private String codigoDoPagamento;

    @JsonProperty("reference_id")
    private String codigoReferenciaDoPedido;

    @JsonProperty("status")
    private String status;

    @JsonProperty("amount")
    private Total total;

    @JsonProperty("paid_at")
    private String dataPagamento;
}
