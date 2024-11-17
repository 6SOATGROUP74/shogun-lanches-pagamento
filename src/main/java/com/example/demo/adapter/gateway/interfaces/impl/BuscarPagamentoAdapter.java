package com.example.demo.adapter.gateway.interfaces.impl;
import com.example.demo.adapter.controller.PagamentoController;
import com.example.demo.core.domain.Pagamento;
import com.example.demo.adapter.gateway.interfaces.pagamento.BuscarPagamentoAdapterPort;
import com.example.demo.exceptions.PagamentoNotFoundException;
import com.example.demo.infrastructure.repository.PagamentoRepository;
import com.example.demo.infrastructure.repository.presenter.PagamentoEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BuscarPagamentoAdapter implements BuscarPagamentoAdapterPort {

    private final PagamentoRepository pagamentoRepository;

    public BuscarPagamentoAdapter(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @Override
    public Pagamento buscar(Long pagamentoId) {
        logger.info("m=buscar, status=init,  msg=Busca de pagamento, pagamentoId={}", pagamentoId);
        final var pagamentoEntity = pagamentoRepository.findById(pagamentoId).orElse(null);

        if(Objects.isNull(pagamentoEntity)){
            throw new PagamentoNotFoundException("Pagamento não encontrado");
        }

        final var pagamento = PagamentoEntityMapper.INSTANCE.mapFrom(pagamentoEntity);

        logger.info("m=buscar, status=sucess,  msg=Busca de pagamento realizada com sucesso, pagamentoId={}", pagamentoId);
        return pagamento;
    }

    private Logger logger = LoggerFactory.getLogger(PagamentoController.class);
}

