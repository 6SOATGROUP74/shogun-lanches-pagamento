package com.example.demo.infrastructure.repository;

import com.example.demo.infrastructure.repository.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<PagamentoEntity, Long> {
    PagamentoEntity findByCodPagamento(String codPagamento);
}
