package com.example.demo.adapter.controller;

import com.example.demo.adapter.controller.request.pagamento.PagamentoRequest;
import com.example.demo.adapter.controller.request.pagamento.mapper.PagamentoMapper;
import com.example.demo.adapter.gateway.interfaces.pagamento.BuscarPagamentoAdapterPort;
import com.example.demo.adapter.presenter.pagamento.PagamentoResponseMapper;
import com.example.demo.core.domain.Pagamento;
import com.example.demo.core.usecase.interfaces.pagamento.AlterarStatusPagamentoUseCasePort;
import com.example.demo.core.usecase.interfaces.pagamento.PagarPedidoUseCasePort;
import com.example.demo.core.usecase.interfaces.pagamento.ValidarPagamentoPedidoUseCasePort;
import com.example.demo.infrastructure.integration.pagbank.request.PagbankWebhookRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/pagamento")
public class PagamentoController {

    private static final Logger logger = LoggerFactory.getLogger(PagamentoController.class);

    private final PagarPedidoUseCasePort pagarPedidoUseCasePort;
    private final ValidarPagamentoPedidoUseCasePort validarPagamentoPedidoUseCasePort;
    private final BuscarPagamentoAdapterPort buscarPagamentoAdapterPort;
    private final AlterarStatusPagamentoUseCasePort alterarStatusPagamentoUseCasePort;

    public PagamentoController(PagarPedidoUseCasePort pagarPedidoUseCasePort, ValidarPagamentoPedidoUseCasePort validarPagamentoPedidoUseCasePort, BuscarPagamentoAdapterPort buscarPagamentoAdapterPort, AlterarStatusPagamentoUseCasePort alterarStatusPagamentoUseCasePort){
        this.pagarPedidoUseCasePort = pagarPedidoUseCasePort;
        this.validarPagamentoPedidoUseCasePort = validarPagamentoPedidoUseCasePort;
        this.buscarPagamentoAdapterPort = buscarPagamentoAdapterPort;
        this.alterarStatusPagamentoUseCasePort = alterarStatusPagamentoUseCasePort;
    }


    @PostMapping
    public ResponseEntity<?> realizarPagamento(@RequestBody PagamentoRequest pagamentoRequest) {

        logger.info("m=realizarPagamento, status=init,  msg=Realiza processo de pagamento, pagamentoRequest={}", pagamentoRequest);

        Pagamento pagamento = pagarPedidoUseCasePort.checkout(PagamentoMapper.INSTANCE.mapFrom(pagamentoRequest));

        logger.info("m=realizarPagamento, status=success,  msg=Processo de pagamento realizado com sucesso, pagamentoRequest={}", pagamentoRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(PagamentoMapper.INSTANCE.mapFrom(pagamento));
    }

    @GetMapping("/{codPagamento}")
    public ResponseEntity<?> consultaStatusPagamento(@PathVariable Long pagamentoId) {

        logger.info("m=consultaStatusPagamento, status=init,  msg=Consulta status de pagamento, pagamentoId={}", pagamentoId);

        Pagamento pagamentoStatus = buscarPagamentoAdapterPort.buscar(pagamentoId);

        logger.info("m=consultaStatusPagamento, status=sucess,  msg=Consulta status de pagamento realizada com sucesso, pagamentoId={}", pagamentoId);

        return ResponseEntity.ok().body(PagamentoResponseMapper.INSTANCE.mapFrom(pagamentoStatus));
    }

    @PostMapping("/confirma-pagamento/{pagamentoId}")
    public ResponseEntity<?> confirmaPagamen(@PathVariable Long pagamentoId) {

        logger.info("m=recebeConfirmacaoDePagamentoWebhook, msg=Recebendo confirmação de status de pagamento do Pagbank, pagamentoId={}", pagamentoId);

        Pagamento pagamentoAtual = buscarPagamentoAdapterPort.buscar(pagamentoId);

        var pagamento =  validarPagamentoPedidoUseCasePort.execute(pagamentoAtual);

        logger.info("m=recebeConfirmacaoDePagamentoWebhook, msg=Confirmação de pagamento recebido do Pagbank com sucesso, pagamentoId={}", pagamentoId);

        return ResponseEntity.status(HttpStatus.CREATED).body(PagamentoMapper.INSTANCE.mapConvertFrom(pagamento));
    }

    @PostMapping("/webhook")
    public ResponseEntity<?> recebeConfirmacaoDePagamentoWebhook(@PathVariable Long pagamentoId) {

        logger.info("m=recebeConfirmacaoDePagamentoWebhook, msg=Recebendo confirmação de status de pagamento do Pagbank, pagamentoId={}", pagamentoId);

        var pagamento =  alterarStatusPagamentoUseCasePort.execute(pagamentoId);

        logger.info("m=recebeConfirmacaoDePagamentoWebhook, msg=Confirmação de pagamento recebido do Pagbank com sucesso, pagamentoId={}", pagamentoId);

        return ResponseEntity.status(HttpStatus.CREATED).body(PagamentoMapper.INSTANCE.mapConvertFrom(pagamento));
    }
}
