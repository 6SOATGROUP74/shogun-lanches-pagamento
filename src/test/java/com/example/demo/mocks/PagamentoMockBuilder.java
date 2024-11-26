package com.example.demo.mocks;

import com.example.demo.core.domain.Pagamento;

import java.math.BigDecimal;

public class PagamentoMockBuilder {

    private Long idPagamento;
    private Long numeroPedido;
    private String statusDoPagamento;
    private BigDecimal valorTotal;
    private String tipoDoPagamento;
    private String dataPagamento;
    private String codPagamento;
    private String copiaCola;
    private String qrCodeLink;

    private PagamentoMockBuilder() {
    }

    public static PagamentoMockBuilder builder() {
        return new PagamentoMockBuilder();
    }

    public PagamentoMockBuilder idPagamento(Long idPagamento) {
        this.idPagamento = idPagamento;
        return this;
    }

    public PagamentoMockBuilder numeroPedido(Long numeroPedido) {
        this.numeroPedido = numeroPedido;
        return this;
    }

    public PagamentoMockBuilder statusDoPagamento(String statusDoPagamento) {
        this.statusDoPagamento = statusDoPagamento;
        return this;
    }

    public PagamentoMockBuilder valorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public PagamentoMockBuilder tipoDoPamento(String tipoDoPagamento) {
        this.tipoDoPagamento = tipoDoPagamento;
        return this;
    }

    public PagamentoMockBuilder dataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
        return this;
    }

    public PagamentoMockBuilder codPagamento(String codPagamento) {
        this.codPagamento = codPagamento;
        return this;
    }

    public PagamentoMockBuilder copiaCola(String copiaCola) {
        this.copiaCola = copiaCola;
        return this;
    }

    public PagamentoMockBuilder qrCodeLink(String qrCodeLink) {
        this.qrCodeLink = qrCodeLink;
        return this;
    }

    public Pagamento build() {
        Pagamento pagamento = new Pagamento();
        pagamento.setIdPagamento(this.idPagamento);
        pagamento.setNumeroPedido(this.numeroPedido);
        pagamento.setstatusDoPagamento(this.statusDoPagamento);
        pagamento.setValorTotal(this.valorTotal);
        pagamento.setTipoDoPagamento(this.tipoDoPagamento);
        pagamento.setDataPagamento(this.dataPagamento);
        pagamento.setCodPagamento(this.codPagamento);
        pagamento.setCopiaCola(this.copiaCola);
        pagamento.setQrCodeLink(this.qrCodeLink);
        return pagamento;
    }
}
