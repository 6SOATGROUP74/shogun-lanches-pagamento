package com.example.demo.infrastructure.integration.pagbank;

import com.example.demo.adapter.gateway.interfaces.pagamento.PagarPedidoAdapterPort;
import static com.example.demo.core.domain.FormasPagamentoEnum.QR_CODE_PAGBANK;
import com.example.demo.core.domain.Pagamento;
import static com.example.demo.core.domain.StatusPagamento.PENDENTE;
import com.example.demo.infrastructure.integration.pagbank.presenter.Cliente;
import com.example.demo.infrastructure.integration.pagbank.presenter.Produto;
import com.example.demo.infrastructure.integration.pagbank.presenter.Total;
import com.example.demo.infrastructure.integration.pagbank.request.PagbankPagamentoRequest;
import com.example.demo.infrastructure.integration.pagbank.request.QRCodeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PagarPedidoPagbankAdapter implements PagarPedidoAdapterPort {

    @Value("${pagbank.token}")
    String token;

    @Autowired
    private PagbankClient pagbankClient;

    public PagarPedidoPagbankAdapter(PagbankClient pagbankClient) {
        this.pagbankClient = pagbankClient;
    }

    @Override
    public Pagamento pagar(Pagamento pagamento) {
        logger.info("m=pagar, status=init, msg=Realizando requisição para geração de QR no PagBank, pagamento={}", pagamento);
        var response = pagbankClient.realizaPagamentoQRCodePix(token, criaRequestParaPagamento(pagamento));
        pagamento.setDataPagamento(response.getBody().getDataPagamento());
        pagamento.setCodPagamento(response.getBody().getCodigoDoPagamento());
        pagamento.setTipoDoPagamento(QR_CODE_PAGBANK.name());
        pagamento.setstatusDoPagamento(PENDENTE.name());
        pagamento.setCopiaCola(response.getBody().getQrCodes().get(0).getCopiaCola());
        pagamento.setQrCodeLink(response.getBody().getQrCodes().get(0).getLinks().get(0).getLinkDoQRCode());
        logger.info("m=pagar, status=success, msg=Geração de QR no PagBank realizada com sucesso, pagamento={}", pagamento);
        return pagamento;
    }

    private PagbankPagamentoRequest criaRequestParaPagamento(Pagamento pagamento) {
        logger.info("m=criaRequestParaPagamento, status=init, msg=Criando a request para o PagBank, pagamento={}", pagamento);
        PagbankPagamentoRequest pagbankPagamentoRequest = new PagbankPagamentoRequest(
                pagamento.getNumeroPedido().toString(),
                new Cliente("Shogun Lanches", "shogunlanches@gmail.com", "78026897000110"),
                List.of(new Produto("Ordem de pedido Shogun Lanches", 1L, pagamento.getValorTotal().longValue())),
                List.of(new QRCodeRequest(new Total(pagamento.getValorTotal().longValue()))),
                List.of("https://webhook.site/51035dbc-3bb3-43ac-b00d-6be6f3cf51d8")

        );
        logger.info("m=criaRequestParaPagamento, status=success, msg=Request para o PagBank criado com sucesso, pagamento={}", pagamento);
        return pagbankPagamentoRequest;
    }

    private final Logger logger = LoggerFactory.getLogger(PagarPedidoPagbankAdapter.class);
}
