package com.example.demo.core.usecase.interfaces.pagamento;

import com.example.demo.core.domain.Pagamento;

public interface AlterarStatusPagamentoUseCasePort {
    Pagamento execute(Long pagamentoId);
}
