package com.example.demo.infrastructure.integration.pagbank;

import com.example.demo.infrastructure.integration.pagbank.request.PagbankPagamentoRequest;
import com.example.demo.infrastructure.integration.pagbank.response.PagbankPagamentoResponse;
import com.example.demo.infrastructure.integration.pagbank.response.PagbankStatusPagamentoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "PagbankClient", url = "${pagbank.url}")
public interface PagbankClient {

    @PostMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PagbankPagamentoResponse> realizaPagamentoQRCodePix(@RequestHeader("Authorization") String token, @RequestBody PagbankPagamentoRequest pagbankPagamentoRequest);

    @GetMapping(value = "/orders/{order_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PagbankStatusPagamentoResponse> consultaStatusPagamento(@RequestHeader("Authorization") String token, @PathVariable("order_id") String orderId);

}
