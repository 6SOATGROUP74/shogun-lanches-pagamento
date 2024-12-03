package com.example.demo.bdd;


import com.example.demo.adapter.controller.request.pagamento.PagamentoRequest;
import com.example.demo.core.domain.FormasPagamentoEnum;
import io.cucumber.java.pt.Quando;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

public class DefinicaoPassos {

    private Response response;

    private String ENDPOINT_PAGAMENTO = "http://a1d23f03f33f94f51995976dfdfc8be4-990202056.us-east-1.elb.amazonaws.com/v1/pagamento";

    @Quando("receber uma solicitação de pagamento")
    public void um_recebimento_de_uma_solicitação_de_pagamento() {
        PagamentoRequest pagamentoRequest = PagamentoRequest.builder()
                .numeroPedido(1L)
                .valorTotal(BigDecimal.TEN)
                .tipoDoPagamento(FormasPagamentoEnum.QR_CODE_PAGBANK).build();

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pagamentoRequest)
                .when()
                .post(ENDPOINT_PAGAMENTO);
    }

    @Quando("a solitação de pagamento de QR do PagBank deve ser realizada com sucesso")
    public void a_chamada_de_pagamento_de_qr_do_pag_bank_for_realizada() {
        response.then().statusCode(HttpStatus.CREATED.value());
    }
}
