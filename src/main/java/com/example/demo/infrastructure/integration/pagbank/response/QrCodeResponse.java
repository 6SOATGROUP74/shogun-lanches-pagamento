package com.example.demo.infrastructure.integration.pagbank.response;


import com.example.demo.infrastructure.integration.pagbank.presenter.LinkQRCode;
import com.example.demo.infrastructure.integration.pagbank.presenter.Total;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QrCodeResponse {

    @JsonProperty("id")
    private String codigoQRCode;

    @JsonProperty("expiration_date")
    private String dataExpiracao;

    @JsonProperty("amount")
    private Total total;

    @JsonProperty("text")
    private String copiaCola;

    @JsonProperty("links")
    private List<LinkQRCode> links;
}