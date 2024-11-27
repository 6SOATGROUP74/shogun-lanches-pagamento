package com.example.demo.infrastructure.integration.pagbank.request;

import com.example.demo.infrastructure.integration.pagbank.presenter.Total;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class QRCodeRequest {

    @JsonProperty("amount")
    private Total total;
}
