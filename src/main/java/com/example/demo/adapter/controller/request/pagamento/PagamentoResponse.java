package com.example.demo.adapter.controller.request.pagamento;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoResponse {
    private String status;
    private BigDecimal valorTotal;
    private String tipoDoPagamento;
    private String dataPagamento;
    private String codPagamento;
    private String copiaCola;
    private String qrCodeLink;
}
