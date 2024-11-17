package com.example.demo.infrastructure.integration.pagbank.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LinkQRCode {

    public LinkQRCode(){}

    @JsonProperty("href")
    private String linkDoQRCode;
}