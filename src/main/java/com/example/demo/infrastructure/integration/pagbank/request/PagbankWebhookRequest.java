package com.example.demo.infrastructure.integration.pagbank.request;

import com.example.demo.infrastructure.integration.pagbank.response.Pagamento;
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
public class PagbankWebhookRequest {

    @JsonProperty("id")
    private String codPedido;;

    @JsonProperty("charges")
    private List<Pagamento> pagamentos;

}
