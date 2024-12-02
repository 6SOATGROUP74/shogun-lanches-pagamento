package com.example.demo.infrastructure.integration.pagbank.response;


import com.example.demo.infrastructure.integration.pagbank.presenter.Cliente;
import com.example.demo.infrastructure.integration.pagbank.presenter.Produto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PagbankPagamentoResponse {

    @JsonProperty("id")
    private String codigoDoPagamento;

    @JsonProperty("created_at")
    private String dataPagamento;

    @JsonProperty("reference_id")
    private String codigoReferenciaDoPedido;

    @JsonProperty("customer")
    private Cliente cliente;

    @JsonProperty("items")
    private List<Produto> produtos;

    @JsonProperty("qr_codes")
    private List<QrCodeResponse> qrCodes;
}
