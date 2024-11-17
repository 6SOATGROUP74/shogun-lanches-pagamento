package com.example.demo.adapter.gateway.interfaces.impl;

import com.example.demo.core.domain.Pagamento;
import com.example.demo.adapter.gateway.interfaces.pagamento.SalvarPagamentoAdapterPort;
import com.example.demo.infrastructure.repository.PagamentoRepository;
import com.example.demo.infrastructure.repository.presenter.PagamentoEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SalvarPagamentoAdapter implements SalvarPagamentoAdapterPort {

    private final PagamentoRepository pagamentoRepository;

    public SalvarPagamentoAdapter(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @Override
    public Pagamento salvar(Pagamento pagamento) {
        logger.info("m=salvar, status=init, msg=Persistindo pagamento na base de dados, pagamento={}", pagamento);
        final var result = pagamentoRepository.save(PagamentoEntityMapper.INSTANCE.mapFrom(pagamento));
        logger.info("m=salvar, status=success, msg=Persistência de pagamento na base de dados realizada com sucesso, pagamento={}", pagamento);
        return PagamentoEntityMapper.INSTANCE.mapFrom(result);
    }
    private Logger logger = LoggerFactory.getLogger(SalvarPagamentoAdapter.class);
}
