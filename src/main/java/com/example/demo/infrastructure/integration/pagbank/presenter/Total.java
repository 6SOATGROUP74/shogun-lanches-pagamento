package com.example.demo.infrastructure.integration.pagbank.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Total {

    public Total() {
    }

    @JsonProperty("value")
    private Long valor;
}
