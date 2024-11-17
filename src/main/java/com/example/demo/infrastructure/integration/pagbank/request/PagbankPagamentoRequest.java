package com.example.demo.infrastructure.integration.pagbank.request;

import com.example.demo.infrastructure.integration.pagbank.presenter.Cliente;
import com.example.demo.infrastructure.integration.pagbank.presenter.Produto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PagbankPagamentoRequest {

    @JsonProperty("reference_id")
    private String codigoReferenciaDoPedido;

    @JsonProperty("customer")
    private Cliente cliente;

    @JsonProperty("items")
    private List<Produto> produtos;

    @JsonProperty("qr_codes")
    private List<QRCodeRequest> qrCodes;

    @JsonProperty("notification_urls")
    private List<String> notificationUrls;
}
