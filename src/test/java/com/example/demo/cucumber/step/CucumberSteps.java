package com.example.demo.cucumber.step;

import com.example.demo.adapter.controller.request.pagamento.PagamentoRequest;
import com.example.demo.adapter.controller.response.pagamento.PagamentoResponse;
import com.example.demo.core.domain.FormasPagamentoEnum;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.cucumber.junit.platform.engine.Cucumber;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

public class CucumberSteps {

    PagamentoResponse pagamentoResponse = null;
    Response response;

    @LocalServerPort
    private int port;

    @Dado("uma requisição")
    public void uma_requisição() {

        PagamentoRequest pagamentoRequest = PagamentoRequest.builder()
                .numeroPedido(1L)
                .valorTotal(BigDecimal.TEN)
                .tipoDoPagamento(FormasPagamentoEnum.QR_CODE_PAGBANK).build();

        RestAssured.baseURI = "http://localhost:"+port;

       RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pagamentoRequest)
                .post("/v1/pagamento")
                .then()
                .statusCode(HttpStatus.CREATED.value());

    }

    @Quando("processar a requisição")
    public void processar_a_requisição() {


    }

    @Então("o pagamento será realizado com sucesso")
    public void o_pagamento_será_realizado_com_sucesso() {

    }
}
