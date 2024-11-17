package com.example.demo.adapter.controller.request.pagamento;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoStatusResponse {
    private String status;
    private BigDecimal valorTotal;
    private String codPagamento;
}
