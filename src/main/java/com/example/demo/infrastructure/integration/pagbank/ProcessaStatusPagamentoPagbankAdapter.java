package com.example.demo.infrastructure.integration.pagbank;

import com.example.demo.core.domain.Pagamento;
import com.example.demo.adapter.gateway.interfaces.pagamento.ProcessaStatusPagamentoAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProcessaStatusPagamentoPagbankAdapter implements ProcessaStatusPagamentoAdapter {

    @Value("${pagbank.token}")
    String token;

    @Autowired
    private PagbankClient pagbankClient;

    public ProcessaStatusPagamentoPagbankAdapter(PagbankClient pagbankClient) {
        this.pagbankClient = pagbankClient;
    }

    @Override
    public Pagamento execute(Pagamento pagamento) {
        var response = pagbankClient.consultaStatusPagamento(token, pagamento.getNumeroPedido().toString());
        if(response.getBody().getPagamentos().get(0).getStatus().equals("PAID")){
            pagamento.setCodPagamento(response.getBody().getPagamentos().get(0).getCodigoDoPagamento());
        }
        return pagamento;
    }
}
