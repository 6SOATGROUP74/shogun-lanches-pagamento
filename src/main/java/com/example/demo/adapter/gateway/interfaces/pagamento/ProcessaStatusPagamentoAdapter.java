package com.example.demo.adapter.gateway.interfaces.pagamento;

import com.example.demo.core.domain.Pagamento;

public interface ProcessaStatusPagamentoAdapter {
    Pagamento execute(Pagamento pagamento);
}
