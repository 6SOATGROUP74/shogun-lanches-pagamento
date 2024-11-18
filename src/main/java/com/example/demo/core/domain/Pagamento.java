package com.example.demo.core.domain;

import java.math.BigDecimal;

public class Pagamento {

    private Long idPagamento;
    private Long numeroPedido;
    private String statusDoPagamento;
    private BigDecimal valorTotal;
    private String tipoDoPagamento;
    private String dataPagamento;
    private String codPagamento;
    private String copiaCola;
    private String qrCodeLink;

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getCopiaCola() {
        return copiaCola;
    }

    public void setCopiaCola(String copiaCola) {
        this.copiaCola = copiaCola;
    }

    public String getQrCodeLink() {
        return qrCodeLink;
    }

    public void setQrCodeLink(String qrCodeLink) {
        this.qrCodeLink = qrCodeLink;
    }

    public String getCodPagamento() {
        return codPagamento;
    }

    public void setCodPagamento(String codPagamento) {
        this.codPagamento = codPagamento;
    }

    public String getstatusDoPagamento() {
        return statusDoPagamento;
    }

    public void setstatusDoPagamento(String statusDoPagamento) {
        this.statusDoPagamento = statusDoPagamento;
    }

    public Long getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Long idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Long getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(Long numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getTipoDoPagamento() {
        return tipoDoPagamento;
    }

    public void setTipoDoPagamento(String tipoDoPagamento) {
        this.tipoDoPagamento = tipoDoPagamento;
    }


    @Override
    public String toString() {
        return "Pagamento{" +
                "idPagamento=" + idPagamento +
                ", numeroPedido=" + numeroPedido +
                ", statusDoPagamento='" + statusDoPagamento + '\'' +
                ", valorTotal=" + valorTotal +
                ", tipoDoPagamento='" + tipoDoPagamento + '\'' +
                ", dataPagamento='" + dataPagamento + '\'' +
                ", codPagamento='" + codPagamento + '\'' +
                ", copiaCola='" + copiaCola + '\'' +
                ", qrCodeLink='" + qrCodeLink + '\'' +
                '}';
    }
}
