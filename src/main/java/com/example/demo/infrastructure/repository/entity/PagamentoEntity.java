package com.example.demo.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "tb_pagamento")
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    private Long idPagamento;

    @Column(name = "numero_pedido")
    private Long numeroPedido;

    @Column(name = "status")
    private String status;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "tipo_do_pagamento")
    private String tipoDoPagamento;

    @Column(name = "data_pagamento")
    private String dataPagamento;

    @Column(name = "cod_pagamento")
    private String codPagamento;

    @Column(name = "copia_cola")
    private String copiaCola;

    @Column(name = "qr_code_link")
    private String qrCodeLink;
}
