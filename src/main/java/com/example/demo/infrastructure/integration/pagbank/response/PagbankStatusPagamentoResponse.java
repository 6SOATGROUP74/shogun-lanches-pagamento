package com.example.demo.infrastructure.integration.pagbank.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class PagbankStatusPagamentoResponse {

    @JsonProperty("charges")
    private List<Pagamento> pagamentos;

}
