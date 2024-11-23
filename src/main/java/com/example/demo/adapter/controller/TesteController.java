package com.example.demo.adapter.controller;

import com.example.demo.adapter.controller.request.common.ProducaoRequest;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/pagamento")
public class TesteController {

    private static final Logger logger = LoggerFactory.getLogger(PagamentoController.class);

    @Autowired
    private SqsTemplate sqsTemplate;

    @PostMapping("/cria-evento")
    public ResponseEntity<?> criaEventoFilaSqs(@RequestBody ProducaoRequest producaoRequest) {

        sqsTemplate.send(to -> to.queue("producao").payload(producaoRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body("Evento criado com sucesso!");
    }
}
